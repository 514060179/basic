package com.simon.basics.model;

/**
 * @author fengtianying
 * @date 2018/11/1 15:42
 */
public class RefundOrderWithUser extends RefundOrder {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
