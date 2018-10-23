package cn.cjf.algorithm;

public final class Util {
    /**
     * 交换
     *
     * @param arr  要交换数据的数组
     * @param src  交换位置的索引1
     * @param desc 交换位置的索引2
     */
    public static void swap(int[] arr, int src, int desc) {
        int temp = arr[src];
        arr[src] = arr[desc];
        arr[desc] = temp;
    }
}
