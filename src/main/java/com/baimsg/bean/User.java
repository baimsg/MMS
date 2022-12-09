package com.baimsg.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 0;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Boolean getMerchant() {
        return merchant;
    }

    public void setMerchant(Boolean merchant) {
        this.merchant = merchant;
    }

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private Long telephone;

    /**
     * 地址
     */
    private String address;

    /**
     * 商店名
     */
    private String storeName;

    /**
     * 名字
     */
    private String name;

    /**
     * 余额
     */
    private Long balance;

    private Boolean merchant;

    private static final Gson gson = new Gson();

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return account.equals(user.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}
