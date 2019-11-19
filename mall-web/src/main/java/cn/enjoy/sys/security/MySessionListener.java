package cn.enjoy.sys.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ray
 * @date 2018/3/30.
 */
public class MySessionListener implements SessionListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStart(Session session) {
        logger.info("Session {} 被创建", session.getId());
        MySessionContext.addSession(session);
    }

    @Override
    public void onStop(Session session) {
        logger.info("Session {} 被销毁", session.getId());
        MySessionContext.delSession(session);
    }

    @Override
    public void onExpiration(Session session) {

    }

}
