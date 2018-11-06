package cn.cjf.algorithm;

/**
 * 利用二分的思想，定一个基准数，使这个基准数左边的数都小于基准数，右边的都大于基准数
 * @author cjf
 */
public class QuickSort {

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        int i, j;
        if (left > right) {
            return;
        }

        //temp中存的就是基准数
        i = left;
        j = right;
        while (i != j) {
            //顺序很重要，要先从右边开始找
            while (arr[j] >= arr[left] && i < j) {
                j--;
            }
            //再找右边的
            while (arr[i] <= arr[left] && i < j) {
                i++;
            }
            //交换两个数在数组中的位置
            if (i < j) {
                Util.swap(arr, i, j);
            }
        }
        //最终将基准数归位
        Util.swap(arr, left, i);

        //继续处理左边的，这里是一个递归的过程
        quickSort(arr, left, i - 1);
        //继续处理右边的 ，这里是一个递归的过程
        quickSort(arr, i + 1, right);
    }
}
