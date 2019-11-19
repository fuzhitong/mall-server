package cn.enjoy.mall.web.controller;

import cn.enjoy.mall.model.Goods;
import cn.enjoy.mall.model.GoodsAttribute;
import cn.enjoy.mall.model.Spec;
import cn.enjoy.sys.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.mall.service.IGoodsService;
import cn.enjoy.mall.vo.GoodsSpecAttrVo;
import cn.enjoy.mall.vo.GoodsSpecPriceAttrVo;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 商品相关
 */
@RestController
@RequestMapping("/api/goods")
public class GoodsContoller extends BaseController {
    @Reference
    private IGoodsService goodsService;

    /**
     * 根据关键字搜索商品
     *
     * @param param
     * @return
     */
    @GetMapping("searchList")
    public HttpResponseBody searchList(Integer catId, String param, int page, int pageSize,String sidx,String sord){
        return HttpResponseBody.successResponse("ok", goodsService.queryListPageFromDB(catId,param, page, pageSize, sidx, sord));
    }

    /**
     * 商品详情
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/detail")
    public HttpResponseBody detail(Integer goodsId) {
        return HttpResponseBody.successResponse("ok", goodsService.queryDetailFromDB(goodsId));
    }

    /**
     * 保存商品到数据库
     *
     * @param goods
     * @return
     */
    @PostMapping("save")
    public HttpResponseBody save(Goods goods) {
        return HttpResponseBody.successResponse("ok", goodsService.save(goods));
    }

    /**
     * 删除商品
     *
     * @param goodsId
     * @return
     */
    @PostMapping("delete")
    public HttpResponseBody delete(Integer goodsId) {
        return HttpResponseBody.successResponse("ok", goodsService.delete(goodsId));
    }

    /**
     * 批量删除商品
     *
     * @param ids
     * @return
     */
    @PostMapping("batchDelete")
    public HttpResponseBody batchDelete(Integer[] ids) {
        return HttpResponseBody.successResponse("ok", goodsService.deteteBatch(ids));
    }

    /**
     * 保存商品图片
     *
     * @param imageUrls
     * @param goodsId
     * @return
     */
    @PostMapping("saveGoodsImage")
    public HttpResponseBody saveGoodsImage(String[] imageUrls, Integer goodsId) {
        return HttpResponseBody.successResponse("ok", goodsService.saveGoodsImages2DB(imageUrls, goodsId));
    }

    /**
     * 保存商品规格和属性
     *
     * @param specPriceAttrVo
     * @return
     */
    @PostMapping("saveGoodsSpecAndAttr")
    public HttpResponseBody saveGoodsSpecAndAttr(@RequestBody GoodsSpecPriceAttrVo specPriceAttrVo) {
        goodsService.saveGoodsSpecAndAttr2DB(specPriceAttrVo);
        return HttpResponseBody.successResponse("保存成功");
    }

    /**
     * 根据类型查找商品规格和属性
     *
     * @param type
     * @return
     */
    @GetMapping("querySpecAndAttrByType")
    public HttpResponseBody querySpecAndAttrByType(Integer goodsId, Short type) {
        ExecutorService service  = Executors.newFixedThreadPool(2);

        Callable<List<Spec>> specListCallable = ()->goodsService.querySpecListByGoodsIdAndTypeIdFromDB(goodsId, type);
        Callable<List<GoodsAttribute>> attributeListCallable = () ->goodsService.queryGoodsAttributeListByGoodsIdAndTypeFromDB(goodsId, type);

        FutureTask<List<Spec>> specListTask = new FutureTask<List<Spec>>(specListCallable);
        FutureTask<List<GoodsAttribute>> attributeListTask = new FutureTask<List<GoodsAttribute>>(attributeListCallable);

        service.submit(specListTask);
        service.submit(attributeListTask);

        GoodsSpecAttrVo vo = new GoodsSpecAttrVo();
        vo.setGoodsId(goodsId);
        try {
            vo.setSpecList(specListTask.get());
            vo.setAttributeList(attributeListTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return HttpResponseBody.successResponse("ok", vo);
    }

    /**
     * 发布所有商品
     *
     * @return
     */
    @PostMapping("publishAll")
    public HttpResponseBody publishAll() {
        goodsService.publishAll2MongoDB();
        return HttpResponseBody.successResponse("ok");
    }

    /**
     * 发布所有商品
     *
     * @return
     */
    @PostMapping("publish")
    public HttpResponseBody publish(Integer goodsId) {
        goodsService.publishGoods2MongoDB(goodsId);
        return HttpResponseBody.successResponse("ok");
    }
}