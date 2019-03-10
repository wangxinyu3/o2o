package web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主要用来解析路由并转发到相应的html中
 */
@Controller
@RequestMapping(value = "shopadmin", method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping(value = "/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanage")
    public String ShopManage(){
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement", method =RequestMethod.GET)
    public String productCategoryManage(){
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation", method =RequestMethod.GET)
    public String productOperation(){
        //转发至商品添加/编辑页面
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement", method =RequestMethod.GET)
    public String productManagement(){
        //转发至商品添加/编辑页面
        return "shop/productmanagement";
    }
}
