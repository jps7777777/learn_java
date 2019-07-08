package image.util;

public class FinallyException extends Exception implements CommonException {

    private CommonException commonException;

    public FinallyException(CommonException commonException){
        this.commonException = commonException;
    }

    public FinallyException(CommonException commonException,String errMsg){
        super();
        this.commonException = commonException;
        this.commonException.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonException.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonException.getErrMsg();
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        this.commonException.setErrMsg(errMsg);
        return this.commonException;
    }
}
