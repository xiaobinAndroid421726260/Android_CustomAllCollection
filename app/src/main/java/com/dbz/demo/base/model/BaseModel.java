package com.dbz.demo.base.model;

import java.lang.ref.WeakReference;

public abstract class BaseModel<T> extends SuperBaseModel<T>{

    /**
     * 网络数据加载成功,通知所有注册者加载数据
     *
     * @param data 数据bean
     */
    public void loadSuccess(T data) {
        synchronized (this){
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakReference : mWeakReferenceDeque) {
                    if (weakReference.get() instanceof IModelListener){
                        IModelListener<T> modelListener = (IModelListener<T>) weakReference.get();
                        if (modelListener != null){
                            modelListener.onLoadFinish(BaseModel.this, data);
                        }
                    }
                }
            }, 0);
        }
    }

    /**
     * 加载数据失败,通知所有注册者
     */
    protected void loadFail(String error) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakReference : mWeakReferenceDeque) {
                    if (weakReference.get() instanceof IModelListener) {
                        IModelListener<T> modelListener = (IModelListener<T>) weakReference.get();
                        if (modelListener != null){
                            modelListener.onLoadFail(BaseModel.this, error);
                        }
                    }
                }
            }, 0);
        }
    }

    @Override
    protected void notifyCacheData(T data) {
        loadSuccess(data);
    }
}