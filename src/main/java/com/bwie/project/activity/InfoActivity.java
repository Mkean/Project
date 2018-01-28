package com.bwie.project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.project.R;

import java.io.File;

import bean.UserInfoBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity {

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.back_right)
    ImageView mBackRight;
    @InjectView(R.id.avatar)
    RelativeLayout mAvatar;
    @InjectView(R.id.userName)
    RelativeLayout mUserName;
    @InjectView(R.id.nickName)
    RelativeLayout mNickName;
    @InjectView(R.id.Zxing)
    RelativeLayout mZxing;
    @InjectView(R.id.sex)
    RelativeLayout mSex;
    @InjectView(R.id.petName)
    TextView mPetName;
    @InjectView(R.id.gender)
    TextView mGender;
    @InjectView(R.id.touXiang)
    ImageView touXiang;
    private UserInfoBean.DataBean data;
    private View view;
    private TextView dialong_title;
    private TextView dialog_bt1;
    private TextView dialog_bt2;
    private String uid;
    public static final int ACTION_PICK_CODE = 1000;
    public static final int ACTION_PICK_CROP = 10;
    public static final int ACTION_CAPTURE_CODE = 10000;
    public static final String path = Environment.getExternalStorageDirectory() + "/touxiang.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        data = (UserInfoBean.DataBean) intent.getSerializableExtra("data");
        uid = String.valueOf(data.getUid());
        String nickname = data.getNickname();
        mPetName.setText(nickname);

    }

    @OnClick({R.id.back, R.id.back_right, R.id.avatar, R.id.userName, R.id.back_right1, R.id.nickName, R.id.back_right2, R.id.Zxing, R.id.back_right3, R.id.sex})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.avatar:
                getDialog();
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                final AlertDialog mDialog = dialog.setView(view).create();
                dialog_bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, ACTION_PICK_CODE);
                        mDialog.dismiss();
                    }
                });

                dialog_bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                        startActivityForResult(intent, ACTION_CAPTURE_CODE);
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
                break;
            case R.id.userName:
                Toast.makeText(this, "淘宝会员名作为登录名不可修改～", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nickName:
                Intent intent = new Intent(this, UpdateNickActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("nickName", mPetName.getText().toString());
                startActivityForResult(intent, 100);
                break;
            case R.id.Zxing:
                AutoIntent(ZXingActivity.class);

                break;
            case R.id.sex:
                getDialog();
                dialong_title.setText("修改性别");
                dialog_bt2.setText("女");
                dialog_bt1.setText("男");
                final AlertDialog.Builder dialogSex = new AlertDialog.Builder(this);
                final AlertDialog mDialogSex = dialogSex.setView(view).create();

                dialog_bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGender.setText("男");
                        mDialogSex.dismiss();
                    }
                });
                dialog_bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGender.setText("女");
                        mDialogSex.dismiss();
                    }
                });

                mDialogSex.show();

                break;
        }
    }

    public void AutoIntent(Class ObjectClass) {
        Intent intent = new Intent(this, ObjectClass);
        startActivity(intent);
    }

    public void getDialog() {
        view = View.inflate(this, R.layout.dialog_item, null);
        dialong_title = view.findViewById(R.id.dialog_title);
        dialog_bt1 = view.findViewById(R.id.dialog_bt1);
        dialog_bt2 = view.findViewById(R.id.dialog_bt2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                String name = data.getStringExtra("name");
                mPetName.setText(name);
                break;
            case ACTION_PICK_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    PictureCut(uri);
                }
                break;
            case ACTION_PICK_CROP:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    touXiang.setImageBitmap(bitmap);
                }
                break;
            case ACTION_CAPTURE_CODE:
                if (resultCode == RESULT_OK) {
                    PictureCut(Uri.fromFile(new File(path)));
                }
                break;
        }
    }

    private void PictureCut(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("CROP", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 40);
        intent.putExtra("outputY", 40);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, ACTION_PICK_CROP);
    }
}
