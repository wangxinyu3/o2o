package exceptions;

import enums.ProductCategoryStateEnum;

public class ProductCategoryOperationException extends RuntimeException{

    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}
