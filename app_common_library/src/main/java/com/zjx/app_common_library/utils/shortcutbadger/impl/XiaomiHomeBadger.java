package com.zjx.app_common_library.utils.shortcutbadger.impl;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.zjx.app_common_library.R;
import com.zjx.app_common_library.utils.shortcutbadger.Badger;
import com.zjx.app_common_library.utils.shortcutbadger.ShortcutBadgeException;
import com.zjx.app_common_library.utils.shortcutbadger.util.BroadcastHelper;


/**
 * @author leolin
 */
public class XiaomiHomeBadger implements Badger {
    public static final String INTENT_ACTION = "android.intent.action.APPLICATION_MESSAGE_UPDATE";
    public static final String EXTRA_UPDATE_APP_COMPONENT_NAME = "android.intent.extra.update_application_component_name";
    public static final String EXTRA_UPDATE_APP_MSG_TEXT = "android.intent.extra.update_application_message_text";
    private ResolveInfo resolveInfo;
    public static final int notificationId = 0;

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        try {
            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            try {
                field.set(miuiNotification, badgeCount);
            } catch (Exception e) {
                LogUtils.e("错误：" + e);
                field.set(miuiNotification, String.valueOf(badgeCount));
            }
        } catch (Exception e) {
            LogUtils.e("错误：" + e);
            Intent localIntent = new Intent(
                    INTENT_ACTION);
            localIntent.putExtra(EXTRA_UPDATE_APP_COMPONENT_NAME, componentName.getPackageName() + "/" + componentName.getClassName());
            localIntent.putExtra(EXTRA_UPDATE_APP_MSG_TEXT, String.valueOf(badgeCount == 0 ? "" : badgeCount));
            try {
                BroadcastHelper.sendIntentExplicitly(context, localIntent);
            } catch (ShortcutBadgeException ignored) {
                LogUtils.e("错误：" + ignored);
            }
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            try {
                tryNewMiuiBadge(context, badgeCount);
            } catch (ShortcutBadgeException e) {
                LogUtils.e("错误：" + e);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void tryNewMiuiBadge(Context context, int badgeCount) throws ShortcutBadgeException {
        if (resolveInfo == null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        }
        if (resolveInfo != null) {
            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(context.getPackageName(), "mi_push", NotificationManager.IMPORTANCE_HIGH);
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(channel);
                }
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getPackageName())
                    .setContentTitle(AppUtils.getAppName())
                    .setContentText(StringUtils.getString(R.string.text_receive_notification, badgeCount))
                    .setWhen(TimeUtils.getNowMills())
                    .setSmallIcon(resolveInfo.getIconResource())
                    .setAutoCancel(true);
            Notification notification = builder.build();
            try {
                Field field = notification.getClass().getDeclaredField("extraNotification");
                Object extraNotification = field.get(notification);
                Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
                method.invoke(extraNotification, badgeCount);
            } catch (Exception e) {
                throw new ShortcutBadgeException("not able to set badge", e);
            }
            mNotificationManager.cancel(notificationId);
            if (badgeCount != 0) {
                mNotificationManager.notify(notificationId, notification);
            }
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.miui.miuilite",
                "com.miui.home",
                "com.miui.miuihome",
                "com.miui.miuihome2",
                "com.miui.mihome",
                "com.miui.mihome2",
                "com.i.miui.launcher"
        );
    }
}
