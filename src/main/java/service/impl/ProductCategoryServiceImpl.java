package service.impl;

import com.sun.xml.internal.bind.v2.TODO;
import dao.ProductCategoryDao;
import dto.ProductCategoryExecution;
import entity.ProductCategory;
import enums.ProductCategoryStateEnum;
import exceptions.ProductCategoryOperationExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.ProductCategoryService;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationExecption {
        if (productCategoryList != null && productCategoryList.size() > 0){
            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectNum <= 0){
                    throw new ProductCategoryOperationExecption("店铺类别创建失败");
                }else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationExecption("batchInsertProductCategory error:" + e.getMessage());
            }
        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationExecption {
        // TODO 将此类商品类别下的商品类别ID置为空
        try {
           int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
           if (effectNum <= 0){
               throw new ProductCategoryOperationExecption("商品类别删除失败!");
           }else {
               return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
           }
         }catch (Exception e){
            throw new ProductCategoryOperationExecption("deleteProductCategory error:"+ e.getMessage());
        }
    }


}
