/*
  Created on 2015年7月9日 by Jack
 */
package cn.enjoy.core.utils.response;

import cn.enjoy.core.exception.BusinessException;

import java.io.Serializable;


public class HttpResponseBody<E> implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5406928126690333502L;
	
	private String code;
	
	private String msg;
	
	private E data;
	
	public HttpResponseBody(){
	}
	
	public HttpResponseBody(String code, String msg, E data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public HttpResponseBody(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}


	public static HttpResponseBody successResponse(String message) {
		return new HttpResponseBody(ResponseCodeConstant.SUCCESS, message);
	}

	public static <T> HttpResponseBody<T> successResponse(String message,T singleData) {
		return new HttpResponseBody<>(ResponseCodeConstant.SUCCESS, message,singleData);
	}

	public static HttpResponseBody failResponse(String message) {
		//message可能长这样：
		/* 你没看错，就是有两个 “此手机号码已经被注册”
		* cn.enjoy.core.exception.BusinessException: ~此手机号码已经被注册
   cn.enjoy.core.exception.BusinessException: ~此手机号码已经被注册
    at com.pzh.sys.service.impl.UserServiceImpl.register(UserServiceImpl.java:536)
	at com.pzh.sys.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$c0ec0773.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
	...
		* */
		String delimiter = BusinessException.resCodeSplitFlat;
		if(message != null && message.contains(delimiter)){
			//这样切了之后长这样：此手机号码已经被注册\ncn.enjoy.core.exception.BusinessException:
			message = message.split(delimiter)[1];
		}
		//把异常消息的小尾巴去掉(后面那一截英文)
		if(message != null){
			//切了之后长这样：此手机号码已经被注册\n
			message = message.split("cn.hehuoren.core.exception")[0].trim();
		}
		return new HttpResponseBody(ResponseCodeConstant.FAIL, message);
	}

}

