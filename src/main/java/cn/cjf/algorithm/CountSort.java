package cn.cjf.algorithm;

/**
 * 计数排序：是一个非基于比较的排序，类似于哈希表中的直接定址法
 * 在给定的一组序列中，找出最大值和最小值，从而确定需要开辟多少辅助空间
 * 每一个数在辅助空间中都有唯一的下标，遍历序列，如果与下标相等的值，则辅助空间下标的值加1
 * 然后遍历辅助空间，就可以得到一组有序的值
 */
public class CountSort {
    public static void sort(int[] arr) {
        if (arr != null && arr.length > 1) {
            //找出数组中最大值和最小值
            int max = arr[0];
            int min = arr[0];
            for (int i = 0; i < arr.length; i++) {
                if (max < arr[i]) {
                    max = arr[i];
                }
                if (min > arr[i]) {
                    min = arr[i];
                }
            }
            //新建辅助数组
            int[] assistantArr = new int[max - min + 1];
            //遍历源数组，对应辅助数组下标+1
            for (int i = 0; i < arr.length; i++) {
                assistantArr[arr[i] - min]++;
            }
            //遍历数组数组
            int index = 0;
            for (int i = 0; i < assistantArr.length; i++) {
                while (assistantArr[i] > 0) {
                    arr[index] = i + min;
                    assistantArr[i]--;
                    index++;
                }
            }
        }
    }
}
