package cn.enjoy.mall.service.impl;

import cn.enjoy.lock.Lock;
import cn.enjoy.mall.constant.KillConstants;
import cn.enjoy.mall.constant.RedisKey;
import cn.enjoy.mall.dao.ScheduledMapper;
import cn.enjoy.mall.model.HotSellingGoods;
import cn.enjoy.mall.service.IScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
@Service
public class ScheduledServiceImpl implements IScheduledService {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Resource
    private ScheduledMapper scheduledMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private  Lock zkLock;

    private final String HOT_LOCK_PATH = "/hotLock";
    private final String KILL_LOCK_PATH = "/killLock";

    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduledHotProduct() {
            if(zkLock.tryLock(HOT_LOCK_PATH)){//获取分布式锁
               try {
                   //System.out.println("任务1，当前时间：" + dateFormat.format(new Date()));
                   List<HotSellingGoods> hotSellingGoodsList = new ArrayList<>();
                   //获取热门商品信息
                   hotSellingGoodsList =scheduledMapper.hotProduct(5);
                   scheduledMapper.deleteByAll();
                   //使用redis统计商品的销量
                   redisTemplate.delete(RedisKey.GOODS_SALE_NUM);
                   for(HotSellingGoods hotSellingGoods : hotSellingGoodsList){
                       //System.out.println("ID:"+map.get("goods_id").toString()+",num:"+map.get("salessum"));
                       scheduledMapper.insert(hotSellingGoods);
                       redisTemplate.opsForZSet().incrementScore(RedisKey.GOODS_SALE_NUM,hotSellingGoods.getGoodsId()+"",hotSellingGoods.getSalesSum());
                   }
               }finally {
                   zkLock.unLock(HOT_LOCK_PATH);
               }
            }
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    public void scheduledKill() {

        if(zkLock.tryLock(KILL_LOCK_PATH)){//获取分布式锁
            System.out.println("scheduledKill，当前时间：" + dateFormat.format(new Date()));
            try {
                //获取失效秒杀商品
                List<Integer> ids =scheduledMapper.getInvalidKill();
                if (null != ids && ids.size() > 0){
                    //置状态为下架状态
                    scheduledMapper.disableKill(ids);
                    scheduledMapper.cancelKillOrder(ids);
                    //清缓存
                    redisTemplate.delete(KillConstants.KILLGOODS_LIST);
                }
            }finally {
                zkLock.unLock(KILL_LOCK_PATH);
            }
        }
    }
}
