package com.bwie.project.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bwie.project.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ZXingActivity extends AppCompatActivity {

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.zxing_img)
    ImageView mZxingImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.inject(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.touxiang);
        Bitmap img = CodeUtils.createImage("阿诗丹顿", 300, 300, bitmap);
        mZxingImg.setImageBitmap(img);
    }

    @OnClick(R.id.back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
