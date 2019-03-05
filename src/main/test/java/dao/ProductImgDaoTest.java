package dao;

import entity.ProductImg;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductImgDaoTest extends  BaseTest{
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchInsertProductImg()throws Exception{
        ProductImg productImg = new ProductImg();
        productImg.setImgAddr("图片1");
        productImg.setImgDesc("测试1");
        productImg.setPriority(1);
        productImg.setCreateTime(new Date());
        productImg.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);
        List<ProductImg> list = new ArrayList<>();
        list.add(productImg);
        list.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(list);
        assertEquals(2,effectNum);
    }

    @Test
    @Ignore
    public void testBQueryProductImgList(){
        List<ProductImg> list = productImgDao.queryProductImgList(1L);
        assertEquals(2,list.size());
    }

    @Test
    @Ignore
    public void testCDeleteProductImgProductId()throws Exception{
        long productId = 1L;
        int effectNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectNum);
    }
}
