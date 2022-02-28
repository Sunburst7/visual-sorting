package com.example.sort.entity;

/**
 * 实体类 封装前端传来的Data
 */
public class DataForm {

    private String dataList;
    private String dataType;
    private int model;

    public DataForm(String dataList, String dataType, int model) {
        this.dataList = dataList;
        this.dataType = dataType;
        this.model = model;
    }

    public String getDataList() {
        return dataList;
    }

    public void setDataList(String dataList) {
        this.dataList = dataList;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "DataForm{" +
                "dataList='" + dataList + '\'' +
                ", dataType='" + dataType + '\'' +
                ", model=" + model +
                '}';
    }
}
