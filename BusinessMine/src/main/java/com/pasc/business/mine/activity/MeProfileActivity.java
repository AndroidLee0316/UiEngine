package com.pasc.business.mine.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.business.mine.R;
import com.pasc.business.mine.config.MineConfigManager;
import com.pasc.business.mine.config.ProfileManager;
import com.pasc.business.mine.net.MineBiz;
import com.pasc.business.mine.resp.UploadHeadImgResp;
import com.pasc.business.mine.util.CommonUtils;
import com.pasc.business.mine.util.EventConstants;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.business.mine.util.RouterTable;
import com.pasc.business.mine.widget.ChooseOptionDialog;
import com.pasc.business.user.PascUserChangePhoneNumListener;
import com.pasc.business.user.PascUserConfig;
import com.pasc.business.user.PascUserManager;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.event.BaseEvent;
import com.pasc.lib.base.permission.PermissionUtils;
import com.pasc.lib.base.user.IUserInfo;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.net.resp.BaseRespObserver;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.picture.pictureSelect.NewPictureSelectActivity;
import com.pasc.lib.picture.takephoto.app.TakePhoto;
import com.pasc.lib.picture.takephoto.app.TakePhotoImpl;
import com.pasc.lib.picture.takephoto.compress.CompressConfig;
import com.pasc.lib.picture.takephoto.model.CropOptions;
import com.pasc.lib.picture.takephoto.model.InvokeParam;
import com.pasc.lib.picture.takephoto.model.TContextWrap;
import com.pasc.lib.picture.takephoto.model.TImage;
import com.pasc.lib.picture.takephoto.model.TResult;
import com.pasc.lib.picture.takephoto.model.TakePhotoOptions;
import com.pasc.lib.picture.takephoto.permission.InvokeListener;
import com.pasc.lib.picture.takephoto.permission.PermissionManager;
import com.pasc.lib.picture.takephoto.permission.TakePhotoInvocationHandler;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.router.interceptor.LoginInterceptor;
import com.pasc.lib.widget.toolbar.PascToolbar;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(
    path = RouterTable.MAIN_PROFILE
)
public class MeProfileActivity extends BaseStatusActivity
    implements TakePhoto.TakeResultListener, InvokeListener, View.OnClickListener {

  PascToolbar titleView;
  TextView nicknameView;
  TextView genderView;
  TextView tvPhone;
  TextView addressView;
  ImageView ivLogo;
  TextView tvCertificationView;
  TextView tvCertificationValue;
  TextView tvIdentityNo;
  private ChooseOptionDialog choosePhotoDialog, chooseGenderDialog;
  //拍照权限
  String[] needCameraPermission = new String[] {
      Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
  };
  String[] needSdcardPermission = new String[] {
      Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
  };
  private TakePhoto takePhoto;
  private InvokeParam invokeParam;
  private ArrayList<String> optionsItems = new ArrayList<>();
  private View viewPortrait, viewName, viewGender, viewMobile, viewNickName, viewAddress,
      viewIdentity, viewExtend;
  private View tvCertificationIcon;
  private View ivGenderArrow;
  private View ivIdentityArrow;
  private View ivNameArrow;
  private View ivAdressArrow;
  private TextView tvExtendTitle;

  public static void start(Context context) {
    Intent intent = new Intent(context, MeProfileActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int layoutResId() {
    return R.layout.mine_activity_me_profile;
  }

  @Override
  protected void onInit(@Nullable Bundle bundle) {
    // 传入自定义相册
    NewPictureSelectActivity.setIsHeadImg(true);
    getTakePhoto().customPickActivity(NewPictureSelectActivity.class);
    titleView = findViewById(R.id.top_bar);
    nicknameView = findViewById(R.id.tv_nickname_desc);
    genderView = findViewById(R.id.gender_desc);
    tvPhone = findViewById(R.id.tv_phone_number_value);
    addressView = findViewById(R.id.address_desc);
    ivLogo = findViewById(R.id.riv_head_logo);
    tvCertificationView = findViewById(R.id.name_desc);
    tvCertificationIcon = findViewById(R.id.tv_certification);
    tvCertificationValue = findViewById(R.id.tv_name_value);
    tvIdentityNo = findViewById(R.id.identity_desc);

    viewPortrait = findViewById(R.id.view_portrait);
    viewNickName = findViewById(R.id.view_nickname);
    viewGender = findViewById(R.id.view_gender);
    viewMobile = findViewById(R.id.view_mobile);
    viewName = findViewById(R.id.view_name);
    viewAddress = findViewById(R.id.view_address);
    viewIdentity = findViewById(R.id.view_identity);
    viewExtend = findViewById(R.id.view_extend);
    ivGenderArrow = findViewById(R.id.iv_gender_arrow);
    ivIdentityArrow = findViewById(R.id.iv_identity_arrow);
    ivNameArrow = findViewById(R.id.iv_name_arrow);
    ivAdressArrow = findViewById(R.id.iv_address_arrow);

    tvExtendTitle = findViewById(R.id.title);

    ivLogo.setOnClickListener(this);
    nicknameView.setOnClickListener(this);
    viewGender.setOnClickListener(this);
    genderView.setOnClickListener(this);
    tvCertificationView.setOnClickListener(this);
    tvCertificationValue.setOnClickListener(this);
    tvIdentityNo.setOnClickListener(this);
    addressView.setOnClickListener(this);
    viewAddress.setOnClickListener(this);
    viewGender.setOnClickListener(this);
    viewIdentity.setOnClickListener(this);
    viewName.setOnClickListener(this);
    viewMobile.setOnClickListener(this);
    viewNickName.setOnClickListener(this);
    titleView.addCloseImageButton()
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });

    EventBus.getDefault().register(this);
    updateUIFromConfig();
    initData();
  }

  private void updateUIFromConfig() {
    viewPortrait.setVisibility(
        ProfileManager.getInstance().showPortrait() ? View.VISIBLE : View.GONE);
    viewNickName.setVisibility(
        ProfileManager.getInstance().showNickname() ? View.VISIBLE : View.GONE);
    viewMobile.setVisibility(
        ProfileManager.getInstance().showMobile() ? View.VISIBLE : View.GONE);
    viewName.setVisibility(
        ProfileManager.getInstance().showCertification() ? View.VISIBLE : View.GONE);
    viewAddress.setVisibility(
        ProfileManager.getInstance().showAddress() ? View.VISIBLE : View.GONE);
    viewGender.setVisibility(ProfileManager.getInstance().showGender() ? View.VISIBLE : View.GONE);
    viewIdentity.setVisibility(
        ProfileManager.getInstance().showIdentity() ? View.VISIBLE : View.GONE);
    if (ProfileManager.getInstance().showExtend()) {
      viewExtend.setVisibility(View.VISIBLE);
      MineConfigManager.ExtendInfo extendInfo=
          ProfileManager.getInstance().getProfileConfigBean().extendInfo;
      tvExtendTitle.setText(extendInfo.title==null?"":extendInfo.title);
      viewExtend.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          Bundle bundle=new Bundle();
          bundle.putBoolean("needIdentity",extendInfo.needCert);
          BaseJumper.jumpBundleARouter(extendInfo.router,bundle);
        }
      });
    } else {
      viewExtend.setVisibility(View.GONE);
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN) private void initData() {
    optionsItems.add("男");
    optionsItems.add("女");
    initTypePickerView();
    if (AppProxy.getInstance().getUserManager().isLogin()) {
      IUserInfo userInfo = AppProxy.getInstance().getUserManager().getUserInfo();
      int defaultHeadimg = R.drawable.mine_ic_head_default_logged;
      if ("1".equals(userInfo.getSex())) {
        defaultHeadimg = R.drawable.mine_ic_default_head_male;
      } else if ("2".equals(userInfo.getSex())) {
        defaultHeadimg = R.drawable.mine_ic_default_head_female;
      }
      PascImageLoader.getInstance()
          .loadImageUrl(userInfo.getHeadImg(), ivLogo,
              defaultHeadimg, PascImageLoader.SCALE_DEFAULT);
      setUserMsg(AppProxy.getInstance().getUserManager().getUserInfo());
    }
    titleView.setTitle(getResources().getString(R.string.business_mine_profile));
    addressView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            int lineCount = addressView.getLineCount();
            if (lineCount > 1) {
              addressView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            }
            addressView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
          }
        });
  }

  private void setUserMsg(IUserInfo user) {
    String sexType = user.getSex();
    String sexText;
    if (TextUtils.isEmpty(sexType) || "0".equals(sexType)) {
      sexText = "未选择";
      genderView.setTextColor(getResources().getColor(R.color.gray_c7c7c7));
      ivGenderArrow.setVisibility(View.VISIBLE);
    } else {
      sexText = (("1".equals(user.getSex())) ? "男" : "女");
      genderView.setTextColor(getResources().getColor(R.color.meprofile_item_right_color));
      ivGenderArrow.setVisibility(View.GONE);
    }
    genderView.setText(sexText);
    setTextView(nicknameView, generateNickname());
    setTextView(addressView, user.getAddress());
    if (!TextUtils.isEmpty(user.getMobileNo())
        && user.getMobileNo().length() >= 11) {
      String dest = user.getMobileNo().substring(0, 3)
          + "******"
          + user.getMobileNo().substring(9, 11);
      tvPhone.setText(dest);
    }
    setCertificationView(tvCertificationView, user);
  }

  private String generateNickname() {
    boolean isChanged =
        "1".equals(AppProxy.getInstance().getUserManager().getUserInfo().getNickNameStatus());
    IUserInfo userInfo = AppProxy.getInstance().getUserManager().getUserInfo();
    if (isChanged) {
      return userInfo.getNickName();
    }
    if (AppProxy.getInstance().getUserManager().isCertified()) {
      return maskRealName(userInfo.getUserName());
    }
    return userInfo.getNickName();
  }

  /**
   * 接收到实名认证成功后刷新个人资料
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void onCertification(BaseEvent event) {
    if (EventConstants.USER_CERTIFICATE_SUCCEED.equals(event.getTag())) {
      setUserMsg(AppProxy.getInstance().getUserManager().getUserInfo());
    }
  }

  /**
   * 个人信用直接点击返回的通知
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void onCertificationRefresh(
      BaseEvent event) {
    if (EventConstants.USER_CERTIFICATE_NOT.equals(event.getTag())) {
      setUserMsg(AppProxy.getInstance().getUserManager().getUserInfo());
    }
  }

  private String maskRealName(String username) {
    String maskName = "";
    if (!TextUtils.isEmpty(username)) {
      maskName =
          username.length() > 2 ? username.substring(0, 1) + "*" + username.substring(
              username.length() - 1, username.length())
              : "*" + username.substring(username.length() - 1, username.length());
    }
    return maskName;
  }

  /**
   * 设置实名认证
   */
  private void setCertificationView(TextView tvCertificationView, IUserInfo user) {
    if (user == null) {
      return;
    }
    if (AppProxy.getInstance().getUserManager().isCertified()) { //已认证
      ivGenderArrow.setVisibility(View.GONE);
      ivIdentityArrow.setVisibility(View.GONE);
      tvCertificationView.setVisibility(View.GONE);
      tvCertificationValue.setVisibility(View.VISIBLE);
      tvCertificationValue.setText(maskRealName(user.getUserName()));
      ivNameArrow.setVisibility(View.GONE);

      tvIdentityNo.setText(!TextUtils.isEmpty(user.getIdCard()) ? user.getIdCard().substring(0, 1)
          + "***************" + user.getIdCard().substring(user.getIdCard().length() - 1) : "");
      tvIdentityNo.setCompoundDrawables(null, null, null, null);
      tvCertificationIcon.setVisibility(View.VISIBLE);
      //tvCertificationValue.setClickable(false);
      tvIdentityNo.setTextColor(getResources().getColor(R.color.meprofile_item_right_color));
    } else {

      tvCertificationView.setVisibility(View.VISIBLE);
      ivNameArrow.setVisibility(View.VISIBLE);
      tvCertificationValue.setVisibility(View.GONE);
      tvCertificationIcon.setVisibility(View.GONE);
      tvCertificationView.setText("未认证");
      tvIdentityNo.setText("未认证");
      tvIdentityNo.setTextColor(getResources().getColor(R.color.gray_c7c7c7));
      ivGenderArrow.setVisibility(View.VISIBLE);
      ivIdentityArrow.setVisibility(View.VISIBLE);
    }
  }

  private void setTextView(TextView textView, String text) {
    if (TextUtils.isEmpty(text)) {
      textView.setText("");
      textView.setTextColor(getResources().getColor(R.color.gray_c7c7c7));
    } else {
      text = CommonUtils.toDBC(text);
      textView.setText(text);
      textView.setTextColor(getResources().getColor(R.color.meprofile_item_right_color));
    }
  }

  /**
   * 刷新信息
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void refresh(BaseEvent event) {
    if (EventConstants.USER_MODIFY_USER.equals(event.getTag())) {
      setUserMsg(AppProxy.getInstance().getUserManager().getUserInfo());
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.iv_title_left) {
      onBackPressed();
    } else if (i == R.id.view_mobile) {
      PascUserManager.getInstance().toChangePhoneNum(new PascUserChangePhoneNumListener() {
        @Override
        public void onSuccess() {
          setUserMsg(AppProxy.getInstance().getUserManager().getUserInfo());
        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onCanceld() {

        }
      });
    } else if (i == R.id.tv_nickname_desc || i == R.id.view_nickname) {
      actionStart(NicknameActivity.class);
      HashMap<String, String> map = new HashMap<>();
      map.put(EventUtils.KEY_USER_NAME, EventUtils.VALUE_USER_NAME);
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);
    } else if (i == R.id.gender_desc || i == R.id.view_gender) {
      if (AppProxy.getInstance().getUserManager().isCertified()) {
        return;
      }
      HashMap<String, String> map = new HashMap<>();
      map.put(EventUtils.KEY_GENDER, EventUtils.VALUE_GENDER);
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);
      if (chooseGenderDialog != null) {
        chooseGenderDialog.show();
      }
    } else if (i == R.id.identity_desc || i == R.id.view_identity) {
      // 跳转认证页
      if (AppProxy.getInstance().getUserManager().isCertified()) {
        return;
      }
      HashMap<String, String> map = new HashMap<>();
      map.put(EventUtils.KEY_IDCARD, EventUtils.VALUE_IDCARD);
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);
      PascUserManager.getInstance().toCertification(PascUserConfig.CERTIFICATION_TYPE_ALL,null);

    } else if (i == R.id.address_desc || i == R.id.view_address) {
      actionStart(MyAddressActivity.class);

      HashMap<String, String> map = new HashMap<>();
      map.put(EventUtils.KEY_ADDRESS, EventUtils.VALUE_ADDRESS);
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);
    } else if (i == R.id.riv_head_logo) {
      HashMap<String, String> map = new HashMap<>();
      map.put(EventUtils.KEY_AVATAR, EventUtils.VALUE_AVATAR);
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);
      showChooseDialog();
    } else if (i == R.id.tv_name_value || i == R.id.name_desc || i == R.id.view_name) {
      HashMap<String, String> map = new HashMap<>();
      map.put(EventUtils.KEY_IDCARD, EventUtils.VALUE_IDCARD);
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);
      if (AppProxy.getInstance().getUserManager().isCertified()) {
        return;
      }
      PascUserManager.getInstance().toCertification(PascUserConfig.CERTIFICATION_TYPE_ALL,null);
    }
  }

  private void showChooseDialog() {
    if (choosePhotoDialog == null) {
      choosePhotoDialog =
          new ChooseOptionDialog(this, R.layout.mine_user_dialog_options);
      choosePhotoDialog.setOnSelectedListener(new ChooseOptionDialog.OnSelectedListener() {
        @Override public void onFirst() {
          //拍照选择
          //chooseFromCamera();
        /*  PermissionHelper.requestWidthDialog(MeProfileActivity.this, needCameraPermission)
              .subscribe(new Consumer<Boolean>() {
                @Override public void accept(Boolean aBoolean) throws Exception {

                }
              });*/
          PermissionUtils.requestWithDialog(MeProfileActivity.this, needCameraPermission)
              .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                  if (aBoolean) {
                    chooseFromCamera();

                    HashMap<String, String> map = new HashMap<>();
                    map.put(EventUtils.KEY_TAKE_PHOTOS, EventUtils.VALUE_TAKE_PHOTOS);
                    EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);
                  } else {
                    //                  PermissionSettingUtils.gotoPermissionSetting(
                    //                      MeProfileActivity.this);
                  }
                }
              });
        }

        @Override public void onSecond() {
          //chooseFromGallery();
         /* PermissionHelper.requestWidthDialog(MeProfileActivity.this, needSdcardPermission)
              .subscribe(new Consumer<Boolean>() {
                @Override public void accept(Boolean aBoolean) throws Exception {
                  if (aBoolean) {
                    chooseFromGallery();
                  } else {
                    //                  PermissionSettingUtils.gotoPermissionSetting(
                    //                      MeProfileActivity.this);
                  }
                }
              });*/

          PermissionUtils.requestWithDialog(MeProfileActivity.this, needSdcardPermission)
              .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                  if (aBoolean) {
                    //                    chooseFromGallery();
                    HashMap<String, String> map = new HashMap<>();
                    map.put(EventUtils.KEY_SELECT_IN_ALBUM, EventUtils.VALUE_SELECT_IN_ALBUM);
                    EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_USER_DATA, map);

                    configTakePhotoOption(getTakePhoto());
                    configCompress(getTakePhoto());
                    getTakePhoto().onPickMultipleWithCrop(1, getCropOptions());
                  } else {
                    //                  PermissionSettingUtils.gotoPermissionSetting(
                    //                      MeProfileActivity.this);
                  }
                }
              });
        }

        @Override public void onCancel() {
        }
      });
    }
    if (choosePhotoDialog != null && !choosePhotoDialog.isShowing()) {
      choosePhotoDialog.show();
    }
  }

  //从相册选择
  private void chooseFromGallery() {
    TakePhoto cameraTakePhoto = getTakePhoto();
    File file = new File(Environment.getExternalStorageDirectory(),
        "/temp" + System.currentTimeMillis() + ".jpg");

    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdirs();
    }

    Uri imageUri1 = Uri.fromFile(file);
    configCompress(cameraTakePhoto);
    configTakePhotoOption(cameraTakePhoto);

    cameraTakePhoto.onPickFromGalleryWithCrop(imageUri1, getCropOptions());
  }

  //拍照
  private void chooseFromCamera() {
    TakePhoto cameraTakePhoto = getTakePhoto();
    File file = new File(Environment.getExternalStorageDirectory(),
        "/temp" + System.currentTimeMillis() + ".jpg");

    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdirs();
    }

    Uri imageUri1 = Uri.fromFile(file);
    configCompress(cameraTakePhoto);
    configTakePhotoOption(cameraTakePhoto);

    cameraTakePhoto.onPickFromCaptureWithCrop(imageUri1, getCropOptions());
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    getTakePhoto().onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    getTakePhoto().onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionManager.TPermissionType type =
        PermissionManager.onRequestPermissionsResult(requestCode, permissions,
            grantResults);
    PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
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

  @Override public void takeSuccess(TResult result) {
    Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
    ArrayList<TImage> images = result.getImages();
    String path = images.get(images.size() - 1).getCompressPath();
    uploadImageToNet(path);
  }

  /**
   * 上传图片到网络
   */
  private void uploadImageToNet(final String path) {
    //showLoading("图片上传中");
    MineBiz.uploadHeadImg(path)
        .compose(this.<UploadHeadImgResp>bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(new BaseRespObserver<UploadHeadImgResp>() {
          @Override public void onError(int code, String msg) {
            ToastUtils.toastMsg(msg);
            //dismissLoading();
          }

          @Override public void onSuccess(UploadHeadImgResp uploadHeadImgResp) {
            super.onSuccess(uploadHeadImgResp);
            //dismissLoading();
            String headImg = uploadHeadImgResp.headImg;
            Bundle bundle = new Bundle();
            IUserInfo userInfo = AppProxy.getInstance().getUserManager().getUserInfo();
            userInfo.setHeadImg(headImg);
            AppProxy.getInstance().getUserManager().updateUser(userInfo);
            File file = new File(path);
            Log.e("path", path);
            //PascImageLoader.getInstance()(MeProfileActivity.this).load(file).into(ivLogo);
            PascImageLoader.getInstance().loadLocalImage(path, ivLogo);
            EventBus.getDefault().post(new BaseEvent(EventConstants.USER_MODIFY_USER));
          }
        });
  }

  @Override public void takeFail(TResult result, String msg) {
    Log.i(TAG, "takeFail:" + msg);
  }

  @Override public void takeCancel() {
    Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
  }

  @Override public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
    PermissionManager.TPermissionType type =
        PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
    if (PermissionManager.TPermissionType.WAIT.equals(type)) {
      this.invokeParam = invokeParam;
    }
    return type;
  }

  //裁剪图片属性
  private CropOptions getCropOptions() {
    CropOptions.Builder builder = new CropOptions.Builder();
    builder.setAspectX(800).setAspectY(800);//裁剪时的尺寸比例
    //builder.setOutputX(800).setOutputY(800);
    builder.setWithOwnCrop(false);//s使用第三方还是takephoto自带的裁剪工具
    return builder.create();
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
   * 实例化选择器
   */
  private void initTypePickerView() {
    chooseGenderDialog = new ChooseOptionDialog(this, R.layout.mine_user_dialog_gender);
    chooseGenderDialog.setOnSelectedListener(new ChooseOptionDialog.OnSelectedListener() {
      @Override public void onFirst() {
        modifyUserSex("男");
      }

      @Override public void onSecond() {
        modifyUserSex("女");
      }

      @Override public void onCancel() {
      }
    });
  }

  /**
   * 修改用户信息
   */
  private void modifyUserSex(final String sex) {

    //showLoading();
    final int sexType = (("男".equals(sex)) ? 1 : 2);
    MineBiz.modifyUserMsg(AppProxy.getInstance().getUserManager().getToken(), null, sexType)
        .compose(this.<VoidObject>bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe(new BaseRespObserver<VoidObject>() {
          @Override public void onError(int code, String msg) {
            ToastUtils.toastMsg(msg);
            //dismissLoading();
          }

          @Override public void onSuccess(VoidObject voidObject) {
            super.onSuccess(voidObject);
            setTextView(genderView, sex);
            //User user = UserRouterBiz.fetchUserInfoServices().getUser();
            IUserInfo userInfo = AppProxy.getInstance().getUserManager().getUserInfo();
            userInfo.setSex(String.valueOf(sexType));
            AppProxy.getInstance().getUserManager().updateUser(userInfo);
            int defaultHeadimg = R.drawable.mine_ic_head_default_logged;
            if (1 == sexType) {
              defaultHeadimg = R.drawable.mine_ic_default_head_male;
            } else if (2 == sexType) {
              defaultHeadimg = R.drawable.mine_ic_default_head_female;
            }
            PascImageLoader.getInstance()
                .loadImageUrl(userInfo.getHeadImg(), ivLogo, defaultHeadimg,
                    PascImageLoader.SCALE_DEFAULT);
            EventBus.getDefault().post(new BaseEvent(EventConstants.USER_MODIFY_USER));
            //dismissLoading();
          }
        });
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEventMsg(BaseEvent event) {
    if (EventConstants.USER_MODIFY_USER.equals(event.getTag())) {
      setUserMsg(AppProxy.getInstance().getUserManager().getUserInfo());
    }
  }
}
