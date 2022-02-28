package com.example.sort.core;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 快速排序
 */
public class QuickSort implements Sort{

    private Comparable[] data;
    private Vector<String> steps;
    private int model;

    public QuickSort(Comparable[] data, Vector<String> steps, int model) {
        this.data = data;
        this.steps = steps;
        this.model = model;
    }

    //快速排序的递归方法
    private void quick_sort(Comparable[] data,int l,int r){
        //添加初始情况
        this.steps.add(Arrays.toString(this.data));
        if (l < r) {
            //Swap(s[l], s[(l + r) / 2]); //如果基准数是中间的数的话，将中间的这个数和第一个数交换
            int i = l;
            int j = r;
            Comparable standard = data[l]; //确定基数
            while (i < j)
            {
                // 从右边搜索
                // 升序:从右向左找第一个小于等于基数的数;降序:从右往左找第一个大于等于基数的数
                while(i < j && ((data[j].compareTo(standard)>=0&&this.model == 1) || (data[j].compareTo(standard)<=0&&this.model == 2)))
                    j--;
                if(i < j){
                    data[i] = data[j]; //填下第一个坑
                    i++;
                    this.steps.add(Arrays.toString(this.data));
                }

                //从左边搜索
                // 升序：从左向右找第一个大于基数的数；降序：从左往右找第一个小于基数的数
                while(i < j && ((data[i].compareTo(standard)<0&&this.model == 1) || (data[i].compareTo(standard)>0&&this.model == 2)))
                    i++;
                if(i < j){
                    data[j] = data[i];
                    j--;
                    this.steps.add(Arrays.toString(this.data));
                }

            }
            data[i] = standard;
            quick_sort(data, l, i - 1); // 递归调用
            quick_sort(data, i + 1, r);
        }
    }

    @Override
    public void sort() {
        quick_sort(this.data,0,this.data.length-1);
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
