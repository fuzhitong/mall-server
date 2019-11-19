package cn.enjoy.mall.web.controller;

import cn.enjoy.core.utils.response.HttpResponseBody;
import cn.enjoy.mall.service.IOrderService;
import cn.enjoy.mall.vo.KillGoodsSpecPriceDetailVo;
import cn.enjoy.mall.web.service.KillGoodsService;
import cn.enjoy.sys.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀商品相关
 */
@RestController
@RequestMapping("/api/killgoodsSpec")
public class KillGoodsContoller extends BaseController {
    @Autowired
    private KillGoodsService killGoodsService;
    @Reference
    private IOrderService orderService;

    @GetMapping("/queryByPage")
    public HttpResponseBody queryByPage(){
        return HttpResponseBody.successResponse("ok", killGoodsService.queryByPage());
    }

    @GetMapping("/detail")
    public HttpResponseBody detail(Integer id) {
        return HttpResponseBody.successResponse("ok", killGoodsService.detail(id));
    }

    @PostMapping("kill")
    public HttpResponseBody kill(int killId){
        KillGoodsSpecPriceDetailVo killGoods = killGoodsService.detail(killId);
        if (killGoods.getBegainTime().getTime() > System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购还未开始");
        }
        if (killGoods.getEndTime().getTime() < System.currentTimeMillis()){
            return HttpResponseBody.failResponse("抢购已结束");
        }
        if (!killGoodsService.secKill(killId,getSessionUserId())){
            return HttpResponseBody.failResponse("抢购失败");
        }

        return HttpResponseBody.successResponse("ok",  killGoods);
    }

    @PostMapping("submit")
    public HttpResponseBody submit(int addressId, int killId){
        if (!killGoodsService.chkKillOrder(String.valueOf(killId),getSessionUserId())){
            return HttpResponseBody.failResponse("请先抢购");
        }
//        KillGoodsSpecPriceDetailVo killGoods = killGoodsService.detail(killId);

        //创建秒杀订单
//        Integer orderId = orderService.killOrder(addressId,killGoods,getSessionUserId());
        /*发送到消息队列*/
//        secKillSender.send(addressId,killGoods,getSessionUserId());

        String orderId = killGoodsService.submitOrder(addressId,killId,getSessionUserId());
        if (null ==orderId){
            return HttpResponseBody.failResponse("抢购失败");
        }
        return HttpResponseBody.successResponse("ok",orderId);
    }

}
