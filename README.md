# PermssionHelper
  真正的好用的权限申请库，无需依赖其他第三方库，纯原生代码，调用方便
  
  # 特性
    1.支持动态添加权限申请，个数不受限
    
    2.支持权限名称重命名
    
    3.支持单纯申请权限以及申请权限返回申请结果
    
    4.链式调用，无需继承以及实现接口的方式，随调随用
    
    5.无性能问题，无内存泄漏问题
    
    6.支持6.0以上所有机型，低于此版本默认返回成功，后续会处理国产机型低版本权限校验
    
    
    
### 1.开篇：调用方式
  ``` kotlin
     PermissionHelper.Builder(this)
                .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.str_extraternal_permissioin)
                .requestPermission()
     亦或
     PermissionHelper.Builder(this)
                .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.str_extraternal_permissioin)
                .requestPermission(object : IPermissionResult {
                    override fun onGranted() {
                        Toast.makeText(this@MainActivity, "授权成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDenied(deniedPermissions: ArrayList<PermissionBean>) {
                        showDeniedPermissionDialog(deniedPermissions)
                    }
                })
  ```
 ### 2.申请单个权限
    ``` kotlin
    addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.str_extraternal_permissioin)
    或者
    .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入外部存储权限")
    亦或
    .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    ```
    
    
    
 ### 3.申请多个权限 
    ``` kotlin
      .addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermission(Manifest.permission.CAMERA, "摄像机权限")
                .addPermission(Manifest.permission.READ_EXTERNAL_STORAGE, "读取外部存储权限")
                .addPermission(Manifest.permission.ACCESS_COARSE_LOCATION, "定位权限")
                ......
    ```    
    
 ### 4.只申请权限，不需要回调 
    ``` kotlin
      .requestPermission()
    ```
  ### 5.申请权限并返回结果 
    ``` kotlin
      .requestPermission(object : IPermissionResult {
                    override fun onGranted() {
                        Toast.makeText(this@MainActivity, "授权成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDenied(deniedPermissions: ArrayList<PermissionBean>) {
                        showDeniedPermissionDialog(deniedPermissions)
                    }
                }
    ``` 
    
    
   ### 6.权限回调处理（开发者根据自己的业务做相应处理，这里这是示例c）
     ``` kotlin
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
     ```
   
   ### 结语
      此开源库通过创建permissionFragment，在fragment中处理相应的权限，解决android原生调用繁琐的问题
