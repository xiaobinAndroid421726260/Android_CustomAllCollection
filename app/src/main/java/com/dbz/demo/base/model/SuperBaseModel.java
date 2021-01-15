package com.dbz.demo.base.model;

import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.util.GsonUtils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentLinkedDeque;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 应用模块: model
 * 类描述: 基类抽象model
 */
public abstract class SuperBaseModel<T> {

    protected final Handler mUiHandler = new Handler(Looper.getMainLooper());

    protected CompositeDisposable compositeDisposable;

    /**
     * 引用队列
     */
    protected final ReferenceQueue<IBaseModelListener> mReferenceQueue = new ReferenceQueue<>();

    /**
     * 线程安全队列
     */
    protected final ConcurrentLinkedDeque<WeakReference<IBaseModelListener>> mWeakReferenceDeque = new ConcurrentLinkedDeque<>();

    /**
     * 对具体业务model进行注册区分
     *
     * @param listener 业务监听器
     */
    public void register(IBaseModelListener listener) {
        if (null == listener) return;
        synchronized (this) {
            // 每次注册的时候清理已经被系统回收的对象
            Reference<? extends IBaseModelListener> reference;
            while ((reference = mReferenceQueue.poll()) != null) {
                mWeakReferenceDeque.remove(reference);
            }
            // 如果列队中 还存在此对象,就不用再次注册了
            for (WeakReference<IBaseModelListener> weakReference : mWeakReferenceDeque) {
                if (null != weakReference.get() && weakReference.get() == listener) return;
            }
            // 注册此listener对象
            WeakReference<IBaseModelListener> weakReference = new WeakReference<>(listener, mReferenceQueue);
            mWeakReferenceDeque.add(weakReference);
        }
    }

    /**
     * 取消注册
     *
     * @param listener listener
     */
    public void unRegister(IBaseModelListener listener) {
        if (null == listener) return;
        synchronized (this) {
            for (WeakReference<IBaseModelListener> weakReference : mWeakReferenceDeque) {
                if (null != weakReference.get() && weakReference.get() == listener){
                    mWeakReferenceDeque.remove(weakReference);
                    break;
                }
            }
        }
    }


    /**
     * 需要缓存的数据类型
     */
    protected Type getTclass() {
        return null;
    }

    /**
     * 该model的数据是否有apk预制的数据,如果有请返回
     */
    protected String getCacheData() {
        return null;
    }

    /**
     * 是否需要更新数据,默认每一次都需要更新
     *
     * @return true
     */
    protected boolean isNeedToUpData() {
        return true;
    }

    /**
     * 获取缓存数据并加载网络数据
     */
    public void getCacheDataAndLoad() {
        // 如果有apk内置数据,加载内置数据
        if (null != getCacheData()) {
            notifyCacheData((T) GsonUtils.fromJson(getCacheData(), getTclass()));
            if (isNeedToUpData()) {
                load();
            }
            return;
        }
        // 没有缓存数据,直接加载网络数据
        load();
    }

    /**
     * 加载缓存数据
     */
    protected abstract void notifyCacheData(T data);

    /**
     * 加载网络数据
     */
    protected abstract void load();

    /**
     * 订阅对象管理
     */
    public void addDisposable(Disposable disposable) {
        if (null == disposable) {
            return;
        }
        if (null == compositeDisposable) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅
     */
    public void cancel() {
        if (null != compositeDisposable && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
            compositeDisposable.isDisposed();
        }
    }

}