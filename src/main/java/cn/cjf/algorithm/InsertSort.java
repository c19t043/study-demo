package cn.cjf.algorithm;


/**
 * 插值排序，每次一次排序，都保障上一趟排序比较过的元素是有序的
 * 有两种基本操作：比较，交换
 * 交换操作可以优化成移动交换，即不直接进行两个元素的交换，
 * 每趟排序，先将枢轴元素（要插入的值）保存起来,然后执行移动操作，待确定最终位置，再执行交换操作
 *
 * @author CJF
 */
public class InsertSort {

    public static <T extends Comparable<? super T>> void sort1(T[] a) {
        for (int p = 1; p < a.length; p++) {
            //保存当前位置p的元素，其中[0,p-1]已经有序
            T tmp = a[p];
            int j;
            for (j = p; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {
                //后移一位
                a[j] = a[j - 1];
            }
            //插入到合适的位置
            a[j] = tmp;
        }
    }

    public static void sort1(int[] arr, int initValue, int incrBy) {
        for (int i = initValue; i < arr.length; i++) {
            for (int j = i; j - incrBy >= 0 && arr[j - incrBy] > arr[j]; j -= incrBy) {
                Util.swap(arr, j, j - incrBy);
            }
        }
    }

    public static void sort(int[] arr, int initValue, int incrBy) {
        for (int i = initValue; i < arr.length; i++) {
            int temp = arr[i];
            int j;
            for (j = i; j - incrBy >= 0 && arr[j - incrBy] > temp; j -= incrBy) {
                arr[j] = arr[j - incrBy];
            }
            arr[j] = temp;
        }
    }

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        for (int p = 1; p < a.length; p++) {
            //保存当前位置p的元素，其中[0,p-1]已经有序
            for (int j = p; j >= 1 && a[j].compareTo(a[j - 1]) < 0; j--) {
                //后移一位
                Util.swap(a, j, j - 1);
            }
        }
    }
}