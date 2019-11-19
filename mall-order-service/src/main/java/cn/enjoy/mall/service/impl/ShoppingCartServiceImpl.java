package cn.enjoy.mall.service.impl;

import cn.enjoy.mall.model.SpecGoodsPrice;
import cn.enjoy.mall.vo.GoodsVo;
import com.alibaba.dubbo.config.annotation.Service;
import cn.enjoy.mall.mongo.GoodsDao;
import cn.enjoy.mall.mongo.ShoppingCartDao;
import cn.enjoy.mall.service.IShoppingCartService;
import cn.enjoy.mall.vo.ShoppingGoodsVo;
import cn.enjoy.mall.vo.UserShoppingGoodsVo;
import cn.enjoy.core.exception.BusinessException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zm
 */
@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Resource
    private ShoppingCartDao shoppingCartDao;

    @Resource
    private GoodsDao goodsDao;

    /**
     * 添加商品到购物车
     * @param specGoodsId
     * @param num
     * @param userId
     * @param saveMode
     */
    @Override
    public void save(Integer specGoodsId,Integer num,String userId,Integer saveMode) {
        num = num == null ? 1 : num;
        //1查询mongdb当前用户是否有购物车信息
        UserShoppingGoodsVo userShoppingGoodsVo = shoppingCartDao.findOne(userId);
        if(userShoppingGoodsVo == null){
            userShoppingGoodsVo = new UserShoppingGoodsVo();
        }
        userShoppingGoodsVo.setUserId(userId);
        //2查询mongdb里面具体的商品信息
        GoodsVo goodsVo = goodsDao.findOneBySpecGoodsId(specGoodsId);
        if(goodsVo == null){
            throw new BusinessException("没有找到对应的商品");
        }
        if(goodsVo.getBase().getIsOnSale() == false){
            throw new BusinessException("对不起，该商品已下架");
        }
        List<SpecGoodsPrice> machedSpecGoodsPriceList = goodsVo.getSpecGoodsPriceList().stream().filter(specGoodsPrice -> specGoodsPrice.getId().intValue()==specGoodsId.intValue()).collect(Collectors.toList());
        if(machedSpecGoodsPriceList==null || machedSpecGoodsPriceList.size()==0){
            throw new BusinessException("没有找到对应的商品");
        }
        if(machedSpecGoodsPriceList.get(0).getStoreCount().intValue()<num.intValue()){
            throw new BusinessException("对不起，该商品的库存不足");
        }
        BigDecimal price = machedSpecGoodsPriceList.get(0).getPrice();
        String keyName = machedSpecGoodsPriceList.get(0).getKeyName();

        //取出购物车的商品集合
        List<ShoppingGoodsVo> shoppingGoodsList = userShoppingGoodsVo.getShoppingGoodsList();
        if(shoppingGoodsList == null ){
            shoppingGoodsList = new ArrayList<>();
        }
        //购物车中没有该类型商品，则直接添加
        if(CollectionUtils.isEmpty(shoppingGoodsList)
                || shoppingGoodsList.stream().filter(shoppingGoodsVo -> shoppingGoodsVo.getSpecGoodsId().intValue()== specGoodsId.intValue()).count() == 0 ){
            ShoppingGoodsVo shoppingGoodsVo = new ShoppingGoodsVo();
            shoppingGoodsVo.setSpecGoodsId(specGoodsId);
            shoppingGoodsVo.setGoodsId(goodsVo.getBase().getGoodsId());
            shoppingGoodsVo.setGoodsName(goodsVo.getBase().getGoodsName());
            shoppingGoodsVo.setOriginalImg(goodsVo.getBase().getOriginalImg());
            shoppingGoodsVo.setPrice(price);
            shoppingGoodsVo.setNum(num);
            shoppingGoodsVo.setStatus(1);
            shoppingGoodsVo.setKeyName(keyName);
            shoppingGoodsList.add(shoppingGoodsVo);
            userShoppingGoodsVo.setShoppingGoodsList(shoppingGoodsList);
        }else { //购物车中有该类型商品，则根据保存模式决定累加还是替换
            for (ShoppingGoodsVo vo : shoppingGoodsList ) {
                if (vo.getSpecGoodsId().intValue() == specGoodsId.intValue()) {
                    vo.setGoodsId(goodsVo.getBase().getGoodsId());
                    vo.setGoodsName(goodsVo.getBase().getGoodsName());
                    vo.setOriginalImg(goodsVo.getBase().getOriginalImg());
                    vo.setPrice(price);
                    if(saveMode == SAVE_MODE_APPEND.intValue()){
                        vo.setNum(vo.getNum() + num);
                        vo.setStatus(1);
                    }else{
                        vo.setNum(num);
                    }
                    break;
                }
            }
            userShoppingGoodsVo.setShoppingGoodsList(shoppingGoodsList);
        }
        shoppingCartDao.save(userShoppingGoodsVo);
    }

    @Override
    public void remove(Integer specGoodsId,String userId) {
        UserShoppingGoodsVo userShoppingGoodsVo = shoppingCartDao.findOne(userId);
        if(userShoppingGoodsVo == null || userShoppingGoodsVo.getShoppingGoodsList()==null){
            return;
        }else{
            for (ShoppingGoodsVo vo : userShoppingGoodsVo.getShoppingGoodsList()) {
                if (vo.getSpecGoodsId().intValue() == specGoodsId.intValue()) {
                    userShoppingGoodsVo.getShoppingGoodsList().remove(vo);
                    break;
                }
            }
            shoppingCartDao.save(userShoppingGoodsVo);
        }
    }

    @Override
    public void removeAll(String userId) {
        shoppingCartDao.removeByUserId(userId);
    }

    @Override
    public List<ShoppingGoodsVo> list(String userId) {
        UserShoppingGoodsVo vo = shoppingCartDao.findOne(userId);
        if(vo!=null){
            return vo.getShoppingGoodsList();
        }else{
            return null;
        }
    }

    @Override
    public ShoppingGoodsVo findBySpecGoodsId(Integer specGoodsId, String userId) {
        UserShoppingGoodsVo userShoppingGoodsVo = shoppingCartDao.findOne(userId);
        if(userShoppingGoodsVo==null || CollectionUtils.isEmpty(userShoppingGoodsVo.getShoppingGoodsList())){
            return null;
        }
        List<ShoppingGoodsVo> shoppingGoodsList =
                userShoppingGoodsVo.getShoppingGoodsList().stream().filter(shoppingGood -> shoppingGood.getSpecGoodsId().intValue()==specGoodsId.intValue()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(shoppingGoodsList)){
            return null;
        }else{
            return shoppingGoodsList.get(0);
        }
    }

    @Override
    public void updateStatus(String specGoodsIds, Integer status, String userId) {
        UserShoppingGoodsVo userShoppingGoodsVo = shoppingCartDao.findOne(userId);
        if(userShoppingGoodsVo == null || CollectionUtils.isEmpty(userShoppingGoodsVo.getShoppingGoodsList())){
            throw new BusinessException("没有找到对应的商品");
        }
        String[] specGoodsIdArray = specGoodsIds.split("_");
        if(specGoodsIdArray.length==0){
            throw new BusinessException("没有找到对应的商品");
        }
        List<String> specGoodsIdList = Arrays.asList(specGoodsIds.split("_"));
        List<ShoppingGoodsVo> shoppingGoodsList = userShoppingGoodsVo.getShoppingGoodsList();
        shoppingGoodsList.stream().filter(shoppingGoodsVo -> specGoodsIdList.contains(shoppingGoodsVo.getSpecGoodsId()+""))
                .forEach(shoppingGoodsVo -> shoppingGoodsVo.setStatus(status));
        userShoppingGoodsVo.setShoppingGoodsList(shoppingGoodsList);
        shoppingCartDao.save(userShoppingGoodsVo);
    }

    @Override
    public List<ShoppingGoodsVo> findCheckedGoodsList(String userId){
        List<ShoppingGoodsVo> checkedShoppingGoodsList = null;
        UserShoppingGoodsVo userShoppingGoodsVo = shoppingCartDao.findOne(userId);
        if(userShoppingGoodsVo != null && !CollectionUtils.isEmpty(userShoppingGoodsVo.getShoppingGoodsList())){
            checkedShoppingGoodsList = userShoppingGoodsVo.getShoppingGoodsList()
                    .stream().filter( x -> x.getStatus() == 1 )
                    .collect(Collectors.toList());
        }
        return checkedShoppingGoodsList;
    }

    /**
     * 清除购物车内容
     * @param userId
     */
    @Override
    public void removeCheckedGoodsList(String userId){
        List<ShoppingGoodsVo> unCheckedShoppingGoodsList = null;
        UserShoppingGoodsVo userShoppingGoodsVo = shoppingCartDao.findOne(userId);
        if(userShoppingGoodsVo != null && !CollectionUtils.isEmpty(userShoppingGoodsVo.getShoppingGoodsList())){
            unCheckedShoppingGoodsList = userShoppingGoodsVo.getShoppingGoodsList()
                    .stream().filter( x -> x.getStatus() != 1 )
                    .collect(Collectors.toList());
        }
        userShoppingGoodsVo.setShoppingGoodsList(unCheckedShoppingGoodsList);
        shoppingCartDao.save(userShoppingGoodsVo);
    }
}
