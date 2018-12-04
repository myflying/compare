package com.yc.compare.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.yc.compare.R;
import com.yc.compare.bean.UserInfoRet;
import com.yc.compare.presenter.UserPresenterImp;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.util.StringUtils;
import com.yc.compare.view.UserView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/4.
 */
public class LoginActivity extends BaseFragmentActivity implements UserView {

    @BindView(R.id.et_user_name)
    EditText userNameEditText;

    @BindView(R.id.et_user_pw)
    EditText passWordEditText;

    @BindView(R.id.tv_login_type)
    TextView mLoginTypeTextView;

    @BindView(R.id.tv_forget_pw)
    TextView mForgetTextView;

    @BindView(R.id.tv_register)
    TextView mRegisterTextView;

    @BindView(R.id.btn_get_code)
    Button mGetCodeButton;

    @BindView(R.id.btn_login)
    Button mLoginButton;

    private UserPresenterImp userPresenterImp;

    private ProgressDialog progressDialog = null;

    private int loginType = 1;

    private int seconds = 60;

    private String validateNum;//随机验证码

    private boolean isSendSms;//是否是发送验证码的请求

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getContextViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        userPresenterImp = new UserPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录");
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence.toString())) {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_pre_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.send_pre_color));
                    mLoginButton.setBackgroundResource(R.mipmap.login_btn_pre);
                } else {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_press_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    mLoginButton.setBackgroundResource(R.drawable.login_selector);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 刷新验证码倒计时
     */
    private void smsButtonRefresh() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (seconds-- <= 0) {
                    mGetCodeButton.setEnabled(true);
                    mGetCodeButton.setText("获取验证码");
                    mGetCodeButton.setBackgroundResource(R.drawable.send_press_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                    return;
                }
                mGetCodeButton.setEnabled(false);
                mGetCodeButton.setText(seconds + "s");
                mGetCodeButton.setBackgroundResource(R.drawable.send_pre_bg);
                mGetCodeButton.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.send_pre_color));
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    @OnClick(R.id.btn_get_code)
    void sendCode() {
        if (StringUtils.isEmpty(userNameEditText.getText())) {
            ToastUtils.showLong(loginType == 1 ? "请输入账号" : "请输入手机号");
            return;
        }
        isSendSms = true;
        validateNum = String.valueOf((int) (Math.random() * (9999 - 1000 + 1)) + 1000);
        userPresenterImp.sendSms(userNameEditText.getText().toString(), validateNum);
    }

    @OnClick(R.id.btn_login)
    void login() {

        if (StringUtils.isEmpty(userNameEditText.getText())) {
            ToastUtils.showLong(loginType == 1 ? "请输入账号" : "请输入手机号");
            return;
        }

        if (StringUtils.isEmpty(passWordEditText.getText())) {
            ToastUtils.showLong(loginType == 1 ? "请输入密码" : "请输入验证码");
            return;
        }

        progressDialog.show();

        userPresenterImp.userLogin(userNameEditText.getText().toString(), passWordEditText.getText().toString(), String.valueOf(loginType));
    }

    @OnClick(R.id.tv_login_type)
    void changeType() {
        if (mForgetTextView.getVisibility() == View.VISIBLE) {
            loginType = 2;
            mForgetTextView.setVisibility(View.GONE);
            mGetCodeButton.setVisibility(View.VISIBLE);
            passWordEditText.setHint(R.string.user_input_sms_code);
        } else {
            loginType = 1;
            mForgetTextView.setVisibility(View.VISIBLE);
            mGetCodeButton.setVisibility(View.GONE);
            passWordEditText.setHint(R.string.user_input_password);
        }
    }

    @OnClick(R.id.tv_register)
    void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("page_type", 1);
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(UserInfoRet tData) {
        Logger.i(JSONObject.toJSONString(tData));
        progressDialog.dismiss();
        if (isSendSms) {
            ToastUtils.showLong("已发送");
            isSendSms = false;
            smsButtonRefresh();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        progressDialog.dismiss();
        if (isSendSms) {
            isSendSms = false;
        }
    }
}
