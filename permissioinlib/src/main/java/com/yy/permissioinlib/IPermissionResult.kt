package com.yy.permissioinlib
/**
 * Created by yuyang on 2020/6/14.
 * function: 权限结果回调
 */
interface IPermissionResult {

    /**
     * 申请成功(所有权限申请成功后回调该方法)
     */
    fun onGranted()


    /**
     * 申请失败
     * @param deniedPermissions 申请失败的权限集合
     */
    fun onDenied(deniedPermissions:  ArrayList<PermissionBean>)
}