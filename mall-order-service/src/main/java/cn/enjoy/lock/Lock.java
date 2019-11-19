package cn.enjoy.lock;

/**
 * Created by VULCAN on 2018/9/20.
 */
public interface Lock {
    //获取到锁的资源
     boolean tryLock(String path);

    // 释放锁
     void unLock(String path);
}
