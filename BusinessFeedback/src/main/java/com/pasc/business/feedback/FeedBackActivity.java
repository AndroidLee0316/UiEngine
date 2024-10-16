package com.pasc.business.feedback;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pasc.business.feedback.net.FeedBackServerManager;
import com.pasc.business.feedback.router.RouterTable;
import com.pasc.business.feedback.widget.ChooseOptionDialog;
import com.pasc.lib.base.permission.PermissionUtils;
import com.pasc.lib.base.util.AppUtils;
import com.pasc.lib.base.util.SPUtils;
import com.pasc.lib.base.util.StatusBarUtils;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.base.widget.LoadingDialog;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.picture.pictureSelect.NewPictureSelectActivity;
import com.pasc.lib.picture.pictureSelect.PictureSelectActivity;
import com.pasc.lib.picture.takephoto.app.TakePhoto;
import com.pasc.lib.picture.takephoto.app.TakePhotoImpl;
import com.pasc.lib.picture.takephoto.compress.CompressConfig;
import com.pasc.lib.picture.takephoto.model.InvokeParam;
import com.pasc.lib.picture.takephoto.model.TContextWrap;
import com.pasc.lib.picture.takephoto.model.TImage;
import com.pasc.lib.picture.takephoto.model.TResult;
import com.pasc.lib.picture.takephoto.model.TakePhotoOptions;
import com.pasc.lib.picture.takephoto.permission.InvokeListener;
import com.pasc.lib.picture.takephoto.permission.PermissionManager;
import com.pasc.lib.picture.takephoto.permission.TakePhotoInvocationHandler;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.common.ConfirmDialogFragment;
import com.pasc.lib.widget.roundview.RoundTextView;
import com.pasc.lib.widget.toolbar.PascToolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Created by ex-lingchun001 on 2018/3/3.
 * 意见反馈
 */
