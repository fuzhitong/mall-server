package cn.enjoy.mall.dao;

import cn.enjoy.mall.model.OrderAction;

import java.util.LinkedList;

public interface OrderActionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_order_action
     *
     * @mbggenerated Wed Feb 28 18:00:18 CST 2018
     */
    int deleteByPrimaryKey(Integer actionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_order_action
     *
     * @mbggenerated Wed Feb 28 18:00:18 CST 2018
     */
    int insert(OrderAction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_order_action
     *
     * @mbggenerated Wed Feb 28 18:00:18 CST 2018
     */
    int insertSelective(OrderAction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_order_action
     *
     * @mbggenerated Wed Feb 28 18:00:18 CST 2018
     */
    OrderAction selectByPrimaryKey(Integer actionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_order_action
     *
     * @mbggenerated Wed Feb 28 18:00:18 CST 2018
     */
    OrderAction queryByPrepayId(String prepayId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_order_action
     *
     * @mbggenerated Wed Feb 28 18:00:18 CST 2018
     */
    int updateByPrimaryKeySelective(OrderAction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_order_action
     *
     * @mbggenerated Wed Feb 28 18:00:18 CST 2018
     */
    int updateByPrimaryKey(OrderAction record);

    LinkedList<OrderAction> queryByOrderId(Integer orderId);
}