package com.example.sort.core;

import java.util.*;

/**
 * 冒泡排序
 */
public class BubbleSort implements Sort{

    private Comparable[] data;
    private Vector<String> steps;
    private int model;

    public BubbleSort(Comparable[] data, Vector<String> steps, int model) {
        this.data = data;
        this.steps = steps;
        this.model = model;
    }

    /**
     * 一轮冒泡，把最大/最小的元素冒泡到edge处
     * @param data 数据
     * @param model 排序类型 1：升序 2：降序
     * @param edge 排序的边界
     */
    public void bubble(Comparable[] data,int model,int edge){
        for(int i = 0;i < edge;i++){
            if ((data[i].compareTo(data[i+1])>0 && model == 1) || (data[i].compareTo(data[i+1])<0 && model == 2)){
                Comparable temp = data[i];
                data[i] = data[i+1];
                data[i+1] = temp;
                //保存排序状态
                steps.add(Arrays.toString(data));
            }
        }
    }

    //实现冒泡排序
    @Override
    public void sort() {
        //把初始状态添加到steps中
        steps.add(Arrays.toString(data));

        //循环进行data.length-1轮
        for(int i = data.length-1;i>0;i--){
            bubble(data,model,i);
        }

    }

    public void showSteps(){
        Enumeration enumeration = this.steps.elements();
        while (enumeration.hasMoreElements()){
            System.out.println(enumeration.nextElement());
        }
    }

    @Override
    public Vector<String> getSteps() {
        return this.steps;
    }
}
