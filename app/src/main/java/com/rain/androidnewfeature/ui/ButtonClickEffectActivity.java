package com.rain.androidnewfeature.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rain.androidnewfeature.R;

/**
 * Author:rain
 * Date:2018/12/25 9:39
 * Description:
 * 参见：https://www.cnblogs.com/carlo/p/4795424.html
 */
public class ButtonClickEffectActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click_effect);

        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:

                break;

            case R.id.btn2:
                break;

            case R.id.btn3:
                break;

            case R.id.btn4:
                break;

                default:
        }
    }
}
