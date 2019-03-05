package dao;

import entity.Product;

public interface ProductDao {
    /***
     * 插入商品
     * @param product
     * @return
     */
    int insertProduct(Product product);
}
