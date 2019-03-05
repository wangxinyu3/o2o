package dao;

import entity.Product;
import entity.ProductCategory;
import entity.ProductImg;
import entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest{
    @Autowired
    private ProductDao productDao;

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

}
