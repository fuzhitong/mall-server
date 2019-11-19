package cn.enjoy.config;

import cn.enjoy.core.exception.BusinessException;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author chenlin
 * @date 2017/4/10
 */
@ControllerAdvice
public class ErrorControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object errorHandle(Exception e){
        String errMsg = e.getMessage();
        if(e instanceof BusinessException || (e instanceof RuntimeException && errMsg.contains("BusinessException"))){
            logger.error(errMsg);
            String delimiter = BusinessException.resCodeSplitFlat;
            if(errMsg != null && errMsg.contains(delimiter)){
                //这样切了之后长这样：此手机号码已经被注册\ncn.enjoy.core.exception.BusinessException:
                errMsg = errMsg.split(delimiter)[1];
            }
            //把异常消息的小尾巴去掉(后面那一截英文)
            if(errMsg != null){
                //切了之后长这样：此手机号码已经被注册\n
                errMsg = errMsg.split("cn.hehuoren.core.exception")[0].trim();
            }
        }else {
            logger.error("", e);
        }
        return HttpResponseBody.failResponse(errMsg);
    }


}
