package com.yy.permissioinlib

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment


/**
 * Created by yuyang on 2020/6/14.
 * function: 权限fragment 用于申请权限用
 */
class PermissionFragment : Fragment() {

    /**
     * 所需申请的权限集合
     */
    private var permissions: Array<String>? = null

    /**
     * 权限结果回调
     */
    private var callback: IPermissionResult? = null

    /**
     * 权限map集合 包含权限名称，以及权限提醒
     */
    private var permissionMap = HashMap<String, String>()

    companion object {
        private const val REQUEST_PERMISSION_CODE = 666
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    @TargetApi(23)
    fun requestPermissions(permissionMap: HashMap<String, String>, callback: IPermissionResult?) {
        this.callback = callback
        this.permissionMap = permissionMap
        var permissionArray = NumUtils.map2Array(permissionMap)
        if (isAdded) {
            this.requestPermissions(permissionArray, 666)
        } else {
            this.permissions = permissionArray
        }
    }


    /**
     * 防止fragment未attch到activity上时导致的申请异常
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (permissions != null) {
            this.requestPermissions(permissions!!, 666)
            permissions = null
        }
    }

    /**
     * 申请结果回调
     */
    @TargetApi(23)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                var permissionList = ArrayList<PermissionBean>()

                grantResults.forEachIndexed { index, c ->
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        var permissionBean =
                            PermissionBean(permissions[index], permissionMap[permissions[index]]?:"")
                        permissionList.add(permissionBean)
                    }
                }
                if (permissionList.isNotEmpty()) {
                    callback?.onDenied(permissionList)
                } else {
                    callback?.onGranted()
                }
            }
        }
        permissionMap.clear()
    }
}