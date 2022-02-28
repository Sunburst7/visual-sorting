package com.example.sort.core;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Vector;
//排序接口

@Component
public interface Sort{

    //抽象排序方法
    public abstract void sort();

    //打印具体排序步骤
    public abstract void showSteps();

    //获取具体步骤
    public abstract Vector<String> getSteps();

}
