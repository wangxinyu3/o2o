package dao;

import entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    ProductCategoryDao productCategoryDao;


    @Test
    public void testBQueryByShopId()throws Exception {
        long shopId = 1;
        List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("该店铺自定义类别数为：" + list.size());
    }

    @Test
    public void testABatchInsertProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1l);
        productCategory.setProductCategoryName("商品类别1");
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1l);
        productCategory2.setProductCategoryName("商品类别2");
        List<ProductCategory> list = new ArrayList<>();
        list.add(productCategory);
        list.add(productCategory2);
        int effectNum = productCategoryDao.batchInsertProductCategory(list);
        assertEquals(2,effectNum);
    }

    @Test
    public void testCDeleteProductCategory()throws Exception{
        long shopId = 1;
        List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory pc : list){
            if ("商品类别1".equals(pc.getProductCategoryName()) || "商品类别2".equals(pc.getProductCategoryName())){
                int effectNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                assertEquals(1,effectNum);
            }
        }
    }
}
