package com.huashi.badgerdemo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zjx.app_common_library.utils.WeakHandler;

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
            }
        });
        findViewById(R.id.btnRemoveBadge).setOnClickListener(view -> {
        });
    }
}