@Route(
        path = RouterTable.MAIN_FEEDBACK
)
public class FeedBackActivity extends BaseStatusActivity
        implements TakePhoto.TakeResultListener, InvokeListener {

    public static final String LAYOUT_TYPE = "layoutType";
    private Context mContext;
    private static final String TAG = FeedBackActivity.class.getName();
    public static final String FEED_BACK_TEXT = "feed_back_text";
    public static final int LAYOUT_TEMPLATE = 0;
    public static final int LAYOUT_CS = 1;
    public static final int LAYOUT_SMT = LAYOUT_CS;
    public  int layoutType = 0;

    LinearLayout llOpinionImages;
    PascToolbar commonTitle;
    AppCompatEditText etOpinion;
    TextView tvTextLength;
    ImageView ivAdd;
    RoundTextView rtvQuestion;
    RoundTextView rtvSuggest;
    private TextView rightTextButton;
    //拍照权限
    String[] needCameraPermission = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    String[] needSdcardPermission = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private ArrayList<String> imagePathList = new ArrayList<>();
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private int Max = 4;

    private static final String HAS_SHOW_FEEDBACK_TIPS = "has_show_feedback_tips";

    private static final int REQUEST_CODE_PICTURE_SELECT = 100;
    private TextView tvPhotoNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBarColor(this, true);
        layoutType=getIntent().getIntExtra(LAYOUT_TYPE,0);
        setContentView();
        mContext = this;
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.feedback_bg));

        initView();
    }

    /**
     * 根据类型 设置页面布局
     */
    private void setContentView() {
        if (layoutType == LAYOUT_TEMPLATE) {
            setContentView(R.layout.feedback_activity_feedback);
        } else if (layoutType == LAYOUT_CS || layoutType == LAYOUT_SMT) {
            setContentView(R.layout.feedback_activity_feedback_cs);
        }
    }


    private void initView() {
        // 传入自定义相册
        NewPictureSelectActivity.setIsHeadImg(false);
        getTakePhoto().customPickActivity(NewPictureSelectActivity.class);
        llOpinionImages = findViewById(R.id.ll_opinion_images);
        commonTitle = findViewById(R.id.common_title1);
        etOpinion = findViewById(R.id.et_opinion);
        tvTextLength = findViewById(R.id.tv_text_length);
        ivAdd = findViewById(R.id.img_add);
        rtvQuestion = findViewById(R.id.rtv_question);
        rtvSuggest = findViewById(R.id.rtv_suggest);
        if (layoutType == LAYOUT_TEMPLATE) {
            tvPhotoNum = findViewById(R.id.tv_photo_num);
        }
        commonTitle.addCloseImageButton()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        setCommitButton();
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
        rtvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSuggestTagSelected(false);
            }
        });
        rtvSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSuggestTagSelected(true);
            }
        });
        etOpinion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (layoutType == LAYOUT_TEMPLATE) {
                    if (s.length() >= 10) {
                        rightTextButton.setAlpha(1);
                    } else {
                        rightTextButton.setAlpha(0.4f);
                    }
                    if (s.length() > 0) {
                        tvTextLength.setTextColor(getResources().getColor(R.color.feedback_item_activated_color));
                    } else {
                        tvTextLength.setTextColor(getResources().getColor(R.color.gray_c7c7c7));
                    }

                } else if (layoutType == LAYOUT_CS || layoutType == LAYOUT_SMT) {
                    if (s.toString().trim().length() >= 1) {
                        rightTextButton.setAlpha(1);
                    } else {
                        rightTextButton.setAlpha(0.4f);
                    }
                    if (s.length() >= 240) {
                        ToastUtils.toastMsg("请输入不大于240个字的描述");
                    }
                }
                tvTextLength.setText(String.valueOf(s.length()));

            }
        });
        //测试要求屏蔽此功能   by  zc   2018年07月16日19:33:07
        //        etOpinion.setText(SPUtils.getInstance().getParam(FEED_BACK_TEXT, "").toString());
        etOpinion.setSelection(etOpinion.getText().length());
        setSuggestTagSelected(true);
        if (layoutType == LAYOUT_CS || layoutType == LAYOUT_SMT) {
            showTipsDialog();
        }
    }

    private void setCommitButton() {
        if (layoutType == LAYOUT_TEMPLATE) {
            rightTextButton = findViewById(R.id.tv_commit);
            rightTextButton.setAlpha(0.3f);
            rightTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(etOpinion.getText().toString().trim())) {
                        ToastUtils.toastMsg("请输入内容");
                        return;
                    }
                    if (etOpinion.getText() == null || etOpinion.getText().length() < 10) {
                        ToastUtils.toastMsg("请输入不少于10个字的描述");
                        return;
                    }
                    submit();
                }
            });
        } else if (layoutType == LAYOUT_CS || layoutType == LAYOUT_SMT) {
            rightTextButton = commonTitle.addRightTextButton("提交");
            rightTextButton.setBackgroundColor(Color.TRANSPARENT);
            rightTextButton.setTextColor(Color.BLACK);
            rightTextButton.setAlpha(0.4f);
            rightTextButton.setOnClickListener(new View.OnClickListener
                    () {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(etOpinion.getText().toString().trim())) {
                        return;
                    }
                    if (etOpinion.getText() == null || etOpinion.getText().toString().trim().length() < 1) {
                        ToastUtils.toastMsg("请输入不少于1个字的描述");
                        return;
                    }
                    submit();
                }
            });
        }

    }

    /**
     * 设置两个tag 的状态
     */
    private void setSuggestTagSelected(boolean isSuggestSelected) {
        int selectedColor = getResources().getColor(R.color.green_f0f5ff);
        int defaultColor = getResources().getColor(R.color.gray_f5f5f5);
        rtvSuggest.setSelected(isSuggestSelected);
        rtvSuggest.getDelegate().setBackgroundColor(isSuggestSelected ? selectedColor : defaultColor);
        rtvQuestion.setSelected(!isSuggestSelected);
        rtvQuestion.getDelegate().setBackgroundColor(isSuggestSelected ? defaultColor : selectedColor);
    }

    private void submit() {
        final String phoneSystemType = Build.VERSION.RELEASE;
        final String phoneModel = Build.MODEL;
        final String appVersion = AppUtils.getVersionName(this);
        final LoadingDialog loadingDialog = new LoadingDialog(mContext);
        loadingDialog.setContent("正在提交");
        loadingDialog.show();

        int userDefineType = 0;
        if (rtvSuggest != null) {
            userDefineType = rtvSuggest.isSelected() ? 0 : 1;
        }
        if (layoutType == LAYOUT_TEMPLATE) {
            submitFeedbackTemplate(phoneSystemType, phoneModel, appVersion, loadingDialog);
        } else if (layoutType == LAYOUT_CS || layoutType == LAYOUT_SMT) {
            submitFeedbackCs(phoneSystemType, "" + userDefineType, phoneModel, appVersion, loadingDialog);
        }
    }

    private void submitFeedbackCs(String phoneSystemType, String userDefineType, String phoneModel, String appVersion, LoadingDialog loadingDialog) {
        FeedBackServerManager.feedBack(imagePathList, userDefineType, phoneSystemType, phoneModel,
                appVersion,
                etOpinion.getText().toString(), new FeedBackServerManager.FeedCallBack() {
                    @Override
                    public void onSuccess() {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                        SPUtils.getInstance().setParam(FEED_BACK_TEXT, "");
                        Intent intent = new Intent();
                        intent.setClass(mContext, FeedBackSuccessActivity.class);
                        intent.putExtra(LAYOUT_TYPE,layoutType);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(String msg) {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                        ToastUtils.toastMsg(msg);
                        SPUtils.getInstance()
                                .setParam(FEED_BACK_TEXT,
                                        etOpinion.getText().toString());
                    }
                });
    }

    private void submitFeedbackTemplate(String phoneSystemType, String phoneModel, String appVersion, LoadingDialog loadingDialog) {
        FeedBackServerManager.feedBack(imagePathList, phoneSystemType, phoneModel,
                appVersion,
                etOpinion.getText().toString(), new FeedBackServerManager.FeedCallBack() {
                    @Override
                    public void onSuccess() {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                        SPUtils.getInstance().setParam(FEED_BACK_TEXT, "");
                        Intent intent = new Intent();
                        intent.setClass(mContext, FeedBackSuccessActivity.class);
                        intent.putExtra(LAYOUT_TYPE,layoutType);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(String msg) {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                        ToastUtils.toastMsg(msg);
                        SPUtils.getInstance()
                                .setParam(FEED_BACK_TEXT,
                                        etOpinion.getText().toString());
                    }
                });
    }

    private void showChooseDialog() {
        ChooseOptionDialog choosePhotoDialog =
                new ChooseOptionDialog(FeedBackActivity.this, R.layout.feedback_common_dialog_options);
        choosePhotoDialog.setOnSelectedListener(new ChooseOptionDialog.OnSelectedListener() {
            @Override
            public void onFirst() {
                //拍照选择
                //chooseFromCamera();
                PermissionUtils.requestWithDialog(FeedBackActivity.this, needCameraPermission)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    chooseFromCamera();
                                }
                            }
                        });
            }

            @Override
            public void onSecond() {
                //从相册选取
                //chooseFromGallery();
                PermissionUtils.requestWithDialog(FeedBackActivity.this, needSdcardPermission)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                if (layoutType==LAYOUT_TEMPLATE){
                                    NewPictureSelectActivity.actionStart(FeedBackActivity.this,
                                            REQUEST_CODE_PICTURE_SELECT,
                                            Max - imagePathList.size());
                                }else if(layoutType==LAYOUT_CS||layoutType==LAYOUT_SMT){
                                    PictureSelectActivity.actionStart(FeedBackActivity.this,
                                            REQUEST_CODE_PICTURE_SELECT,
                                            Max - imagePathList.size());
                                }
                            }
                        });
            }

            @Override
            public void onCancel() {

            }
        });
        choosePhotoDialog.show();
    }

    //拍照
    private void chooseFromCamera() {
        TakePhoto cameraTakePhoto = getTakePhoto();
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        .getAbsolutePath(), System.currentTimeMillis() + ".jpg");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        Uri imageUri1 = Uri.fromFile(file);
        configCompress(cameraTakePhoto);
        configTakePhotoOption(cameraTakePhoto);

        cameraTakePhoto.onPickFromCapture(imageUri1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type =
                PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICTURE_SELECT && data != null) {
            List<String> pictures = data.getStringArrayListExtra("images");
            if (pictures != null) {
                for (String picture : pictures) {
                    imagePathList.add(picture);
                }
            }
            showSelectPictures();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type =
                PermissionManager.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public void takeSuccess(TResult result) {
        for (Object image : result.getImages()) {
            imagePathList.add(((TImage)image).getOriginalPath());
        }
        showSelectPictures();
    }

    private void showSelectPictures() {
        if (layoutType == LAYOUT_TEMPLATE) {
            tvPhotoNum.setText(String.format(getResources().getString(R.string.business_mine_feedback_photo_num), "" + imagePathList.size()));
        }
        ivAdd.setVisibility(imagePathList.size() >= 4 ? View.GONE : View.VISIBLE);
        llOpinionImages.setVisibility(View.VISIBLE);

        llOpinionImages.removeAllViews();
        for (int i = 0; i < imagePathList.size(); i++) {
            final String imagePath = imagePathList.get(i);
            final View view =
                    LayoutInflater.from(this).inflate(R.layout.feedback_view_item_opinion_image, null);
            RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.img_opinion);
            ImageView imgRemove = (ImageView) view.findViewById(R.id.img_remove);
            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagePathList.remove(imagePath);
                    llOpinionImages.removeView(view);
                    ivAdd.setVisibility(imagePathList.size() >= 4 ? View.GONE : View.VISIBLE);
                    if (layoutType == LAYOUT_TEMPLATE) {
                        tvPhotoNum.setText(String.format(getResources().getString(R.string.business_mine_feedback_photo_num), "" + imagePathList.size()));
                    }
                }
            });

            PascImageLoader.getInstance().loadLocalImage(imagePath, imageView);
            //            PicassoUtils.loadImagePathWithCenterCropExt(imagePath, imageView);
            llOpinionImages.addView(view);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        PascLog.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        PascLog.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this)
                    .bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    //配置图片属性
    private void configTakePhotoOption(TakePhoto photo) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(false);//使用自带相册
        builder.setCorrectImage(false);//纠正旋转角度
        photo.setTakePhotoOptions(builder.create());
    }

    //    配置压缩
    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config = new CompressConfig.Builder().setMaxSize(102400)//大小不超过100k
                .setMaxPixel(800)//最大像素800
                .enableReserveRaw(true)//是否压缩
                .create();
        takePhoto.onEnableCompress(config, false);//这个trued代表显示压缩进度条
    }

    /**
     * 提示
     */
    @SuppressLint("ResourceType")
    private void showTipsDialog () {
        boolean hasShowFeedBackTips = (Boolean) SPUtils.getInstance().getParam
                (HAS_SHOW_FEEDBACK_TIPS, false);
        if (hasShowFeedBackTips){
            return;
        }

        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment.Builder()
                .setDesc(getString(R.string.business_mine_feedback_dialog_tips))
                .setDescLineSpacingExtra(4)
                .setDescSize(15)
                .setConfirmText("确认")
                .setConfirmTextSize(18)
                .setHideCloseButton(true)
                .setConfirmTextColor(getResources().getColor(R.color.red_e03a1f))
                .setOnConfirmListener(new OnConfirmListener<ConfirmDialogFragment>() {
                    @Override
                    public void onConfirm (ConfirmDialogFragment confirmDialogFragment) {
                        confirmDialogFragment.dismiss();
                    }
                })
                .build();
        confirmDialogFragment.show(getSupportFragmentManager(), "confirmDialogFragment");
        SPUtils.getInstance().setParam(HAS_SHOW_FEEDBACK_TIPS, true);
    }

}
