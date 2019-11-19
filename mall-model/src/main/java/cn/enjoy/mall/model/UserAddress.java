package cn.enjoy.mall.model;

import java.io.Serializable;

public class UserAddress implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.address_id
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private Integer addressId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.user_id
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.consignee
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String consignee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.email
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.area
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String area;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.address
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.zipcode
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String zipcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.mobile
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.is_default
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private Boolean isDefault;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.is_pickup
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private Boolean isPickup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_user_address.label
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    private String label;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.address_id
     *
     * @return the value of tp_user_address.address_id
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.address_id
     *
     * @param addressId the value for tp_user_address.address_id
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.user_id
     *
     * @return the value of tp_user_address.user_id
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.user_id
     *
     * @param userId the value for tp_user_address.user_id
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.consignee
     *
     * @return the value of tp_user_address.consignee
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.consignee
     *
     * @param consignee the value for tp_user_address.consignee
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.email
     *
     * @return the value of tp_user_address.email
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.email
     *
     * @param email the value for tp_user_address.email
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.area
     *
     * @return the value of tp_user_address.area
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getArea() {
        return area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.area
     *
     * @param area the value for tp_user_address.area
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.address
     *
     * @return the value of tp_user_address.address
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.address
     *
     * @param address the value for tp_user_address.address
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.zipcode
     *
     * @return the value of tp_user_address.zipcode
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.zipcode
     *
     * @param zipcode the value for tp_user_address.zipcode
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.mobile
     *
     * @return the value of tp_user_address.mobile
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.mobile
     *
     * @param mobile the value for tp_user_address.mobile
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.is_default
     *
     * @return the value of tp_user_address.is_default
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.is_default
     *
     * @param isDefault the value for tp_user_address.is_default
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.is_pickup
     *
     * @return the value of tp_user_address.is_pickup
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public Boolean getIsPickup() {
        return isPickup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.is_pickup
     *
     * @param isPickup the value for tp_user_address.is_pickup
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setIsPickup(Boolean isPickup) {
        this.isPickup = isPickup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_user_address.label
     *
     * @return the value of tp_user_address.label
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_user_address.label
     *
     * @param label the value for tp_user_address.label
     *
     * @mbggenerated Sun Feb 25 16:24:15 CST 2018
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }
}