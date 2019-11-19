package cn.enjoy.mall.web.controller;

import cn.enjoy.core.utils.response.HttpResponseBody;
import cn.enjoy.mall.model.KillGoodsPrice;
import cn.enjoy.mall.service.manage.IKillSpecManageService;
import cn.enjoy.sys.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/killgoodsSpec")
public class KillSpecController extends BaseController {

    @Reference
    private IKillSpecManageService iKillSpecManageService;

    @GetMapping("/queryByPage")
    public HttpResponseBody queryByPage(String name){
        return HttpResponseBody.successResponse("ok", iKillSpecManageService.queryByPage(name,1,20));
    }

    @GetMapping("/detail")
    public HttpResponseBody detail(Integer id) {
        return HttpResponseBody.successResponse("ok", iKillSpecManageService.selectByPrimaryKey(id));
    }

    @PostMapping("/save")
    public HttpResponseBody save(KillGoodsPrice killGoodsPrice){
        if (killGoodsPrice.getId() == null || killGoodsPrice.getId() == 0){
            if (iKillSpecManageService.selectCountBySpecGoodId(killGoodsPrice.getSpecGoodsId()) > 0){
                return HttpResponseBody.failResponse("同一商品规格不能重复加入秒杀");
            }
            iKillSpecManageService.save(killGoodsPrice);
        } else {
            KillGoodsPrice killGoods = iKillSpecManageService.selectByPrimaryKey(killGoodsPrice.getId());
            if (killGoods.getStatus() ==1 && killGoods.getBegainTime().getTime() < System.currentTimeMillis()){
                iKillSpecManageService.flushCache(killGoods);
                return HttpResponseBody.failResponse("秒杀已运行，不支持修改");
            }

            iKillSpecManageService.update(killGoodsPrice);
        }

        return HttpResponseBody.successResponse("保存成功");
    }

    @PostMapping("/delete")
    public HttpResponseBody delete(Integer id){
        iKillSpecManageService.delete(id);
        return HttpResponseBody.successResponse("删除成功");
    }



}
