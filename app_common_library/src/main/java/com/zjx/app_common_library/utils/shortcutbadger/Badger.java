package com.zjx.app_common_library.utils.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;

import java.util.List;

public interface Badger {

    /**
     * 当用户尝试更新通知计数时调用
     * @param context
     * @param componentName 应用程序的包名和类名
     * @param badgeCount 通知数量
     * @throws ShortcutBadgeException
     */
    void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException;

    /**
     * 判断badger支持的包名
     * @return List 包含支持的应用包名
     */
    List<String> getSupportLaunchers();
}
