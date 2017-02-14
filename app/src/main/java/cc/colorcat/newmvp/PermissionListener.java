package cc.colorcat.newmvp;

/**
 * 请求权限时的回调接口
 * <p>
 * Created by cxx on 2017/2/14.
 * xx.ch@outlook.com
 */
public interface PermissionListener {

    /**
     * 请求的所有权限都得到许可时调用
     */
    void onAllGranted();

    /**
     * 有权限被拒绝时调用
     *
     * @param permissions 被拒绝的权限
     */
    void onDenied(String[] permissions);
}
