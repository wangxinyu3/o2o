package exceptions;

import enums.ProductCategoryStateEnum;

public class ProductCategoryOperationExecption extends RuntimeException{

    public ProductCategoryOperationExecption(String msg){
        super(msg);
    }
}
