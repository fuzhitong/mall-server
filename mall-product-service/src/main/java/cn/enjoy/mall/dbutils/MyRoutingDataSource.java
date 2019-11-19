package cn.enjoy.mall.dbutils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：自定义的数据源,充当了真正的的路由中介
 */
public class MyRoutingDataSource  extends AbstractRoutingDataSource {
    /*在运行时, 根据某种规则，比如key值，比如当前线程的id
    来动态切换到真正的DataSource上*/
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
