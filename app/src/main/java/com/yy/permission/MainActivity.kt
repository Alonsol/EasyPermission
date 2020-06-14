package com.yy.permission

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yy.permissioinlib.IPermissionResult
import com.yy.permissioinlib.PermissionBean
import com.yy.permissioinlib.PermissionHelper
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by yuyang on 2020/6/14.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequestPermission.setOnClickListener {

            PermissionHelper.Builder(this)
                .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.str_extraternal_permissioin)
                .addPermission(Manifest.permission.CAMERA, R.string.str_camera_permission)
                .requestPermission()

        }

        /**
         *  申请动态权限
         *  添加权限（权限个数支持动态添加 permissionName为字符串） addPermission(permission: String, permissionName: String? = null)
         *  添加权限 （权限个数支持动态添加 resId：为资源id）  addPermission(permission: String, @StringRes resId: Int)
         *  申请权限 requestPermission 带参数时回调申请结果 空参场景则指申请，不返回申请结果
         */
        btnRequestPermission2.setOnClickListener {

            PermissionHelper.Builder(this)
                .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入外部存储权限")
                .addPermission(Manifest.permission.CAMERA, "摄像机权限")
                .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE, "读取外部存储权限")
                .addPermission(Manifest.permission.ACCESS_COARSE_LOCATION, "定位权限")
                .requestPermission(object : IPermissionResult {
                    override fun onGranted() {
                        Toast.makeText(this@MainActivity, "授权成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDenied(deniedPermissions: ArrayList<PermissionBean>) {
                        showDeniedPermissionDialog(deniedPermissions)
                    }
                })
        }
    }

    /**
     * 拒绝弹窗
     * 这里只是demo，开发者可以根据自己的方视去修改弹窗样式
     */
    private fun showDeniedPermissionDialog(deniedPermissions: ArrayList<PermissionBean>) {
        val stringBuilder = StringBuilder()
        deniedPermissions.forEach {
            stringBuilder.append("${if (it.tips.isEmpty()) it.permission else it.tips}\n")
        }

        AlertDialog.Builder(this@MainActivity)
            .setTitle("权限授权提示")
            .setMessage("请授予一下权限，以继续使用该功能\n\n$stringBuilder")
            .setPositiveButton("好的") { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri =
                    Uri.fromParts("package", this@MainActivity.packageName, null)
                intent.data = uri
                try {
                    this@MainActivity.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .create()
            .show()
    }
}
