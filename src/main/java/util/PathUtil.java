package util;

public class PathUtil {
//    获取系统文件的分隔符
    private static  String separator = System.getProperty("file.separator");
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/image";
        }else {
            basePath = "/home/xiangze/image";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }
    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/",separator);
    }
}
