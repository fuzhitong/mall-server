package cn.enjoy.mall.mongo;

import cn.enjoy.mall.constant.CollectionConstants;
import cn.enjoy.mall.vo.GoodsVo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @date 2018/2/5.
 */
@Repository
public class GoodsDao extends BaseMgDao{

    @Override
    String getCollectionName() {
        return CollectionConstants.GOODS;
    }


    public GoodsVo findOneById(Integer id) {
        return mongoTemplate.findOne(new Query(Criteria.where("base.goodsId").is(id)), GoodsVo.class, getCollectionName());
    }

    public GoodsVo findOneBySpecGoodsId(Integer specGoodsId) {
        return mongoTemplate.findOne(new Query(Criteria.where("specGoodsPriceList._id").is(specGoodsId)), GoodsVo.class, getCollectionName());
    }

    public <T> List<T> findList(Query query, Class<T> tClass) {
        return mongoTemplate.find(query, tClass, getCollectionName());
    }

    public List<GoodsVo> findByCategory(Integer catId) {
        return mongoTemplate.find(new Query(Criteria.where("base.catId").is(catId)), GoodsVo.class, getCollectionName());
    }

    public long countByCategory(Integer catId){
        return mongoTemplate.count(new Query(Criteria.where("base.catId").is(catId)), getCollectionName());
    }

    public int countByName(String goodsName){
        return (int) mongoTemplate.count(new Query(Criteria.where("base.goodsName").regex(goodsName)), getCollectionName());
    }

    public void updateById(Integer goodsId, Map<String, Object> params) {
        mongoTemplate.upsert(new Query(Criteria.where("base.goodsId").is(goodsId)), new Update().set("base.isOnSale", params.get("isOnSale")), GoodsVo.class, getCollectionName());
    }

    public void remove(Integer goodsId ) {
        mongoTemplate.remove(new Query(Criteria.where("base.goodsId").is(goodsId)), Map.class, getCollectionName());
    }

    public void removeAll( ) {
        mongoTemplate.dropCollection(getCollectionName());
    }
}
