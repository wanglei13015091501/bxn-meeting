package cn.boxiao.bxn.meeting.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

/**
 * 功能描述 :
 * create by maqiuliang
 * 
 **/
public class ReuMsg {
    private String code;
    private String msg;
    private String redirect;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public ReuMsg() {
        this.code = Constants.SUCCESS_CODE;
        this.msg = Constants.SUCCESS_MSG;
    }

    public ReuMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReuMsg(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.PrettyFormat);
    }

    /**
     * 返回OK，无数据
     *
     * @return
     */
    public static ReuMsg createOkReuMsg() {
        return new ReuMsg();
    }

    /**
     * 返回OK，有数据
     *
     * @param data
     * @return
     */
    public static ReuMsg createOkReuMsg(Object data) {
        return new ReuMsg(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, data);
    }

    /**
     * 参与验证失败
     *
     * @param msg
     * @return
     */
    public static ReuMsg createParamErrorReuMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = Constants.FAIL_PARAM__MSG;
        }
        return new ReuMsg(Constants.FAIL_PARAM_CODE, msg, null);
    }

    /**
     * 错误
     *
     * @param ex
     * @return
     */
    public static ReuMsg createErrorMsg(Exception ex) {
        return new ReuMsg(Constants.FAIL_CODE, ex.getMessage(), null);
    }


}
