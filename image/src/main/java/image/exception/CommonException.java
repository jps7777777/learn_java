package image.exception;

public interface CommonException {
    /**
     * 返回错误编号
     * @return
     */
    int getErrCode();

    /**
     * 返回错误信息
     * @return
     */
    String getErrMsg();

    /**
     * 返回标准异常对象
     * @param errMsg
     * @return
     */
    CommonException setErrMsg(String errMsg);
}
