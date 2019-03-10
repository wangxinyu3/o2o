package enums;

public enum ProductStateEnum {
    SUCCESS(1, "插入成功"), INNER_ERROR(-1001, "操作失败"),NULL_PRODUCT(-1003,"店铺信息为空");

    private int state;
    private String stateInfo;

    ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}