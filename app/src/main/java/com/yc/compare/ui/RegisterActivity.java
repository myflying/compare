package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
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
public class RegisterActivity extends BaseFragmentActivity implements UserView {

    @BindView(R.id.tv_top_title)
    TextView mTitleTextView;

    @BindView(R.id.et_user_phone)
    EditText mMobileEditText;

    @BindView(R.id.et_sms_code)
    EditText mCodeEditText;

    @BindView(R.id.et_pw)
    EditText mPassWordEditText;

    @BindView(R.id.et_again_pw)
    EditText mAgainPwEditText;

    @BindView(R.id.btn_get_code)
    Button mGetCodeButton;

    @BindView(R.id.btn_register)
    Button mRegisterButton;

    private UserPresenterImp userPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageType = 1;//1:新用户注册，2:忘记密码

    @Override
    protected int getContextViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pageType = bundle.getInt("page_type");
        }
        if (pageType == 1) {
            mTitleTextView.setText(R.string.register_user_txt);
        } else {
            mTitleTextView.setText(R.string.forget_pw_txt);
        }
        initViews();
    }

    public void initViews() {
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        userPresenterImp = new UserPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录");
        mMobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(charSequence.toString())) {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_pre_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.send_pre_color));
                    mRegisterButton.setBackgroundResource(R.mipmap.login_btn_pre);
                } else {
                    mGetCodeButton.setBackgroundResource(R.drawable.send_press_bg);
                    mGetCodeButton.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
                    mRegisterButton.setBackgroundResource(R.drawable.login_selector);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.btn_register)
    void register() {


        if (StringUtils.isEmpty(mMobileEditText.getText())) {
            ToastUtils.showLong("请输入手机号");
            return;
        }

        if (StringUtils.isEmpty(mCodeEditText.getText())) {
            ToastUtils.showLong("请输入验证码");
            return;
        }

        if (StringUtils.isEmpty(mPassWordEditText.getText())) {
            ToastUtils.showLong("请输入新密码");
            return;
        }

        if (StringUtils.isEmpty(mAgainPwEditText.getText())) {
            ToastUtils.showLong("请再次输入新密码");
            return;
        }

        if (!mPassWordEditText.getText().toString().equals(mAgainPwEditText.getText().toString())) {
            ToastUtils.showLong("密码不一致,请重新输入");
            return;
        }

        progressDialog.show();

        userPresenterImp.register(mMobileEditText.getText().toString(), mPassWordEditText.getText().toString());
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
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popBackStack();
    }
}
