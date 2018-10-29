package cn.cjf.algorithm;

import java.util.Arrays;

/**
 * 希尔排序，又称缩小增量排序，是简单插入排序改进后的版本，
 * 希尔排序，按数组下表的一定增量分组，每组使用插入排序算法排序，随着增量逐渐减少，每组的元素越来越多
 * 当增量减至1时，恰好整个数组被分为一组，至此算法终止
 * @author cjf
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 7, 9, 8, 3, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 希尔排序 针对有序序列在插入时采用交换法
     */
    public static void sort1(int[] arr) {
        //增量gap，并逐步缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            InsertSort.sort1(arr, gap, gap);
        }
    }

    /**
     * 希尔排序 针对有序序列在插入时采用移动法。
     */
    public static void sort(int[] arr) {
        //增量gap，并逐步缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            InsertSort.sort(arr, gap, gap);
        }
    }
}