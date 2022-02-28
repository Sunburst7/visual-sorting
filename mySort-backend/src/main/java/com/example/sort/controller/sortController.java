package com.example.sort.controller;

import com.example.sort.core.BubbleSort;
import com.example.sort.core.InsertSort;
import com.example.sort.core.QuickSort;
import com.example.sort.core.Sort;
import com.example.sort.entity.DataForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Vector;

/**
 * 控制器类
 */
@Controller
public class sortController {

    public Sort sort;
    ObjectMapper mapper = new ObjectMapper();
    String json;

    @PostMapping(value = {"/BubbleSort"})
    @ResponseBody
    public String HandleBubbleSort(@RequestBody DataForm data) throws JsonProcessingException {
        System.out.println(data);
        //保存每步的答案
        Vector<String> steps = new Vector<>();
        //把字符串先分割
        String[] dataSource = data.getDataList().split(",");
        //传来的是String类型
        if(data.getDataType().equals("String")){
            sort = new BubbleSort(dataSource,steps,data.getModel());
        }else if(data.getDataType().equals("Integer")) {
            Integer[] dataList = new Integer[dataSource.length];
            for(int i = 0;i< dataSource.length;i++){
                dataList[i] = Integer.parseInt(dataSource[i]);
            }
            sort = new BubbleSort(dataList,steps,data.getModel());
        }

        sort.sort();

        //利用jackson转化成json
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(steps);
        System.out.println(json);
        return json;
    }

    @PostMapping(value = {"/InsertSort"})
    @ResponseBody
    public String HandleInsertSort(@RequestBody DataForm data) throws JsonProcessingException {
        System.out.println(data);
        //保存每步的答案
        Vector<String> steps = new Vector<>();
        //把字符串先分割
        String[] dataSource = data.getDataList().split(",");
        //传来的是String类型
        if(data.getDataType().equals("String")){
            sort = new InsertSort(dataSource,steps,data.getModel());
        }else if(data.getDataType().equals("Integer")) {
            Integer[] dataList = new Integer[dataSource.length];
            for(int i = 0;i< dataSource.length;i++){
                dataList[i] = Integer.parseInt(dataSource[i]);
            }
            sort = new InsertSort(dataList,steps,data.getModel());
        }

        sort.sort();

        //利用jackson转化成json
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(steps);
        System.out.println(json);
        return json;
    }


    @PostMapping(value = {"/QuickSort"})
    @ResponseBody
    public String HandleQuickSort(@RequestBody DataForm data) throws JsonProcessingException {
        System.out.println(data);
        //保存每步的答案
        Vector<String> steps = new Vector<>();
        //把字符串先分割
        String[] dataSource = data.getDataList().split(",");
        //传来的是String类型
        if(data.getDataType().equals("String")){
            sort = new QuickSort(dataSource,steps,data.getModel());
        }else if(data.getDataType().equals("Integer")) {
            Integer[] dataList = new Integer[dataSource.length];
            for(int i = 0;i< dataSource.length;i++){
                dataList[i] = Integer.parseInt(dataSource[i]);
            }
            sort = new QuickSort(dataList,steps,data.getModel());
        }

        sort.sort();

        //利用jackson转化成json
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(steps);
        System.out.println(json);
        return json;
    }
}
