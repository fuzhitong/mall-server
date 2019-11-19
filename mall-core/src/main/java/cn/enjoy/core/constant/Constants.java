/**   
* @Title: Constants.java 
* @Package com.toroot.ht.http 
* @Description: 常量 
* @author 陈顺  
* @date 2016年6月14日 上午11:28:45 
* @version V1.0   
*/
package cn.enjoy.core.constant;


public class Constants {
	
	public static final String JS_VERSION = "1.0.0.11";
	
	public static boolean IS_TEST_MONEY = false; 
	
	public static String IS_COMPANY_DEFAULT = "1";//1为后台角色
	public static String MENU_SYS_CODE = "1";//1为后台菜单
	//cookie名称
	public static final String LOGING_COOKIE_NAME = "241a8cd98f00b204e48f0998ecf84274";
	//投产地址
	public static final String HXTX_WEB_URL = "www.hxtxtrip.com";
	//标记记住
	public static final String LOGING_IS_COOKIE= "iscookie";
	//是否是手机验证码动态登录 1是
	public static final String LOGING_IS_CAPTCHA_SMS= "1";
	
	//秘钥
	public static final String LOGING_COOKIE_CODE = "202cb962ac59075b964b07152d234b70";
	
	//电话: 40000-59180
	public static final String SYSTEM_PHONE = "40000-59180000000";
	//差旅热线
	public static final String TRAVEL_HOTLINE = "40066-88128";
	//左括号
//	public static final String LEFT_KH = "(";
	//右括号
//	public static final String RIGHT_KH = ")";

	//邮箱 
	public static final String SYSTEM_EMAIL = "yangben@toroot.cn";
	
	//微博: @<%=com.pzh.sys.hxtx.Constants.SYSTEM_NAME%>
	public static final String SYSTEM_WEIBO = "@惠行商旅";	
	
	//微信
	public static final String SYSTEM_WEIXIN = "惠行商旅";

	//系统名称
	public static final String SYSTEM_NAME = "惠行商旅";	

	//地址
	public static final String SYSTEM_ADDR = "深圳市南山区科苑南路武汉大学产学研大楼B603";
	
	//版权
	public static final String SYSTEM_COPYRIGHT = "Copyright © 2016 hxtx.com All Rights Reserved.";

	public static final String CARD_ID = "1";
	
	public static final String PASSPORT = "2"; 
	
	public static final String APPROVE_SBMIT = "0";
	
	public static final String APPROVE_PASS = "1";

	public static final String APPROVE_REJECT = "2";

	public static final String APPROVE_DRAFT = "3";
	
	
	//光大扫码支付
	public static final String PAY_GD_IMG = "gd_img";
	
	//光大信息卡支付
	public static final String PAY_GD_CARDPAY = "gd_cardpay";
	
	//农行支付
	public static final String PAY_NH_PAY = "nh_pay";

	
	public enum CardType {
		 CARD_ID("身份证"),PASSPORT("护照"),MTP("台胞证"),HVPS("回乡证"),OTHERS("其他");
		 
		private final String value;
		
		 CardType(String value){
			 this.value = value;
		 }
		 
		 public String getValue(){
			 return value;
		 }
	}
	public enum VipTYInfo {
//		COMPANY_ID("36b3c8336fea417ab011bdc89572a5cf"),
//		ORG_CODE("TY001"),
		VIP_TY_ACCOUNT("VIP_TY_ACCOUNT");
//		DEPARTMENT_ID("9b784ac28ba849c4b0c95363212e7b8d");

		private final String value;

		VipTYInfo(String value){
			this.value = value;
		}

