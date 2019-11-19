package cn.enjoy.core.utils;


public class CommonConstant {

    /**
     * 是否有效:有效
     */
    public final static int VALID = 1;

    /**
     * 是否有效:无效
     */
    public final static int INVALID = 0;

    /**
     * 用户状态 未激活
     */
    public final static int USER_STATUS_NO_ACTIVATION = 0;

    /**
     * 用户状态 已激活
     */
    public final static int USER_STATUS_YES_ACTIVATION = 1;

    /**
     * 用户状态 冻结
     */
    public final static int USER_STATUS_FREEZE = 2;

    /**
     * 用户状态：已作废
     */
    public final static int USER_STATUS_CANCEL = 4;

    public final static String INIT_PASSWORD_RAND = "12345678";


    public final static String SESSION_USER_KEY = "login_user";

    /**
     *
     */
    public final static String SESSION_USER_NAME_KEY = "login_user_name";

    public final static String SESSION_USER_ID_KEY = "login_user_id";

    public final static String SESSION_USER_REAL_NAME_KEY = "login_user_real_name";

    public final static String SESSION_MENUS_KEY = "user_menus";

    public final static String SESSION_ACCESS_TOKEN = "s_accessToken";


    /**
     *是否为虚拟组织负责人
     */
    public final static byte ORGANIZATION_ADMIN = 1;

    public final static byte ORGANIZATION_COMMONLY = 0;


    /**
     * 用户职务
     */
    public  final static String USER_POSITION_LEVEL_EMP= "1"; //普通职员

    public  final static String USER_POSITION_LEVEL_DEPARTMENT="2"; //经理

    public  final static String USER_POSITION_LEVEL_SECOND_DEPARTMENT="3"; //副经理


    //部门Code
    public  final static String DEPARTMENT_DEVELOPMENT = "0001";  //开发部门

    public final static String DEPARTMENT_GENERAL = "0002";  //总经办
    public final static String DEPARTMENT_MARKET = "0003";  //市场部
    public final static String DEPARTMENT_FINANCE = "0004";  //财务部
    public final static String QUALITY_DEPARTMENT = "0005";  //质量部
    public final static String  DEPARTMENT_HR = "0009";  //人事部

    //时间格式
    public final static String  DATE_TIME_FMT= "yyyy-MM-dd HH:mm:ss";
    public final static String  DATE_TIME_FMT_NO_SECOND= "yyyy-MM-dd HH:mm";

    public final static String DATE_FMT="yyyy-MM-dd";

    //流程状态
    public final static String APPLY_STATUS_ING="0"; //申请中

    public final static String APPLY_STATUS_REJECT="2";//拒绝

    public final static String APPLY_STATUS_PASS="1";//通过

    public final static String APPLY_STATUS_CANCEL = "3";//作废

    public final static String APPLY_STATUS_DELETE = "4"; //删除

    //流程标识
    public final static String FLOW_LEAVE="LeaveInfoProcess";//请假

    public final static String FLOW_WORK="WorkOffInfoProcess";//调休

    public final static String FLOW_OVER="OvertimeInfoProcess";//加班

    public final static String FLOW_EGRESS="EgressionInfoProcess";//外出

    public final static String FLOW_BUSINESS_TRIP="BusinessTrip";//出差

    public final static String FLOW_SEAL="SealProcess";//用章

    public final static String FLOW_VEHICLE="vehicle";//用车

    public final static String FLOW_Resource="ResourceProcess";//借用资源流程

    public final static String FLOW_Archive="archiveProcess";//存档资源流程

    //流程标识
    public final static String LeaveInfoProcess="请假";//请假

    public final static String WorkOffInfoProcess="调休";//调休

    public final static String OvertimeInfoProcess="加班";//加班

    public final static String EgressionInfoProcess="外出";//外出

    public final static String SealProcess="用章";//用章

    //公章使用途径
    public final static String ACCESS_GY="0"; //公用

    public final static String ACCESS_SY="1"; //私用

    //各种申请单操作
    public final static String APPLY_CANCEL="CANCEL"; //作废

    //操作绩效表单标志
    public final static String AUDIT="audit"; //传进来的参数等于audit时，才是评分操作，其余为查看

    //流程状态
    public final static String USER_HOUR_STATUS_COMMOM = "1"; //正常

    public final static String USER_HOUR_STATUS_INVALID = "2"; //失效

    public final static String USER_HOUR_STATUS_REJECT = "3"; //作废
}
