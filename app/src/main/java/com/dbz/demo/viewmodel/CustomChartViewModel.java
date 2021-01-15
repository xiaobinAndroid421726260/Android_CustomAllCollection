package com.dbz.demo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dbz.demo.base.model.BaseModel;
import com.dbz.demo.base.viewmodel.BaseViewModel;
import com.dbz.demo.model.BaseCustomModel;
import com.dbz.demo.model.CustomChartModel;

public class CustomChartViewModel extends BaseViewModel<CustomChartModel, BaseCustomModel> {

    private final MutableLiveData<BaseCustomModel> mBaseCustomModel = new MutableLiveData<>();

    public CustomChartViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected CustomChartModel initModel() {
        return new CustomChartModel();
    }

    @Override
    public void onLoadFinish(BaseModel<BaseCustomModel> model, BaseCustomModel data) {
        mBaseCustomModel.setValue(data);
    }

    @Override
    public void onLoadFail(BaseModel<BaseCustomModel> model, String error) {

    }

    public MutableLiveData<BaseCustomModel> getBaseCustomModel() {
        return mBaseCustomModel;
    }
}