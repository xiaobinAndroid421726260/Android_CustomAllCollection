package com.dbz.demo.base.model;

/**
 * 应用模块: model
 */
public interface IModelListener<T> extends IBaseModelListener {
    /**
     * 数据加载完成
     *
     * @param model model
     * @param data  数据
     */
    void onLoadFinish(BaseModel<T> model, T data);

    /**
     * 数据加载失败
     *
     * @param model  model
     * @param error 错误
     */
    void onLoadFail(BaseModel<T> model, String error);
}
