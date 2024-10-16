package com.pasc.business.workspace;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.pasc.lib.widget.tangram.DefaultInnerImageSetter;
import com.pasc.lib.workspace.BizHandler;
import com.pasc.lib.workspace.CacheProxy;
import com.pasc.lib.workspace.ICache;
import com.pasc.lib.workspace.User;
import com.pasc.lib.workspace.UserProxy;
import com.pasc.lib.workspace.WorkspaceBiz;
import com.pasc.lib.workspace.api.DaoBuilder;
import com.pasc.lib.workspace.api.DaoFactory;
import com.pasc.lib.workspace.handler.HandlerProxy;
import com.pasc.lib.workspace.handler.IStat;
import com.pasc.lib.workspace.handler.ProtocolHandler;
import com.pasc.lib.workspace.handler.StatProxy;
import com.pasc.lib.workspace.handler.UrlCreator;
import com.pasc.lib.workspace.handler.UrlProxy;
import com.tmall.wireless.tangram.TangramBuilder;

/**
 * 动态页面管理.
 * Created by chenruihan410 on 2018/08/07.
 */
public class WorkspaceManager {

    private static WorkspaceManager instance = new WorkspaceManager();
    private static final String TAG = "WorkspaceManager";
    private Context context;

    public static WorkspaceManager getInstance() {
        return instance;
    }

    public WorkspaceManager init(final Context context) {
        if (context == null) {
            throw new RuntimeException("Context不能为空");
        }
        this.context = context.getApplicationContext();

        WorkspaceBiz.getInstance().init(context);
        HandlerProxy.getInstance().init(context);

        TangramBuilder.init(context, new DefaultInnerImageSetter(), ImageView.class);

        return instance;
    }

    // 初始化用户代理.
    public WorkspaceManager initUserProxy(User user) {
        UserProxy.getInstance().init(user);
        return this;
    }

    // 初始化统计代理。
    public WorkspaceManager initStatProxy(IStat stat) {
        StatProxy.getInstance().init(stat);
        return this;
    }

    public WorkspaceManager initUrlProxy(UrlCreator urlCreator) {
        UrlProxy.getInstance().init(urlCreator);
        return this;
    }

    public WorkspaceManager initBizHandler(BizHandler bizHandler) {
        WorkspaceBiz.getInstance().initBizHandler(bizHandler);
        return this;
    }

    // 初始化处理器构建者
    public WorkspaceManager initProtocolHandler(ProtocolHandler protocolHandler) {
        HandlerProxy.getInstance().setProtocolHandler(protocolHandler);
        return this;
    }

    // 初始化处理器构建者
    public WorkspaceManager initDaoBuilder(DaoBuilder daoBuilder) {
        DaoFactory.getInstance().setDaoBuilder(daoBuilder);
        return this;
    }

    /**
     * 通过integer方式获取tag.
     *
     * @param view         视图.
     * @param integerResId integer的资源id.
     * @return tag里的对象.
     */
    private Object getTagByInteger(View view, int integerResId) {
        int tagKeyDefaultImage = view.getResources().getInteger(integerResId);
        return view.getTag(tagKeyDefaultImage);
    }

    private WorkspaceManager() {
    }

    public WorkspaceManager initCacheProxy(ICache cache) {
        CacheProxy.getInstance().init(cache);
        return this;
    }
}
