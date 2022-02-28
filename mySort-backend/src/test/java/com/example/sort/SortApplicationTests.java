package com.example.sort;

import com.example.sort.core.BubbleSort;
import com.example.sort.core.Sort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Vector;

@SpringBootTest
class SortApplicationTests {

    @Test
    void contextLoads() {
//        Integer[] data = {2,45,9,3,5,6};
        String[] data = {"hhd","hh","bob","alice"};
        String[] data1 = {"3","1","89","45","2","15"};

        Vector<String> steps = new Vector<>();

        Sort bubbleSort = new BubbleSort(data1,steps,1);
        bubbleSort.sort();
        bubbleSort.showSteps();

//        Sort insertSort = new InsertSort(data,steps,1);
//        insertSort.sort();
//        insertSort.showSteps();

//        Sort quickSort = new QuickSort(data,steps,2);
//        quickSort.sort();
//        quickSort.showSteps();

    }

}
