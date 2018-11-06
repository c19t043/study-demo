package cn.cjf.algorithm;

/**
 * 有两种思路，1、一种是从左向右排，每次遍历将最大的数放到最右边，下次遍历时，不用管用最右边的已排好的数
 * 2、一种是从右向左排，每次便利将最小的值放到最左边，下次遍历时，不用管最左边的已排好的数
 *
 * @author CJF
 */
public class BubbleSort {
    public static void sort(int[] arr) {
        sortFromRight(arr);
    }

    private static void sortFromLeft(int[] arr) {
        for (int i = 0; i <= arr.length - 1; i++) {
            //从左开始比较
            for (int j = 0; j <= arr.length - 2 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    Util.swap(arr, j, j + 1);
                }
            }
        }
    }

    private static void sortFromRight(int[] arr) {
        for (int i = 0; i <= arr.length - 1; i++) {
            //从右边开始排
            for (int j = arr.length - 2; j >= i; j--) {
                if (arr[j] > arr[j + 1]) {
                    Util.swap(arr, j, j + 1);
                }
            }
        }
    }
}
