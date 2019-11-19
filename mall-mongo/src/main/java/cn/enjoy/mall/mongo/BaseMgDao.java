package cn.enjoy.mall.mongo;

import cn.enjoy.core.utils.GridModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author Ray
 * @date 2018/2/5.
 */
public abstract class BaseMgDao {

    abstract String getCollectionName();

    @Resource
    protected MongoTemplate mongoTemplate;

    public <T> GridModel<T> queryPage(Integer page, Integer size, Class<T> entityClass, Query query) {
        if( page.intValue() == 0 ){
            page = 1;
        }
        if( size.intValue() == 0 ){
            size = 10;
        }
        //Query query = new Query(Criteria.where("status").exists(false));
        //查询总数
        int count = (int) mongoTemplate.count(query, entityClass, getCollectionName());
        GridModel<T> gridModel = new GridModel<T>();
        gridModel.setPage(page);
        gridModel.setRecords(count);
        if(count > 0) {
            gridModel.setTotal(count / size + 1);
        }

        //排序
        //query.with(new Sort(Sort.Direction.ASC, "listing.id"));
        query.skip((page - 1 )*size).limit(size);
        List<T> datas = mongoTemplate.find(query, entityClass, getCollectionName());
        gridModel.setRows(datas);
        return gridModel;
    }

    public void insert(Object object){
        mongoTemplate.insert(object, getCollectionName());
    }

    public void save(Object object){
        mongoTemplate.save(object, getCollectionName());
    }

    public void insert(Collection<? extends Object> batchToSave){
        mongoTemplate.insert(batchToSave, getCollectionName());
    }

    public void remove(Query query){
        mongoTemplate.remove(query, getCollectionName());
    }

    public <T> List<T> find(Query query, Class<T> entityClass){
        return mongoTemplate.find(query, entityClass, getCollectionName());
    }

    public long count(Query query){
        return mongoTemplate.count(query, getCollectionName());
    }
}
