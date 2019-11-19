package cn.enjoy.mall.service.impl.manage;

import cn.enjoy.core.utils.GridModel;
import cn.enjoy.mall.constant.KillConstants;
import cn.enjoy.mall.dao.KillGoodsPriceMapper;
import cn.enjoy.mall.model.KillGoodsPrice;
import cn.enjoy.mall.service.manage.IKillSpecManageService;
import cn.enjoy.mall.vo.KillGoodsSpecPriceDetailVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class KillGoodsManageServiceImpl implements IKillSpecManageService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private KillGoodsPriceMapper killGoodsPriceMapper;

    @Override
    public int delete(Integer id) {
        //清缓存
        stringRedisTemplate.delete(KillConstants.KILLGOODS_LIST);
        stringRedisTemplate.delete(KillConstants.KILL_GOOD_COUNT+id);
        return killGoodsPriceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int save(KillGoodsPrice record) {
        int ret = killGoodsPriceMapper.insert(record);
        if (ret > 0){//当前秒杀配置成功，配置秒杀虚拟库存
            final String killGoodCount = KillConstants.KILL_GOOD_COUNT+record.getId();

            //清缓存
            stringRedisTemplate.delete(KillConstants.KILLGOODS_LIST);
            //失效时间
            long expireTime = record.getEndTime().getTime() - System.currentTimeMillis();
            if (expireTime > 0){
                stringRedisTemplate.opsForValue().set(killGoodCount,record.getKillCount().toString(),expireTime, TimeUnit.MILLISECONDS);
            } else {
                stringRedisTemplate.delete(killGoodCount);
            }
        }
        return ret;
    }

    @Override
    public int selectCountBySpecGoodId(Integer specGoodsId) {
        return killGoodsPriceMapper.selectCountBySpecGoodId(specGoodsId);
    }

    @Override
    public KillGoodsPrice selectByPrimaryKey(Integer id) {
        return killGoodsPriceMapper.selectByPrimaryKey(id);
    }

    @Override
    public KillGoodsSpecPriceDetailVo detailBySpecGoodId(Integer specGoodsId) {
        return killGoodsPriceMapper.detailBySpecGoodId(specGoodsId);
    }

    public KillGoodsSpecPriceDetailVo detailById(Integer id) {
        return killGoodsPriceMapper.detail(id);
    }

    @Override
    public int update(KillGoodsPrice record) {
        int ret =  killGoodsPriceMapper.updateByPrimaryKey(record);
        if (ret > 0){//当前秒杀配置成功，配置秒杀虚拟库存
            flushCache(record);
        }
        return ret;
    }

    public void flushCache(KillGoodsPrice record) {
        final String killGoodCount = KillConstants.KILL_GOOD_COUNT + record.getId();

        //清缓存
        stringRedisTemplate.delete(KillConstants.KILLGOODS_LIST);
        //失效时间
        long expireTime = record.getEndTime().getTime() - System.currentTimeMillis();
        if (expireTime > 0) {
            stringRedisTemplate.opsForValue().set(killGoodCount, record.getKillCount().toString(), expireTime, TimeUnit.MILLISECONDS);
        } else {
            stringRedisTemplate.delete(killGoodCount);
        }
    }

    @Override
    public GridModel<KillGoodsSpecPriceDetailVo> queryByPage(String name,int page, int pageSize) {
        PageBounds pageBounds = new PageBounds(page, pageSize);
        return new GridModel<>(killGoodsPriceMapper.select(name,pageBounds));
    }

    public GridModel<KillGoodsSpecPriceDetailVo> queryView(int page, int pageSize) {
        PageBounds pageBounds = new PageBounds(page, pageSize);
        return new GridModel<>(killGoodsPriceMapper.selectView(pageBounds));
    }
}
