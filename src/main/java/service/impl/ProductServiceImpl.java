package service.impl;

import dao.ProductDao;
import dao.ProductImgDao;
import dto.ImageHolder;
import dto.ProductExecution;
import entity.Product;
import entity.ProductImg;
import enums.ProductStateEnum;
import exceptions.ProductOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.ProductService;
import util.ImageUtil;
import util.PageCalculator;
import util.PathUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    /**
     * **********
     * 添加商品
     * **********
     *
     * 1.处理缩略图，获取缩略图相对路径并赋值给product
     * 2.往tb_product写入商品信息 获取productId
     * 3.结合productId批量处理商品详情图
     * 4.将商品详情图列表批量插入tb_product_img中
     */
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null){
            //给商品添加默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架的状态
            product.setEnableStatus(1);
            //若缩略图不为空则添加
            if (thumbnail != null){
                addThumbnail(product,thumbnail);
            }
            try {
                //创建商品信息
                int effectNum = productDao.insertProduct(product);
                if (effectNum < 0){
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("商品创建失败" + e.toString());
            }
            //若商品详情图不为空则添加
            if (productImgList != null && productImgList.size() > 0){
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        }else {
            //传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 批量添加商品详情图
     *
     * @param product
     * @param productImgHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储路径，这里直接存放到相应店铺的文件夹地下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        //遍历图片一次去处理,并添加进productImg实体类里面
        for(ImageHolder productImgHolder : productImgHolderList){
            String imgArr = ImageUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgArr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果确实有图片需要添加的,就执行批量添加操作
        if (productImgList.size() > 0){
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectNum <= 0){
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败" + e.toString());
            }
        }
    }


    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }


    /**
     * 注意事务控制@Transactional
     */
    @Override
    @Transactional
    //1.若缩略图参数有值，则处理缩略图，
    //若原先存在缩略图则先删除在添加新图，之后获取缩略图相对路径并赋值给product
    //2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
    //3.将tb_product_img下面的该商品原来的商品详情图记录全部删除
    //4.更新tb_product_img以及tb_product的信息
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // 设置默认的属性
            product.setLastEditTime(new Date());

            // Step1. 处理缩略图
            if (thumbnail != null) {
                //先获取一遍原有信息，因为原来的信息里有原图片地址
                Product tempProduct = productDao.queryProductById(product.getProductId());
                // 1.1 删除旧的缩略图
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                // 1.2 添加新的缩略图
                addThumbnail(product, thumbnail);
            }

            // Step2. 处理商品详情

            // 如果添加商品成功，继续处理商品详情图片，并写入tb_product_img
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                // 2.1 删除库表中productId对应的tb_product_img的信息
                deleteProductImgList(product.getProductId());
                // 2.2 处理商品详情图片，并写入tb_product_img
                addProductImgList(product, productImgHolderList);
            }
            try {
                // Step3.更新tb_product
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("商品更新失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("商品更新失败：" + e.getMessage());
            }

        } else {
            return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
        }
    }




    private void deleteProductImgList(long productId) {
        // 获取该商铺下对应的productImg信息
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        // 遍历删除该目录下的全部文件
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // 删除tb_product_img中该productId对应的记录
        productImgDao.deleteProductImgByProductId(productId);

    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //页码转换成数据库的行码，并调用dao层取回指定页码的商品列表
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        //基于同样的查询条件返回该查询条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }
}