		public String getValue(){
			return value;
		}
	}
	public enum SmsResult { //短信请求发送结果
		SUCCESS((short)0,"成功"),FAILURE((short) 1,"失败");
		private final short code;
		private final String value;
		SmsResult(short code, String value){
			this.code = code;
			this.value = value;
		}
		public short getCode() {
			return code;
		}
		public String getValue() {
			return value;
		}
	}
	public enum SmsType { //短信请求发送结果
		//短信类型(酒店预订:0，机票预订:1，酒店取消:2，退票:3，改签:4，开户:21，通知:22，其他:41)
		HOTEL_BOOK((short)0,"酒店预订"),
		PLANE_BOOK((short) 1,"机票预订"),
		HOTEL_CANCEL((short) 2,"酒店取消"),
		PLANE_REFUND((short)3,"退票"),
		PLANE_CHANGE((short)4,"改签"),
		OPEN_ACCOUNT((short)21,"开户"),
		NOTICE((short)22,"通知"),
		AIRTICKETREMIND((short)33,"飞机前4小时提醒短信"),
		HOTELREMIND((short)44,"酒店入住前2小时提醒短信"),
		OTHER((short)41,"其他");
		private final short code;
		private final String value;
		SmsType(short code, String value){
			this.code = code;
			this.value = value;
		}
		public short getCode() {
			return code;
		}
		public String getValue() {
			return value;
		}
	}

	public enum MemberType {
		COMPANY("企业"),PERSONAL("个人");
		
		private final String value;
		
		MemberType(String value) {
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
	} 
 
	//保存当前用户的查询审批
	public static final String MY_HOTEL_TR_NO = "my_tr_no"; 
	
	//保存当前用户的出差类型
	public static final String MY_HOTEL_TRIP_MODE = "my_tripMode";  
	public static final String MY_BOOKING_INFO =     "my_booking_info";   
	public static final String MY_HOTEL_ORDER_ID =    		"MY_HOTEL_ORDER_ID_key";  
	public static final String MY_HOTEL_ORDER_PRICE =     "MY_HOTEL_ORDER_PRICE_key"; 
 
	//cookie保存时间
	public static final int SYSTEM_COOKIE_TIME = 14 * 24 * 3600;  //// 保存14天
	
	//调试输出模式
	public static boolean DEBUG = true;
	
	//是否为测试模式
	public static boolean IS_TEST = false;
	public static String NAME_ = null;
	public static String PASS_ = null;
	public static String URL_ =  null;  


    /**
     *<p><b>description:</b>
	 *  <br>是否开通国内机票最低判断
	 * <p/>
     */
	public static String IS_MIN_TICKER = "1";

	/**
	 *<p><b>description:</b>
	 *  <br>是否开通机票提前预定判断
	 * <p/>
	 */
	public static String IS_ADVANCE = "1";

	/**
	 *<p><b>description:</b>
	 *  <br>是否首页显示差旅政策
	 * <p/>
	 */
	public static String IS_VIEW = "1";
	
	public static final String QQR_ROLE_CODE = "QQR";
	
	public static final String RESOURCE_BACK = "2";

	/**
	 *<p><b>description:</b>
	 *  <br>用户 登陆系统划分  后台code
	 * <p/>
	 */
	public static final  String  BACKGROUNDCODE = "1";
	/**
	 *<p><b>description:</b>
	 *  <br>用户 登陆系统划分  前台code
	 * <p/>
	 */
	public static final  String  PROSCENIUMCODE = "PROSCENIUM";
	/**
	 *<p><b>description:</b>
	 *  <br>邮件链接地址的开头地址
	 * <p/>
	 */
	public static final String MAILHEAD = "http://www.hxtxtrip.com/hxtx";


	//机票订单状态
	public static final short PLANE_ORDER_USER_STATUS_DZF = 1;// ("待支付"),
	public static final short PLANE_ORDER_USER_STATUS_CPZ = 2;// ("出票中"),
	public static final short PLANE_ORDER_USER_STATUS_YCP = 3;// ("已出票"),
	public static final short PLANE_ORDER_USER_STATUS_YQX = 4;// ("已取消");

	//酒店订单状态
	public static final short HOTEL_ORDER_STATUS_DQR = 1;// ("待确认"),
	public static final short HOTEL_ORDER_STATUS_YQR = 2;// ("已确认"),
	public static final short HOTEL_ORDER_STATUS_DZF = 3;// ("待支付"),
	public static final short HOTEL_ORDER_STATUS_YZF = 4;// ("已支付"),
	public static final short HOTEL_ORDER_STATUS_WRZ = 5;// ("未入住"),
	public static final short HOTEL_ORDER_STATUS_YRZ = 6;// ("已入住");
	public static final short HOTEL_ORDER_STATUS_QX = 7;//  ("已取消");

