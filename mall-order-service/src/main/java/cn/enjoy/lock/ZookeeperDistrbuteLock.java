package cn.enjoy.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created by VULCAN on 2018/9/20.
 */


public class ZookeeperDistrbuteLock  implements Lock{
    private  ZkClient zkClient = null;


    public ZookeeperDistrbuteLock(String url) {
        if(url.startsWith("zookeeper://")) {
            url = url.split("//")[1];
        }
        zkClient = new ZkClient(url);
    }

    //尝试获得锁
    public  boolean tryLock(String path ) {
        try {
            zkClient.createEphemeral(path);
            return true;
        } catch (Exception e) {
            //如果创建失败报出异常
//			e.printStackTrace();
            return false;
        }
    }

    public void unLock(String path ) {
        //释放锁
        if (zkClient != null) {
            zkClient.delete(path);
           // zkClient.close();
           // System.out.println("释放锁资源...");
        }
    }
}