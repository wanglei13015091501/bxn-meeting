/**
 * Copyright 2016-2018 boxiao.cn
 */
package cn.boxiao.bxn.meeting;

/**
 * @author liumeng
 * @since bxc-space 1.0 2016年3月18日
 */
public interface ErrCode {

	String ERR_COMMON_SYSTEMRUNTIMEEXCEPTION = "110000001";// 系统运行期异常
	String ERR_COMMON_FILENOTFOUND = "110000002";// 文件不存在
	String ERR_COMMON_FILEIOEXCEPTION = "110000003";// 文件IO错误
	String ERR_COMMON_NETIOEXCEPTION = "110000004";// 网络错误
	String ERR_COMMON_PARAMNOTDEFINE = "110000005";// 配置参数不存在
	String ERR_COMMON_XMLPARSE = "110000006";// XML解析错误
	String ERR_COMMON_DATAPETTEN = "110000007";// 数据解析时发生的格式错误
	String ERR_COMMON_CRYPTERROR = "110000008";// 加密错误
	String ERR_COMMON_DECRYPTERROR = "110000009";// 解密错误
	String ERR_COMMON_SERVERLOCATION_ERROR = "110000010";// 服务地址定位错误
	String ERR_RESTAUTHENTICATIONFAILED = "110000011"; // REST服务认证失败
	String ERR_BADREQUEST = "110000012"; // 错误的请求
	String ERR_RESTNOTFOUND = "110000013"; // REST资源不存在
	String ERR_RESTCALLERROR = "110000014"; // REST资源访问时出现未知错误

	String ERR_CODE_RUNTIME_IST = "110000115";

	String ERR_ILLEGALMETHODPARAMEXCEPTION = "110001002";// 方法参数错误
	String ERR_COMMON_JDBC = "110000024";// JDBC错误
	String ERR_COMMON_JDBC_QUERY_RETURNED_UNEXPECTED_RESULT_SIZE = "110000025";// JDBC查询返回了与预期不一致的结果数

	String ERROR_CODE_PREFIX = "";

	String ERROR_CODE_CONFIGPARAM_NULL = ERROR_CODE_PREFIX + "0001"; // 配置参数为空
	String VIDEO_ATTRIBUTE_NOT_SUPPORT = ERROR_CODE_PREFIX + "1019";// 所需视频属性不支持

	String ERR_CODE_RUNTIME_IMP_EXCEL = "110000113";
	String ERR_EXCELVALIDATEERROR = "110002001";// EXCEL上传定义错误
	String ERR_EXCELFILENOTFOUND = "110002002";// 上传的Excel找不到，或未上传文件
	String ERR_EXCELNODATA = "110002003";// 导入的Excel没有数据或数据没有放入第一个工作簿（Sheet）
	String ERR_CODE_RUNTIME_EXP_EXPRESSION_INVALID = "000103";
	String ERR_CODE_RUNTIME_EXP_COMPILE_ROW_EXPRESSION_IS_NULL = "000106";
	String ERR_CODE_RUNTIME_EXP_COMPILE_ROW_EXPRESSION_MUST_IN_ONE_ROW = "000107";
	String ERR_CODE_RUNTIME_EXP_EXCEL_NOT_FOUND = "000104";
	String ERR_CODE_RUNTIME_EXP_EXCEL_INVALID = "000105";
	String ERR_CODE_RUNTIME_EXP_EXCEL_EXPRESSION_COMPILE_ERROR = "000108";
	String ERR_CODE_RUNTIME_EXP_EXCEL_EXPRESSION_COMPILER_INIT_ERROR = "000110";
	String ERR_CODE_RUNTIME_EXP_EXCEL_EXPRESSION_EXECUTE = "000111";
	String ERR_CODE_RUNTIME_EXP_WORD_INVALID = "000112";

	String ILLEGAL_REQUEST_ARGUMENT = "110001001";// 非法请求参数
	String ILLEGAL_METHOD_ARGUMENT = "110001002";// 非法方法参数
	String REMOTE_RETURNING_UNEXPECTED_OBJECT_VALUE = "110001003";// 远程返回了非预期的对象值

	/**
	 * BXC COMMON
	 */
	String ERR_CODE_BXC_PREFIX = "990";
	String ERR_CODE_BXC_COMMON_PREFIX = ERR_CODE_BXC_PREFIX + "000";

	String NO_CLASSIFIED_INTERNAL_ERROR = ERR_CODE_BXC_COMMON_PREFIX + "001";// 未分类错误
	String NOT_HAVE_CORRECT_DATA_FORMAT = ERR_CODE_BXC_COMMON_PREFIX + "002";// 没有正确的数据格式
	String NO_OBJECT_FOUND = ERR_CODE_BXC_COMMON_PREFIX + "003"; // 未找到指定对象
	String DUPLICATED_OBJECT_FOUND = ERR_CODE_BXC_COMMON_PREFIX + "004"; // 重名
	String EXCEED_THRESHOLD_FAILED = ERR_CODE_BXC_COMMON_PREFIX + "005"; // 超过限制值
	String FILE_UPLOAD_FAILED = ERR_CODE_BXC_COMMON_PREFIX + "006"; // 文件上传失败

	/**
	 * BXC SPACE
	 */
	String ERR_CODE_BXC_SPACE_PREFIX = ERR_CODE_BXC_PREFIX + "001";
	String NO_AUTH_TO_ACCESS_CLAZZ_SPACE = ERR_CODE_BXC_SPACE_PREFIX + "001";// 没有权限访问空间
	String NO_AUTH_TO_OPERATION_IN_CLAZZ_SPACE = ERR_CODE_BXC_SPACE_PREFIX + "002";// 没有空间操作权限权限
	String NO_CLAZZ_FOUND = ERR_CODE_BXC_SPACE_PREFIX + "003";// 空间未找到
	String NO_USER_IN_CLAZZ_FOUND = ERR_CODE_BXC_SPACE_PREFIX + "004";// 空间用户未找到

	/**
	 * BXC RESPLAT
	 */
	String ERR_CODE_BXC_RESPLAT_PREFIX = ERR_CODE_BXC_PREFIX + "002";
	String NO_AUTH_TO_OPERATION_IN_RESPLAT = ERR_CODE_BXC_RESPLAT_PREFIX + "003"; // 没有资源平台的操作权限
	String NO_AUTH_TO_READ_IN_RESPLAT = ERR_CODE_BXC_RESPLAT_PREFIX + "004"; // 没有资源平台的可读权限
	String NO_AUTH_TO_WRITE_IN_RESPLAT = ERR_CODE_BXC_RESPLAT_PREFIX + "005"; // 没有资源平台的可写权限
}
