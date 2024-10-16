package com.pasc.demo.workspace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pasc.lib.base.util.StatusBarUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtils.setStatusBarLightMode(this, true, false);

        setContentView(R.layout.activity_main);
    }
}