	//酒店系统状态(1:系统成功;2:待处理;3已处理)
	public static final short HOTEL_SYSTEM_STATUS_XTCG = 1;// ("系统成功"),
	public static final short HOTEL_SYSTEM_STATUS_DCL =  2;// ("待处理"),
	public static final short HOTEL_SYSTEM_STATUS_YCL =  3;// ("已处理"),
	
	//短信状态
	public static final short SMS_TYPE_HOTEL_LOG = 11;			//同时的发短信到值班手机有一笔酒店待处理订单				
	 
	//订单日志记录的状态  
	public static final String ORDER_LOG_STATUS_SYSTEM  		= "2";			//订单系统日志; 
	public static final String ORDER_LOG_STATUS_CHONGSHI  	= "887";		//有错发起重试; 
	public static final String ORDER_LOG_STATUS_FSDDKS  		= "888";		//发送订单开始; 
	public static final String ORDER_LOG_STATUS_HASHLOG  		= "11";			//记录机票goproductid与对应的HASH值  
	public static final String ORDER_LOG_STATUS_SEARCH  		= "12";			//订单的查询条件
	public static final String ORDER_LOG_STATUS_TBJP			= "77";  		//记录同步机票取得信息
	public static final String ORDER_LOG_STATUS_TBJPCC			= "78";  		//同步机票出问题
	public static final String ORDER_LOG_STATUS_TBJPYC			= "79";  		//同步机票出问题(异常) 
	public static final String ORDER_LOG_STATUS_TBJD			= "88";  		//同步酒店订单确定状态
	public static final String ORDER_LOG_STATUS_TBJDCC			= "89";  		//同步酒店订单错误状态
	public static final String ORDER_LOG_STATUS_TBJDYC			= "90";  		//同步酒店出错(异常)
	public static final String ORDER_LOG_STATUS_SENDINFO		= "101";  		//向慧通发起订单处理结果
	public static final String ORDER_LOG_STATUS_SENDORDER		= "102";  		//向慧通发起订单处理记录 
	public static final String ORDER_LOG_STATUS_GQLOG			= "103";  		//改签记录
	public static final String ORDER_LOG_STATUS_ORDERLOG		= "111";  		//订单支付调试信息
	public static final String ORDER_LOG_STATUS_GAIQIAN		= "181";  		//改签支付记录信息
	public static final String ORDER_LOG_STATUS_REFUND_OK  	= "2888";		//订单退款成功
	public static final String ORDER_LOG_STATUS_REFUND_FAIL  	= "1888";		//订单退款失败
	public static final String ORDER_LOG_STATUS_SUCESS  		= "8888";		//订单日志成功; 
	public static final String ORDER_LOG_STATUS_SMS_FAIL  	= "8887";		//订单失败时，发送短信失败;
	public static final String ORDER_LOG_STATUS_GQ_FAIL  		= "8886";		//改签失败
	public static final String ORDER_LOG_STATUS_SMS_SEND_FAIL= "8884";	    //"发送成功支付信息失败", "8884" 
	public static final String ORDER_LOG_STATUS_ORDER_COMMON= "8844";			//公共信息
	public static final String ORDER_LOG_STATUS_JPCW 			= "99";			//向慧通发送机票信息错误 
	public static final String ORDER_LOG_STATUS_JPYC 			= "98"; 		//向慧通发送机票信息异常
	public static final String ORDER_LOG_STATUS_JPOK 			= "100"; 		//向慧通发送机票信息成功 
	public static final String ORDER_LOG_STATUS_ABCNOTIFY  	= "8771";		//农行异步支付; 
	public static final String ORDER_LOG_STATUS_ABCNOTIFYFAIL= "8772";		//农行异步支付失败; 
	public static final String ORDER_LOG_STATUS_ABCNOTIFYOK  	= "8773";		//农行异步支付成功; 
	public static final String ORDER_LOG_STATUS_ORDER_CANCEL 	= "5003";		//机票订单取消
	public static final String ORDER_LOG_STATUS_ORDER_HCANCEL= "5004";		//酒店订单取消


