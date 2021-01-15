package com.dbz.demo.model;

import com.dbz.demo.view.LineChartView;

import java.io.Serializable;
import java.util.List;

public class BaseCustomModel implements Serializable {

    private int code;
    private String msg;
    private List<LineChartView.XValue> modelList1;
    private List<LineChartView.LineValue> lineValues1;
    private List<LineChartView.XValue> modelList2;
    private List<LineChartView.LineValue> lineValues2;


    public List<LineChartView.XValue> getModelList1() {
        return modelList1;
    }

    public void setModelList1(List<LineChartView.XValue> modelList1) {
        this.modelList1 = modelList1;
    }

    public List<LineChartView.LineValue> getLineValues1() {
        return lineValues1;
    }

    public void setLineValues1(List<LineChartView.LineValue> lineValues1) {
        this.lineValues1 = lineValues1;
    }

    public List<LineChartView.XValue> getModelList2() {
        return modelList2;
    }

    public void setModelList2(List<LineChartView.XValue> modelList2) {
        this.modelList2 = modelList2;
    }

    public List<LineChartView.LineValue> getLineValues2() {
        return lineValues2;
    }

    public void setLineValues2(List<LineChartView.LineValue> lineValues2) {
        this.lineValues2 = lineValues2;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}