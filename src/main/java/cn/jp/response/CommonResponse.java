package cn.jp.response;

public class CommonResponse {

    private String status;
    private Object data;

    public static CommonResponse create(Object object){
        return create(object,"success");
    }

    public static CommonResponse create(Object object,String status){
        CommonResponse back = new CommonResponse();
        back.setStatus(status);
        back.setData(object);
        return back;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
