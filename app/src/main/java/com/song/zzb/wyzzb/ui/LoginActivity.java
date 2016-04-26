package com.song.zzb.wyzzb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.util.DeletableEditText;
import com.song.zzb.wyzzb.util.ToastUtil;
import com.song.zzb.wyzzb.util.UserProxy;

/**
 * Created by song on 2016/2/17.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,UserProxy.ILoginListener {
    private DeletableEditText deletableEditTextAccount,deletableEditTextPWD;
    private Button loginbtn;
    private TextView registertTV,findPwdTv;
    private UserProxy userProxy;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        setListener();

    }
    protected void  initView(){
        deletableEditTextAccount = (DeletableEditText)findViewById(R.id.user_name_input);
        deletableEditTextPWD = (DeletableEditText)findViewById(R.id.user_pwd_input);
        loginbtn =(Button)findViewById(R.id.login_btn);
        registertTV = (TextView)findViewById(R.id.register);
        findPwdTv= (TextView)findViewById(R.id.findpwd);
        userProxy = new UserProxy(this.getApplicationContext());
    }
    protected void setListener() {
        // TODO Auto-generated method stub'
        loginbtn.setOnClickListener(this);
        registertTV.setOnClickListener(this);
        findPwdTv.setOnClickListener(this);
       ;

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (TextUtils.isEmpty(deletableEditTextAccount.getText())) {
                    deletableEditTextAccount.setShakeAnimation();
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(deletableEditTextPWD.getText())) {
                    deletableEditTextPWD.setShakeAnimation();
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                userProxy.setOnLoginListener(this);
                initProgressDialog("正在登录");
                userProxy.login(deletableEditTextAccount.getText().toString().trim(), deletableEditTextPWD.getText().toString().trim());

                break;
            case R.id.register:
                Toast.makeText(getApplicationContext(), "nidainj", Toast.LENGTH_SHORT).show();
                break;
            case R.id.findpwd:
                Toast.makeText(getApplicationContext(), "nidainj111", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        dismissDialog();
        showToast("登录成功。");
        Log.i(TAG, "login sucessed!");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onLoginFailure(String msg) {
        dismissDialog();
    }
}
