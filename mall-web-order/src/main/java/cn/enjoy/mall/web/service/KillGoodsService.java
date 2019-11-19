package cn.enjoy.mall.web.service;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.constant.KillConstants;
import cn.enjoy.mall.service.manage.IKillSpecManageService;
import cn.enjoy.mall.vo.KillGoodsSpecPriceDetailVo;
import cn.enjoy.mall.vo.KillOrderVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀商品相关
 */
@Service
public class KillGoodsService  {

    @Reference
    private IKillSpecManageService iKillSpecManageService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SecKillSender secKillSender;

    public GridModel<KillGoodsSpecPriceDetailVo> queryByPage(){
        GridModel<KillGoodsSpecPriceDetailVo> gridModel = (GridModel)redisTemplate.opsForValue().get(KillConstants.KILLGOODS_LIST);
        if (null != gridModel){
            return gridModel;
        }

        gridModel = iKillSpecManageService.queryView(1,100);
        if (null != gridModel){
            redisTemplate.opsForValue().set(KillConstants.KILLGOODS_LIST,gridModel,50000, TimeUnit.MILLISECONDS);//set缓存
        }
        return gridModel;
    }

    public KillGoodsSpecPriceDetailVo detail(Integer id) {
        String killgoodDetail = KillConstants.KILLGOOD_DETAIL+id;
        KillGoodsSpecPriceDetailVo killGoodsPrice = (KillGoodsSpecPriceDetailVo)redisTemplate.opsForValue()
                .get(killgoodDetail);
        if (null != killGoodsPrice){
            System.out.println("缓存中得到数据");
            return killGoodsPrice;
        }
        System.out.println("dubbo service");
        killGoodsPrice = iKillSpecManageService.detailById(id);
        if (null != killGoodsPrice){
            redisTemplate.opsForValue().set(killgoodDetail,killGoodsPrice,50000, TimeUnit.MILLISECONDS);//set缓存
        }
        return killGoodsPrice;
    }

    public boolean kill(String killId,String userId) {

        final String killGoodCount = KillConstants.KILL_GOOD_COUNT+killId;
        try {
            long count = redisTemplate.opsForValue().increment(killGoodCount,-1);
            Object obj = redisTemplate.execute(new SessionCallback() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {

                    operations.watch(killGoodCount);
                    Object val = operations.opsForValue().get(killGoodCount);
                    int valint = Integer.valueOf(val.toString());

                    if (valint > 0){
                        operations.multi();
                        operations.opsForValue().increment(killGoodCount,-1);
                        Object rs = operations.exec();
                        System.out.println(rs);
                        return rs;
                    }

                    return null;
                }
            });
            if (null != obj){
                redisTemplate.opsForSet().add(KillConstants.KILLGOOD_USER,killId+userId);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean secKill(int killId,String userId) {
        long is = redisTemplate.opsForSet().add(KillConstants.KILLED_GOOD_USER+killId,userId);
        if (is == 0){//判断用户已经秒杀过，直接返回当次秒杀失败
//            return false;
        }

        final String killGoodCount = KillConstants.KILL_GOOD_COUNT+killId;
        if(redisTemplate.opsForValue().increment(killGoodCount,-1) < 0){
            return false;
        }

        //秒杀成功，缓存秒杀用户和商品
        redisTemplate.opsForSet().add(KillConstants.KILLGOOD_USER,killId+userId);
        return true;
    }

    public boolean chkKillOrder(String killId,String userId) {
        //校验用户和商品是否有缓存，无则表明当前是非法请求
        boolean isKilld = redisTemplate.opsForSet().isMember(KillConstants.KILLGOOD_USER,killId+userId);
        if (isKilld){
            redisTemplate.opsForSet().remove(KillConstants.KILLGOOD_USER,killId+userId);
        }
        return isKilld;
    }

    public String submitOrder(int addressId, int killId,String userId){
        KillGoodsSpecPriceDetailVo killGoods = detail(killId);

        KillOrderVo vo = new KillOrderVo();
        vo.setUserId(userId);
        vo.setKillGoodsSpecPriceDetailVo(killGoods);
        vo.setAddressId(addressId);

        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();

        //订单有效时间3秒
        String kill_order_user = KillConstants.KILL_ORDER_USER+killId+userId;
        valueOperations.set(kill_order_user,KillConstants.KILL_ORDER_USER_UNDO,3000,TimeUnit.MILLISECONDS);
        /*同步转异步，发送到消息队列*/
        secKillSender.send(vo);

        String orderId = "";
        try {
            while (true){
                orderId = valueOperations.get(KillConstants.KILL_ORDER_USER+killId+userId);
                if (null == orderId){//处理超时，则直接置秒杀失败，取消秒杀订单
                    return null;
                }
                if (!KillConstants.KILL_ORDER_USER_UNDO.equals(orderId)){//订单已处理成功
                    stringRedisTemplate.delete(kill_order_user);
                    return orderId.toString();//
                }
                Thread.sleep(300l);//300ms轮循1次
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