	public static final String URI_CODE_PREFIX_FRONT = "uri.front";  //前台访问地址代码的前缀
	public static final String CACHE_KEY_RESOURCE = "CACHE_KEY_RESOURCE";  //缓存KEY_资源
	public static final String SYS_RESOURCE_FRONT = "SYS_RESOURCE_FRONT";  //缓存OBJECT_KEY

	
	//火车票
	public static final String TRAIN_API_ORDER_TRAINBOOKSEAT =     	"web.order.trainbookseat"; 		//2.​ 前置扣位成功（合作方接收）
	public static final String TRAIN_API_ORDER_CANCELORDER =       	"web.order.cancelOrder"; 		//6.​ 取消消息（合作方接收）
	public static final String TRAIN_API_ORDER_LOCKORDER =         	"web.order.lockOrder"; 			//7.​ 锁定消息（合作方接收）
	public static final String TRAIN_API_ORDER_NOTIFYNOTICKET =   	"web.order.notifyNoTicket"; 	//8.​ 无票消息（合作方接收）   
	public static final String TRAIN_API_ORDER_NOTIFYTICKET =   	 	"web.order.notifyTicket";   	//9.​ 有票消息（合作方接收）  
	public static final String TRAIN_API_ORDER_REQUESTREFUND =   		"web.order.requestRefund"; 		//10.​ 差额退款（合作方接收） 
	public static final String TRAIN_API_ORDER_RETURNTICKETNOTICE = 	"web.order.returnTicketNotice"; //14.​ 退票结果（合作方接收）
//	public static final String TRAIN_API_ORDER_REQUESTREFUND =    			"web.order.requestRefund";		//16.​ 核销退款（合作方接收）


	//火车票系统状态('系统状态(0:正常;1:待处理;2:已处理;)',)
	public static final short TRAIN_SYSTEM_STATUS_ZC  =  0;// ("正常"),
	public static final short TRAIN_SYSTEM_STATUS_DCL =  1;// ("待处理"),
	public static final short TRAIN_SYSTEM_STATUS_YCL =  2;// ("已处理"),		  													

	
	//火车票用户状态 用户状态(1:抢票中;2:提交中;3:占座失败;4:待支付;5:出票中;6:出票成功;7:出票失败已退款;8:已取消;9:已删除)
	public static final short TRAIN_ORDER_STATUS_QPZ 		= 1;// ("抢票中"),
	public static final short TRAIN_ORDER_STATUS_TJZ 	 	= 2;// ("提交中"),
	public static final short TRAIN_ORDER_STATUS_ZZSB 	= 3;// ("占座失败"),
	public static final short TRAIN_ORDER_STATUS_DZF 		= 4;// ("待支付"),
	public static final short TRAIN_ORDER_STATUS_CPZ 		= 5;// ("出票中"),
	public static final short TRAIN_ORDER_STATUS_CPCG 	= 6;// ("出票成功");
	public static final short TRAIN_ORDER_STATUS_CPSB 	= 7;// ("出票失败已退款");
	public static final short TRAIN_ORDER_STATUS_YQX  	= 8;// ("已取消");
	public static final short TRAIN_ORDER_STATUS_YSC 		= 9;// ("已删除");
	
	//火车乘客表状态 状态(1:已出票;2:退票申请中;3:已退票;4:退票失败;)
	public static final short TRAIN_USER_STATUS_YCP 		= 1;// ("已出票"),
	public static final short TRAIN_USER_STATUS_TPSQZ 	= 2;// ("退票申请中"),
	public static final short TRAIN_USER_STATUS_YTP 		= 3;// ("已退票"),
	public static final short TRAIN_USER_STATUS_TPSB 		= 4;// ("退票失败"), 
	
	
	
	
	
	
	
}
