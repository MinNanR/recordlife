package site.minnan.recordlife.infrastructure.enumerate;

/**
 * 日志上的操作类型
 * @author Minnan on 2021/2/25
 */
public enum Operation {
    ADD("添加"),

    UPDATE("修改"),

    DELETE("删除"),

    DOWNLOAD("下载"),

    LOGIN("登录"),

    LOGOUT("登出");

    private final String operationName;

    Operation(String operationName){
        this.operationName = operationName;
    }

    public String operationName(){
        return operationName;
    }

    @Override
    public String toString() {
        return this.operationName;
    }
}
