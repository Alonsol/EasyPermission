package com.yy.permissioinlib

import android.os.Build
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


/**
 * Created by yuyang on 2020/6/14.
 * function: 权限申请帮助类
 */
class PermissionHelper private constructor(private val builder: Builder) {

    private val TAG = "PermissionHelper"

    private var activity: FragmentActivity? = null

    private var callback: IPermissionResult? = null

    private var permissionMap: HashMap<String, String>? = null

    private var permissionFragment: PermissionFragment? = null

    init {
        activity = builder.activity
        callback = builder.callback
        permissionMap = builder.permissionMap
        checkParam()
        requestPermissions()
    }

    private fun checkParam() {
        activity ?: throw IllegalArgumentException("context can not be null")
        permissionMap ?: throw IllegalArgumentException("permissionMap can not be null")
    }


    class Builder(activity: FragmentActivity) {
        internal var callback: IPermissionResult? = null
        internal var permissionMap: HashMap<String, String>? = null
        internal var activity = activity

        /**
         *  添加权限
         *  @param permission 动态权限
         *  @param permissionName 权限
         */
        fun addPermission(permission: String, permissionName: String? = null) = apply {
            if (permissionMap == null) {
                permissionMap = HashMap()
            }
            permissionMap!![permission] = permissionName ?: ""
        }


        /**
         *  添加权限
         *  @param permission 动态权限
         *  @param resId 权限名称(资源文件地址)
         */
        fun addPermission(permission: String, @StringRes resId: Int) = apply {
            if (permissionMap == null) {
                permissionMap = HashMap()
            }
            permissionMap!![permission] = activity.getString(resId)
        }

        /**
         * 添加权限回调
         */
        fun requestPermission(callback: IPermissionResult? = null) = apply {
            this.callback = callback
            PermissionHelper(this)
        }

    }


    /**
     * 申请权限
     */
    private fun requestPermissions() {
        permissionMap ?: return
        activity ?: return

        //android 6.0一下默认申请成功 否者走高版本申请流程
        if (Build.VERSION.SDK_INT < 23) {
            callback?.onGranted()
        } else {
            val fragmentManager = activity?.supportFragmentManager
            findFragment(fragmentManager)
            permissionFragment?.requestPermissions(permissionMap!!, callback)
        }


    }

    /**
     * 查看fragment
     */
    private fun findFragment(fragmentManager: FragmentManager?) {
        var permissionFragment = getFragment(fragmentManager)
        if (permissionFragment != null) {
            permissionFragment = permissionFragment
        } else {
            permissionFragment = PermissionFragment()
            fragmentManager?.beginTransaction()?.add(permissionFragment, TAG)?.commitNow()
        }
        this.permissionFragment = permissionFragment
    }

    private fun getFragment(fragmentManager: FragmentManager?): PermissionFragment? {
        return fragmentManager?.findFragmentByTag(TAG) as PermissionFragment?
    }

}