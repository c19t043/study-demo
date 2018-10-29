package cn.cjf.algorithm;

public final class Util {
    /**
     * 交换
     *
     * @param arr 要交换数据的数组
     * @param a   交换位置的索引1
     * @param b   交换位置的索引2
     */
    public static void swap(int[] arr, int a, int b) {
        arr[a] = arr[a] + arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }

    /**
     * 交换数组元素
     *
     * @param arr 要交换数据的数组
     * @param a   交换位置的索引1
     * @param b   交换位置的索引2
     */
    public static <T extends Comparable<? super T>> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
