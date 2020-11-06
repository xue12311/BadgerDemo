package com.huashi.badgerdemo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zjx.app_common_library.utils.WeakHandler;
import com.zjx.app_common_library.utils.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText etNum = findViewById(R.id.etNum);
        findViewById(R.id.btnSetBadge).setOnClickListener(view -> {
            if (StringUtils.isTrimEmpty(etNum.getText().toString())) {
                ToastUtils.showShort("请填写角标数量");
            } else {
                //延迟显示角标个数，解决小米不正确显示角标
                new WeakHandler().postDelayed(() -> {
                    int badgeCount = 0;
                    try {
                        badgeCount = Integer.parseInt(etNum.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_SHORT).show();
                    }
                    boolean success = ShortcutBadger.applyCount(MainActivity.this, badgeCount);
                    if (success) {
                        ActivityUtils.startHomeActivity();
                        ToastUtils.showShort("角标设置成功，角标数量: " + badgeCount);
                    } else {
                        ToastUtils.showShort("角标设置失败");
                    }
                }, 500);
            }
        });
        findViewById(R.id.btnRemoveBadge).setOnClickListener(view -> {
            boolean success = ShortcutBadger.removeCount(MainActivity.this);
            if (success) {
                ActivityUtils.startHomeActivity();
                ToastUtils.showShort("清除角标成功");
            } else {
                ToastUtils.showShort("清除角标失败");
            }
        });
    }
}