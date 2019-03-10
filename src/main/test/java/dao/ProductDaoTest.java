package dao;

import entity.Product;
import entity.ProductCategory;
import entity.ProductImg;
import entity.Shop;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest{
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsertProduct()throws Exception{
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(1L);
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试desc1");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(0);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试desc2");
        product2.setImgAddr("test2");
        product2.setPriority(1);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);
        int effectNum = productDao.insertProduct(product1);
        assertEquals(1,effectNum);
        effectNum = productDao.insertProduct(product2);
        assertEquals(1,effectNum);
    }

    @Test
    public void testCQueryProductByProductId()throws Exception{
        long productId = 1;
        //初始化两个商品详情图实例作为productId为1的商品下的详情图片
        //批量插入到商品的详情图表中
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> list = new ArrayList<>();
        list.add(productImg1);
        list.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(list);
        assertEquals(2, effectNum);

        Product product = productDao.queryProductById(productId);
        assertEquals(2, product.getProductImgList().size());

        effectNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectNum);
    }


}
