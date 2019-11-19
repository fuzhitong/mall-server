package cn.enjoy.core.constant;

/**
 * 系统的固定角色
 * @author Ray
 * @date 2017/11/30.
 */
public enum SysRoleEnum {

    //系统管理员
    SYS_ADMIN,
    //员工
    EMPLOYEE,
    //项目负责人
    PM,
    //部门经理
    DM,
    //人事部门经理
    HRM,
    //总经理
    GENERAL_MANAGER,
    //资产管理员
    ASSETS_MANAGER,
    /** 资源申请 中【选择最终审批人】 的角色code**/
    FINALAPPROVER,
    //财务部负责人
    FINANCE_HEAD,
    //市场部负责人
    MARKETING_DIRECTOR,
    //质量部部负责人
    QUALITY_MANAGER,
    //顾客（前台用户）
    CUSTOMER
}
