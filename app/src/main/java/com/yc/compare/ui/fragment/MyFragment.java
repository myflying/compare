package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yc.compare.R;
import com.yc.compare.bean.UserInfo;
import com.yc.compare.common.Constants;
import com.yc.compare.ui.AboutActivity;
import com.yc.compare.ui.SuggestActivity;
import com.yc.compare.ui.UserInfoActivity;
import com.yc.compare.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iflying on 2017/12/14.
 */

public class MyFragment extends BaseFragment {

    @BindView(R.id.iv_user_head)
    ImageView mUserHeadImageView;

    @BindView(R.id.tv_not_login)
    TextView mNickNameTextView;

    @BindView(R.id.tv_click_login)
    TextView mClickLoginTextView;

    @BindView(R.id.layout_tucao)
    RelativeLayout mTucaoLayout;

    private UserInfo userInfo;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, root);
        //QMUIStatusBarHelper.translucent(getActivity());
        initViews();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString(Constants.USER_INFO))) {

            Logger.i(SPUtils.getInstance().getString(Constants.USER_INFO));

            userInfo = JSON.parseObject(SPUtils.getInstance().getString(Constants.USER_INFO), new TypeReference<UserInfo>() {
            });
            if (userInfo != null) {
                Glide.with(getActivity()).load(userInfo.getHeadImage()).into(mUserHeadImageView);
                mNickNameTextView.setText(StringUtils.isEmpty(userInfo.getNickName()) ? "火星用户" : userInfo.getNickName());
                mClickLoginTextView.setVisibility(View.GONE);
            }
        }
    }

    public void initViews() {

    }

    @OnClick(R.id.layout_user_info)
    void userInfo() {
//        if (userInfo == null) {
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//        }

        UMImage image = new UMImage(getActivity(), "https://mmbiz.qlogo.cn/mmbiz_png/49m5ew6HtibmsQxsVmvyxjtKjoPia7ZzwnRHLaibacLicqg8ttia6RXdnsNQN23rKdicO2hJt2micXbmWUUDj3jY77GKQ/0?wx_fmt=png");//网络图片

        new ShareAction(getActivity())
                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                .withText("测试内容")//分享内容
                .withMedia(image)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @OnClick(R.id.layout_tucao)
    void suggest() {
        Intent intent = new Intent(getActivity(), SuggestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_info)
    void updateUserInfo() {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_about)
    void about() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

}
