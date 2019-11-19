package cn.enjoy.mall.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import cn.enjoy.mall.service.IShoppingCartService;
import cn.enjoy.mall.vo.ShopppingGoodsAddVo;
import cn.enjoy.sys.controller.BaseController;
import cn.enjoy.core.utils.response.HttpResponseBody;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车相关
 */
@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartContoller  extends BaseController {
    @Reference
    private IShoppingCartService shoppingCartService;

    /**
     * 添加到购物车
     * @param shopppingGoodsAddVo
     * @return
     */
    @PostMapping("add")
    public HttpResponseBody add(ShopppingGoodsAddVo shopppingGoodsAddVo){
        shoppingCartService.save(shopppingGoodsAddVo.getSpecGoodsId(),shopppingGoodsAddVo.getNum(),getSessionUserId(),IShoppingCartService.SAVE_MODE_APPEND);
        return HttpResponseBody.successResponse("ok");
    }

    /**
     * 修改购物车中的商品数量
     * @param shopppingGoodsAddVo
     * @return
     */
    @PostMapping("update")
    public HttpResponseBody update(ShopppingGoodsAddVo shopppingGoodsAddVo ){
        shoppingCartService.save(shopppingGoodsAddVo.getSpecGoodsId(), shopppingGoodsAddVo.getNum() ,getSessionUserId(),IShoppingCartService.SAVE_MODE_UPDATE);
        return HttpResponseBody.successResponse("ok");
    }


    /**
     * 根据specGoodsId删除购物车的内容
     * @param shopppingGoodsAddVo
     * @return
     */
    @PostMapping("remove")
    public HttpResponseBody remove(ShopppingGoodsAddVo shopppingGoodsAddVo  ){
        shoppingCartService.remove(shopppingGoodsAddVo.getSpecGoodsId(),getSessionUserId());
        return HttpResponseBody.successResponse("ok");
    }

    /**
     * 清空购物车
     * @return
     */
    @GetMapping("removeAll")
    public HttpResponseBody removeAll(){
        shoppingCartService.removeAll(getSessionUserId());
        return HttpResponseBody.successResponse("ok");
    }

    /**
     * 查询购物车内容
     * @return
     */
    @GetMapping("list")
    public HttpResponseBody list(){
        return HttpResponseBody.successResponse("ok",  shoppingCartService.list(getSessionUserId()));
    }
    /**
     * 查询购物车中已选中的商品列表
     * @return
     */
    @GetMapping("getCheckedGoodsList")
    public HttpResponseBody getCheckedGoodsList(){
        return HttpResponseBody.successResponse("ok",  shoppingCartService.findCheckedGoodsList(getSessionUserId()));
    }

    @GetMapping("findBySpecGoodsId")
    public HttpResponseBody findBySpecGoodsId(@RequestParam Integer specGoodsId){
        return HttpResponseBody.successResponse("ok",  shoppingCartService.findBySpecGoodsId (specGoodsId , getSessionUserId()));
    }

    @PostMapping("updateStatus")
    public HttpResponseBody updateStatus(@RequestParam String specGoodsIds,@RequestParam Integer status){
        shoppingCartService.updateStatus(specGoodsIds , status, getSessionUserId());
        return HttpResponseBody.successResponse("ok");
    }
}
