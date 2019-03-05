package service;

import dto.ProductCategoryExecution;
import entity.ProductCategory;
import exceptions.ProductCategoryOperationExecption;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 查询指定某个店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationExecption
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
        throws ProductCategoryOperationExecption;

    /**
     * 将此类别下的商品类别id置为空，再删掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationExecption
     */
     ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
        throws ProductCategoryOperationExecption;
}
