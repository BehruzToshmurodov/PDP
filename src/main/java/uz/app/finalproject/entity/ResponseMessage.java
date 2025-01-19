package uz.app.finalproject.entity;

public class ResponseMessage {

    private String message;
    private Object data;
    private Boolean status;


    public ResponseMessage(String message, Object data, Boolean status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public ResponseMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
