package com.pasc.business.mine.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.pasc.lib.base.util.DensityUtils;
import com.pasc.lib.log.PascLog;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具方法类
 * <p>
 * Created by duyuan797 on 2017/3/30.
 */
public class CommonUtils {

  private static final int CLICK_AREA_VALUE = 50;

  private static double upX, upY;

  // 两次点击按钮之间的点击间隔不能少于200毫秒
  private static final int MIN_CLICK_DELAY_TIME = 200;
  private static long lastClickTime = System.currentTimeMillis();

  public static boolean isFastClick() {
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < MIN_CLICK_DELAY_TIME) {
      return true;
    }
    lastClickTime = time;
    return false;
  }

  public static boolean isFastClick(MotionEvent ev) {
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < MIN_CLICK_DELAY_TIME) {
      if (Math.abs(upX) > 0 && Math.abs(upY) > 0) {
        if (Math.abs(ev.getX(ev.getActionIndex()) - upX) < DensityUtils.px2dp(CLICK_AREA_VALUE)
            && Math.abs(ev.getY(ev.getActionIndex()) - upY) < DensityUtils.px2dp(
            CLICK_AREA_VALUE)) {
          return true;
        }
      }
      upX = ev.getX(ev.getActionIndex());
      upY = ev.getY(ev.getActionIndex());
    }
    lastClickTime = time;
    return false;
  }

  public static boolean isChinaPhoneLegal(String str) {
    //String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,4,5-9])|(17[0-8])|(147))\\d{8}$";
    String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(147))\\d{8}$";//稍微放宽检验范围
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(str);
    return m.matches();
  }

  /**
   * 判断密码各式是否正确
   */
  public static boolean isPasswordLegal(String password) {
    String regExp =
        "^(?=[\\u0021-\\u007e]{0,31}[a-z])(?=[\\u0021-\\u007e]{0,31}[A-Z])(?=[\\u0021-\\u007e]{0,31}\\d)[\\u0021-\\u007e]{8,32}$";//修改后的正则，由8-32位unicode0021-007e构成，必须包含数字，字母大小写
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(password);
    return m.matches();
  }

  /**
   * 判断验证码格式
   */
  public static boolean isVerifyCodeLegal(String verifyCode) {
    String regExp = "^[0-9]{6}$";
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(verifyCode);
    return m.matches();
  }

  /**
   * 用户名格式
   */
  public static boolean isUsernameLegal(String username) {
    String regExp = "^[a-zA-Z\\u4E00-\\u9FA5·]{2,18}+$";
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(username);
    return m.matches();
  }

  public static String checkSex(String IdNum) {
    String sex = "0";
    try {
      if (Integer.parseInt(IdNum.substring(16, 17)) % 2 == 0) {// 判断性别
        sex = "2";
      } else {
        sex = "1";
      }
    } catch (Exception e) {
      PascLog.e(e.getMessage());
    }
    return sex;
  }

  /**
   * 判断身份证号码是否正确
   *
   * @param idCard 身份证号码
   */
  public static boolean checkIdcardValid(String idCard) {
    return IdCardUtils.validate_effective(idCard);
  }

  /**
   * 判断身份证号码是否正确
   * 该方法有问题
   *
   * @param idNum 身份证号码
   */
  @Deprecated public static boolean checkIdcardValid2(String idNum) {
    int idLength = idNum.length();
    if (idLength != 15 && idLength != 18) {
      return false;
    }
    // 验证身份证号码出身月日
    String m = "";
    String d = "";
    if (idLength == 15) {
      m = idNum.substring(8, 10);
      d = idNum.substring(10, 12);
    } else {
      m = idNum.substring(10, 12);
      d = idNum.substring(12, 14);
    }
    int mInt = 0;
    int dInt = 0;
    // 如果字符串包含非数据字符，转换异常时返回false
    try {
      mInt = Integer.parseInt(m);
      dInt = Integer.parseInt(d);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }
    if (mInt > 12 || dInt > 31) {
      return false;
    }
    // 如果是15位身份证号码，不进行下面算法的验证
    if (idLength == 15) {
      return true;
    }

    // 加权因子 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
    int w[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    int sum = 0;
    for (int i = 0; i < 17; i++) {
      String a = idNum.substring(i, i + 1);
      int aInt = Integer.parseInt(a);
      sum += aInt * w[i];
    }
    int mod = sum % 11;
    boolean isValid = false;
    String lastC = idNum.substring(17, 18);
    // mod: 0 1 2 3 4 5 6 7 8 9 10
    // 校验码: 1 0 X 9 8 7 6 5 4 3 2
    switch (mod) {
      case 0:
        isValid = ("1".equals(lastC));
        break;
      case 1:
        isValid = ("0".equals(lastC));
        break;
      case 2:
        isValid = ("X".equals(lastC));
        if (!isValid) {
          isValid = ("x".equals(lastC));
        }
        break;
      case 3:
        isValid = ("9".equals(lastC));
        break;
      case 4:
        isValid = ("8".equals(lastC));
        break;
      case 5:
        isValid = ("7".equals(lastC));
        break;
      case 6:
        isValid = ("6".equals(lastC));
        break;
      case 7:
        isValid = ("5".equals(lastC));
        break;
      case 8:
        isValid = ("4".equals(lastC));
        break;
      case 9:
        isValid = ("3".equals(lastC));
        break;
      case 10:
        isValid = ("2".equals(lastC));
        break;
      default:
        break;
    }
    return isValid;
  }


  public static String formatBankNum(String bankNum) {
    return convertFormattedBankCardNum(bankNum).replaceAll("(\\d{4})", "$1 ").trim();
  }

  public static String convertFormattedBankCardNum(String bankNum) {
    return bankNum.replaceAll("\\s*", "");
  }

  /**
   * 非空判断
   */
  public static boolean isEmpty(CharSequence str) {
    return str == null || str.length() == 0 || str.toString().trim().length() == 0;
  }

  public static String formatPlayTime(int timeMillis) {
    int second = timeMillis / 1000;
    int min = second / 60;
    int h = min / 60;
    StringBuilder builder = new StringBuilder();
    if (h > 0) {
      builder.append(h);
      builder.append(":");
    }
    builder.append(min % 60);
    builder.append(":");
    builder.append(second % 60);
    return builder.toString();
  }

  /**
   * 隐藏软键盘
   */
  public static void hideSoftInput(EditText et) {
    InputMethodManager inputMethodManager =
        (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(),
        InputMethodManager.HIDE_NOT_ALWAYS);
  }

  /**
   * 获取版本名
   */
  public static String getVersionName(Context ctx) {
    String versionName = "";
    try {
      PackageInfo packageInfo = ctx.getApplicationContext()
          .getPackageManager()
          .getPackageInfo(ctx.getPackageName(), 0);
      versionName = packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return versionName;
  }

  public static String getHostName(String urlString) {
    String head = "";
    int index = urlString.indexOf("://");
    if (index != -1) {
      head = urlString.substring(0, index + 3);
      urlString = urlString.substring(index + 3);
    }
    index = urlString.indexOf("/");
    if (index != -1) {
      urlString = urlString.substring(0, index + 1);
    }
    return head + urlString;
  }

  /**
   * 半角转换为全角
   */
  public static String toDBC(String input) {
    if (TextUtils.isEmpty(input)) {
      return input;
    }
    char[] c = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      if (c[i] == 12288) {
        c[i] = (char) 32;
        continue;
      }
      if (c[i] > 65280 && c[i] < 65375){ c[i] = (char) (c[i] - 65248);}
    }
    return new String(c);
  }

  /**
   * 各种字符的unicode编码的范围：
   * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
   * 数字：[0x30,0x39]（或十进制[48, 57]）
   * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
   * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
   */
  public static boolean isLetterDigitOrChinese(String str) {
    String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";//其他需要，直接修改正则表达式就好
    return str.matches(regex);
  }

  /**
   * 将中文标点替换为英文标点
   *
   * @param text 要替换的文本
   * @return 替换后的文本
   */
  public static String replacePunctuation(String text) {
    text = text.replace('，', ',')
        .replace('。', '.')
        .replace('【', '[')
        .replace('】', ']')
        .replace('？', '?')
        .replace('！', '!')
        .replace('（', '(')
        .replace('）', ')')
        .replace('“', '"')
        .replace('”', '"');
    return text;
  }

  /**
   * 电话号码中间四位用*代替
   */
  public static String hidePhoneNo(String phoneNo) {
    StringBuilder stringBuilder = new StringBuilder();
    return stringBuilder.append(phoneNo.substring(0, 3))
        .append("****")
        .append(phoneNo.substring(phoneNo.length() - 4))
        .toString();
  }

  public static boolean isDate(String date) {
    /**
     * 判断日期格式和范围
     */
    String rexp =
        "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

    Pattern pat = Pattern.compile(rexp);

    Matcher mat = pat.matcher(date);

    boolean dateType = mat.matches();

    return dateType;
  }

  /**
   * 身份证号码中间16位用*代替
   */
  public static String hideIDNum(String idNum) {
    StringBuilder stringBuilder = new StringBuilder();
    return stringBuilder.append(idNum.substring(0, 1))
        .append("****************")
        .append(idNum.substring(idNum.length() - 1))
        .toString();
  }

  public static String get(String url, String key) {
    Uri uri = Uri.parse(url);
    String ywlx;
    ywlx = uri.getQueryParameter(key);
    return ywlx;
  }
}
