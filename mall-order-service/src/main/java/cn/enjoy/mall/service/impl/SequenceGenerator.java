package cn.enjoy.mall.service.impl;


import cn.enjoy.core.utils.DateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * 单据号生成器
 * Created by Ray on 2017/2/17.
 */
@Service
public class SequenceGenerator {
    @Resource
    private RedisTemplate redisTemplate;

    protected final static String CACHEKEY_PREFIX = "SEQ_";
    protected final static String ORDER = "ORDER";

    public String getNext(String prefix){
        return getNext(prefix, 0);
    }

    /**
     * Get next string.
     *
     * @param prefix the prefix
     * @param digits the digits 序列的位数
     * @return the string
     */
    public String getNext(String prefix, int digits){
        String pattern = "";
        if(digits > 0){
            for (int i = 0; i < digits; i++) {
                 pattern += "0";
            }
        } else {
            pattern = "000";
        }
        NumberFormat nf = new DecimalFormat(pattern);
        return prefix + nf.format(redisTemplate.opsForValue().increment (CACHEKEY_PREFIX + prefix, -1));
    }

    public String getNextByMonth(String prefix){
        String date = DateUtil.getNow("yyMM");
        NumberFormat nf = new DecimalFormat("000");
        return prefix + date + nf.format(redisTemplate.opsForValue().increment(CACHEKEY_PREFIX + prefix + date, 31 * 24 * 3600));
    }

    /**
     * Get next by day string.
     *
     * @param prefix the prefix
     * @return the string
     *
     */
    public String getNextByDay(String prefix){
        String date = DateUtil.getNow("yyyyMMdd");
        NumberFormat nf = new DecimalFormat("00000000");
        String key = CACHEKEY_PREFIX + prefix + date;
        String seqNo =  date + nf.format(redisTemplate.opsForValue().increment(key, 1));
        redisTemplate.expire(key,24 * 3600, TimeUnit.SECONDS);
        return seqNo;
    }

    /**
     * 获取订单号
     * @return
     */
    public String getOrderNo(){
        return getNextByDay(ORDER);
    }

}
