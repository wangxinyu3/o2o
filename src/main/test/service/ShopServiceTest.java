package service;

import dao.BaseTest;
import dto.ImageHolder;
import dto.ShopExecution;
import entity.Area;
import entity.PersonInfo;
import entity.Shop;
import entity.ShopCategory;
import enums.ShopStateEnum;
import exceptions.ShopOperationException;
import org.jetbrains.annotations.TestOnly;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shopCondition.setShopCategory(shopCategory);
        ShopExecution shopExecution = shopService.getShopList(shopCondition,1,2);
        System.out.println(shopExecution.getShopList().size());
        System.out.println(shopExecution.getCount());
    }
    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException, FileNotFoundException{
        Shop shop = shopService.getByShopId(1L);
        shop.setShopId(1L);
        shop.setShopName("修改后的店铺名称");
        File shopImg = new File("C:/Users/Administrator/Desktop/image/cat.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder("cat.jpg",is);
        ShopExecution shopExecution =shopService.modifyShop(shop, imageHolder);
        System.out.println("新的图片地址：" + shopExecution.getShop().getShopImg());
    }

    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:/Users/Administrator/Desktop/image/test.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(),is);
        ShopExecution se = shopService.addShop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }
}
