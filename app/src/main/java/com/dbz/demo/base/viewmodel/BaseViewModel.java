package com.dbz.demo.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dbz.demo.base.model.BaseModel;
import com.dbz.demo.base.model.IModelListener;

public abstract class BaseViewModel<M extends BaseModel, T> extends AndroidViewModel implements IModelListener<T> {

    protected M mModel;

    protected abstract M initModel();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mModel = initModel();
    }

    public void getCacheDataAndLoad(){
        if (null != mModel) {
            mModel.getCacheDataAndLoad();
        }
    }

    public void register() {
        if (null != mModel) {
            mModel.register(this);
        }
    }

    public void unRegister() {
        if (null != mModel) {
            mModel.unRegister(this);
        }
    }

    public static <VM extends BaseViewModel> ViewModelProvider.Factory createViewModelFactory(VM viewModel) {
        return new ViewModelFactory(viewModel);
    }

    public static class ViewModelFactory implements ViewModelProvider.Factory {

        private final BaseViewModel viewModel;

        public ViewModelFactory(BaseViewModel vm) {
            this.viewModel = vm;
        }

        @NonNull
        @Override
        public <VM extends ViewModel> VM create(@NonNull Class<VM> modelClass) {
            return (VM) viewModel;
        }
    }
}