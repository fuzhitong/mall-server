package cn.enjoy.users.dbutils;

import java.util.Stack;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：数据源的选择
 */
public class DBContextHolder {
    /*保存系统中存在的数据源的标识符，然后通过该标识符定位到实际的数据源实体*/
    private static final ThreadLocal<Stack<DBTypeEnum>> contextHolderStack
            = new ThreadLocal<Stack<DBTypeEnum>>(){
        @Override
        protected Stack<DBTypeEnum> initialValue() {
            return new Stack<DBTypeEnum>();
        }
    };


//    private static final ThreadLocal<DBTypeEnum> contextHolder
//            = new ThreadLocal<>();


    public static void set(DBTypeEnum dbType) {
        contextHolderStack.get().push(dbType);
//        contextHolder.set(dbType);
    }

    public static void remove() {
        if(!contextHolderStack.get().empty()){
            contextHolderStack.get().pop();
        }
//        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        if(contextHolderStack.get().empty()){
            return DBTypeEnum.MASTER;
        }
        DBTypeEnum current = contextHolderStack.get().peek();
        System.out.println("当前数据库："+current);
        return current;
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        System.out.println("切换到master");
    }

    /*通过轮询选择从库*/
    public static void slave() {
        set(DBTypeEnum.SLAVE);//轮询
        System.out.println("切换到从库-----------------------");
    }
}
