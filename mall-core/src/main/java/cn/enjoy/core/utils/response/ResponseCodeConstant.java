package cn.enjoy.core.utils.response;

/**
 * Created by zw on 2017/4/13.
 */
public class ResponseCodeConstant {

    public final static String SUCCESS = "0000";

    public final static String FAIL = "9999";

    public final static  String UN_LOGIN_ERROR = "2323";
    /**
     * 没权限的编码
     */
    public final static  String ACCESS_DENIED = "1111";
    //重复提交
    public final static String ERR_CODE_DUPLICATE_SUBMIT = "8888";

    /**
     * 系统异常
     */
    public final static String SYSTEM_EXCEPTION_ERROR = "-1";

    /**
     * 参数校验错误
     */
    public final static String PARAM_VALIDE_FAIL = "1000";


    /**
     * 用户登录失败
     */
    public final static String USER_LOGIN_FAIL = "1002";
    public final static String USER_LOGIN_FAIL_NO_USER_MSG = "用户名不存在或已冻结，请联系管理员";
    public final static String USER_LOGIN_FAIL_NO_ACTIVATION_MSG= "用户未激活";
    public final static String USER_LOGIN_FAIL_FREEZEED_MSG = "贵公司与平台还是非合作中,如有疑问请联系公司管理员咨询!";
    public final static String USER_LOGIN_FAIL_CANCELED_MSG = "用户已作废";
    public final static String USER_LOGIN_FAIL_PASSWORD_FAIL = "1001";
    public final static String USER_LOGIN_FAIL_PASSWORD_FAIL_MSG = "密码错误";


    /**
     * 用户修改密码失败
     */
    public final static String USER_PASSWORD_MODIFY_FAIL = "1006";

    public final static String USER_PASSWORD_MODIFY_FAIL_UNLIKE_MSG = "输入新密码不一致";

    public final static String USER_PASSWORD_MODIFY_FAIL_NOTPASS_MSG = "原密码不正确";

}
