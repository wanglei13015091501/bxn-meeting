package cn.boxiao.bxn.meeting.util;




/**
 * 描述：
 * Created by maqiuliang
 * 
 **/
public interface Constants {
    public static final String SUCCESS_CODE = "0000";
    public static final String SUCCESS_MSG = "操作成功";
    public static final String FAIL_CODE = "0001";
    public static final String FAIL_MSG = "维护失败";
    public static final String DATA_NOT_FOUND = "0002";
    public static final String DATA_NOT_FOUND_MSG = "查无结果";
    public static final String FAIL_PARAM_CODE = "0003";
    public static final String FAIL_PARAM__MSG = "参数验证失败";
    public static final String INTERNAL_ERROR = "0004";
    public static final String INTERNAL_ERROR_MSG = "内部错误";
    public static final String CUSTOMER_EXISTS = "0005";
    public static final String CUSTOMER_EXISTS_MSG = "客户信息已经存在";
    public static final String NO_PERMS_CODE = "0006";
    public static final String NO_PERMS__MSG = "无权访问,若要添加此权限请联系管理员";

 


    //打印日志用
    public static final String SUCCESS_ADD_MSG = "添加成功";
    public static final String SUCCESS_MODIFY_MSG = "修改成功";
    public static final String SUCCESS_DELETE_MSG = "删除成功";

    //01** 参数
    public static final String PARAM_CODE_EXISTS = "0101";
    public static final String PARAM_CODE_EXISTS_MSG = "参数代码已经存在";
    public static final String DICT_EXISTS = "0201";
    public static final String DICT_EXISTS_MSG = "字典信息已经存在";
    public static final String COMPANY_EXISTS = "0301";
    public static final String COMPANY_EXISTS_MSG = "公司信息已经存在";
    public static final String DEPT_EXISTS = "0401";
    public static final String DEPT_EXISTS_MSG = "所属公司已存在该部门";

    //状态0通过1未审核2驳回
    public static final String BUSINESS_STATE_WSH = "1";//未审核
    public static final String BUSINESS_STATE_TG = "0";//0通过
    public static final String BUSINESS_STATE_BH = "2";//驳回

    public static final String INVALID_ARGUMENTS = "2000"; // 无效参数


    public static final String CUSTOMER_DEFAULT_PWD = "123456"; // 客户默认密码

    public static final float IMG_WIDTH = 640;//上传图片的宽度
    public static final float IMG_HEIGH = 720;//上传图片的高度
    public static final int BASE64_MAX_LENGTH = 150000;//base64流的长度超过该值的时候 进行压缩转换

    public static final String ADV_PAGE_URL = "/advPage/";//广告页存放位置
    public static final String ADV_PAGE_IMG_URL = "image/";//广告页图片存放位置
    public static final String ADV_PAGE_VIDEO_URL = "video/";//广告页视频存放位置
    public static final String MEETING_LOGO_URL = "/meetLogo/";//会议Logo存放位置

    //自定义权限
    public static final String ROLE_OPE_ALL_CUSTOMER = "opeAllCustomer";//操作所有人员
    public static final String ROLE_OPE_ALL_MEETING = "opeAllMeeting";//操作所有会议
    public static final String ROLE_OPE_ALL_REGION = "opeAllRegion";//操作所有校区
    public static final String ROLE_OPE_ALL_MEETROOM = "opeAllMeetRoom";//操作所有会议室
    public static final String ROLE_OPE_ALL_DEVICE = "opeAllDevice";//操作所有设备
    public static final String ROLE_OPE_ALL_COMPANY = "opeAllCompany";//操作所有公司


    public static final String ICOOL_TYPE_SCHOOL = "school";//系统用户为学校
    public static final String ICOOL_TYPE_COMPANY = "company";//系统用户为公司


 
}
