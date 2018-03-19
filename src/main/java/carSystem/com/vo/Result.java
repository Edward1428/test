package carSystem.com.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by rico on 2016/3/3.
 */
public class Result {

    public static final int PAGE_SUCCESS = 1;
    public static final int PAGE_FAILURE = 0;

    private int status;

    @JSONField(serialzeFeatures = SerializerFeature.WriteNullStringAsEmpty)
    private String msg;

    @JSONField(serialzeFeatures = SerializerFeature.WriteNullListAsEmpty)
    private Object data;

    public Result(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(PAGE_SUCCESS, "请求成功", null);
    }

    public static Result success(Object data){
        return new Result(PAGE_SUCCESS, "请求成功", data);
    }

    public static Result failed(String error) {
        return new Result(PAGE_FAILURE, error, null);
    }

    public static Result failed(int status, String errorMsg) {
        return new Result(status, errorMsg, null);
    }

    public static Result failed(int status, String errorMsg, Object data) {
        return new Result(status, errorMsg, data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
