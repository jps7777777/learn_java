package image.exception;

public enum EnumException implements CommonException{

    SERVER_ERROR(100100,"系统错误"),
    UNKNOWN_ERROR(100101,"未知错误"),
    SELF_DEFINING_ERROR(100102,"未定义错误"),
    PARAMS_ERROR(100103,"参数错误"),
    PARAMS_IS_EMPTY(100103,"参数不能为空"),

    DATABASE_ERROR(100201,"数据库使用异常"),
    DATA_ERROR(100202,"数据异常"),
    DATA_EMPTY(100203,"没有查询数据内容"),
    /**
     *
     */
    USER_ERROR(200100,"用户错误"),
    USER_NOT_LOGIN(200101,"用户未登录"),
    ;

    private int errCode;
    private String errMsg;

    EnumException(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

}
