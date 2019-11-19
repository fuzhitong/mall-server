package cn.enjoy.mall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Ray
 * @date 2018/2/9.
 */
@Component
public class SysPropUtil {

    private static String goodsPicDomain;
    private static String fdfsDomain;

    public static String getPicDomain(String originalImg) {

        if(originalImg == null || originalImg.startsWith("http")){
            return null;
        } else {
            if( originalImg.startsWith("/public")){
                return goodsPicDomain + originalImg;
            } else {
                return "http://" + fdfsDomain + originalImg;
            }
        }
    }

    @Value("${goods.pic.domain}")
    public void setGoodsPicDomain(String goodsPicDomain) {
        SysPropUtil.goodsPicDomain = goodsPicDomain;
    }
    @Value("${dfs.url}")
    public void setFdfsDomain(String fdfsDomain) {
        SysPropUtil.fdfsDomain = fdfsDomain;
    }
}
