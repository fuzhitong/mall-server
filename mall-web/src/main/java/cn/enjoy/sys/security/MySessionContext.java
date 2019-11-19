package cn.enjoy.sys.security;

import org.apache.shiro.session.Session;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ray
 * @date 2018/3/30.
 */
public class MySessionContext {

    private static Map<Serializable, Session> mymap = new HashMap<>();

    public static synchronized void addSession(Session session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static synchronized void delSession(Session session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    public static synchronized void delSession(String sessionId) {
        if (sessionId != null) {
            mymap.remove(sessionId);
        }
    }

    public static synchronized Session getSession(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        return mymap.get(sessionId);
    }
}
