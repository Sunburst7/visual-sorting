package com.example.sort.core;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 插入排序
 */
public class InsertSort implements Sort{

    private Comparable[] data;
    private Vector<String> steps;
    private int model;

    public InsertSort(Comparable[] data, Vector<String> steps, int model) {
        this.data = data;
        this.steps = steps;
        this.model = model;
    }

    @Override
    public void sort() {
        //添加初始状态
        steps.add(Arrays.toString(data));
        for (int i = 1;i<this.data.length;i++){
            Comparable temp = data[i];

            for(int j = i-1;j>=0 &&
                    ((data[j].compareTo(data[j+1])>0 && model == 1) || (data[j].compareTo(data[j+1])<0 && model == 2));
                j--)
            {
                data[j+1] = data[j];
                data[j] = temp;
                //添加过程
                steps.add(Arrays.toString(data));
            }

        }
    }

    @Override
    public void showSteps() {
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
