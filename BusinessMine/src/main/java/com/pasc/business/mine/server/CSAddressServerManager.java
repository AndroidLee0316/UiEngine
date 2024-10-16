package com.pasc.business.mine.server;

import com.pasc.business.mine.callback.OnResultListener;
import com.pasc.business.mine.params.AddressParam;
import com.pasc.business.mine.params.AreaItem;
import com.pasc.business.mine.params.UpdateAddressParam;
import com.pasc.business.mine.resp.AddressIdResp;
import com.pasc.business.mine.resp.AddressListResp;
import com.pasc.business.mine.resp.CSAddressListResp;
import com.pasc.business.mine.resp.QueryRealNameResp;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.net.resp.BaseRespObserver;
import com.pasc.lib.net.resp.VoidObject;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 功能：
 * <p>
 * create by lichangbao702
 * email : 1035268077@qq.com
 * date : 2018/10/19
 */
public class CSAddressServerManager implements AddressServerManagerInterface {

  /**
   * 监听解除器，所有的请求disposable都需要add 到这里，然后在 activity onDestroy中调用cleanDispoables解除监听
   */
  private CompositeDisposable mDisposables;

  public CSAddressServerManager() {
    mDisposables = new CompositeDisposable();
  }

  @Override
  public void getAreaListFromCache(final OnResultListener<List<AreaItem>> listener) {

    Disposable disposable = CSAddressBiz.getAreaListFromCache()
        .subscribe(new Consumer<List<AreaItem>>() {
          @Override
          public void accept(List<AreaItem> resp) throws Exception {
            if (listener != null) {
              listener.onSuccess(resp);
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            if (listener != null) {
              listener.onFailed("", throwable.getMessage());
            }
          }
        });
    mDisposables.add(disposable);
  }

  @Override
  public void getAreaListFromNet(final OnResultListener<List<AreaItem>> listener) {

    Disposable disposable = CSAddressBiz.getAreaListFromNet(
        AppProxy.getInstance().getUserManager().getUserInfo().getToken())
        .subscribe(new Consumer<List<AreaItem>>() {
          @Override
          public void accept(List<AreaItem> resp) throws Exception {
            if (listener != null) {
              listener.onSuccess(resp);
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            if (listener != null) {
              listener.onFailed("", throwable.getMessage());
            }
          }
        });
    mDisposables.add(disposable);
  }

  @Override
  public void addNewUserAddress(AddressParam param,
      final OnResultListener<AddressIdResp> listener) {

    CSAddressBiz.addNewUserAddress(AppProxy.getInstance().getUserManager().getUserInfo().getToken(),
        param)
        .subscribe(new BaseRespObserver<AddressIdResp>() {
          @Override
          public void onV2Error(String code, String msg) {
            if (listener != null) {
              listener.onFailed(code, msg);
            }
          }

          @Override
          public void onSuccess(AddressIdResp resp) {
            if (listener != null) {
              listener.onSuccess(resp);
            }
          }
        });
  }

  @Override
  public void getAddressList(final OnResultListener<AddressListResp> listener) {

    CSAddressBiz.getAddressList(AppProxy.getInstance().getUserManager().getToken())
        .subscribe(new BaseRespObserver<CSAddressListResp>() {
          @Override
          public void onV2Error(String code, String msg) {
            if (listener != null) {
              listener.onFailed(code, msg);
            }
          }

          @Override
          public void onSuccess(CSAddressListResp resp) {
            if (listener != null) {
              AddressListResp result = new AddressListResp();
              result.list = resp;
              listener.onSuccess(result);
            }
          }
        });
  }

  @Override
  public void setDefaultAddress(final String id, final OnResultListener<VoidObject> listener) {
    CSAddressBiz.setDefaultAddress(AppProxy.getInstance().getUserManager().getToken(), id)
        .subscribe(new BaseRespObserver<VoidObject>() {
          @Override
          public void onV2Error(String code, String msg) {
            if (listener != null) {
              listener.onFailed(code, msg);
            }
          }

          @Override
          public void onSuccess(VoidObject resp) {
            if (listener != null) {
              listener.onSuccess(resp);
            }
          }
        });
  }

  @Override
  public void updateAddress(final UpdateAddressParam addressParam,
      final OnResultListener<VoidObject> listener) {
    CSAddressBiz.updateAddress(AppProxy.getInstance().getUserManager().getToken(), addressParam)
        .subscribe(new BaseRespObserver<VoidObject>() {
          @Override
          public void onV2Error(String code, String msg) {
            if (listener != null) {
              listener.onFailed(code, msg);
            }
          }

          @Override
          public void onSuccess(VoidObject resp) {
            if (listener != null) {
              listener.onSuccess(resp);
            }
          }
        });
  }

  @Override
  public void deleteAddress(String id, final OnResultListener<VoidObject> listener) {
    CSAddressBiz.deleteAddress(AppProxy.getInstance().getUserManager().getToken(), id)
        .subscribe(new BaseRespObserver<VoidObject>() {
          @Override
          public void onV2Error(String code, String msg) {
            if (listener != null) {
              listener.onFailed(code, msg);
            }
          }

          @Override
          public void onSuccess(VoidObject resp) {
            if (listener != null) {
              listener.onSuccess(resp);
            }
          }
        });
  }

  @Override
  public void getRealNameType(OnResultListener<QueryRealNameResp> listener) {

  }

  @Override
  public void cleanDispoables() {
    if (mDisposables != null) {
      mDisposables.clear();
    }
  }
}
