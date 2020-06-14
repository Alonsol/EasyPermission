package com.yy.permissioinlib;

/**
 * Created by yuyang on 2020/6/14.
 * function: 权限对象
 */
public class PermissionBean {

    /**
     * 权限名称
     */
    private String permission;

    /**
     * 权限提示(根据自己的业务名称定)
     */
    private String tips;

    public PermissionBean(String permission, String tips) {
        this.permission = permission;
        this.tips = tips;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
