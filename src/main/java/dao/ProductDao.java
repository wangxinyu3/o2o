package dao;

import entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    /**
     * 分页查询
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex")int rowIndex,
                                   @Param("pageSize")int pageSize);

    int queryProductCount(@Param("productCondition") Product productCondition);
    /**
     * 通过productId查询唯一的商品信息
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /***
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(Product product);


    /**
     * 更新商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除商品类别之前，将商品类别Id置为空
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);
}
