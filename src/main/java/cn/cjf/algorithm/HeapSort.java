package cn.cjf.algorithm;

import java.util.Arrays;

/**
 * 堆排序就是构建大顶堆的过程，
 * 从最后一个非叶节点开始调整，构建大顶堆,然后首尾节点交换，排除尾节点，剩余节点重复交换，构建，直到整个有序
 */
public class HeapSort {
    public static void sort(int[] arr) {
        //1.构建大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, arr.length);
        }
        //2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = arr.length - 1; j > 0; j--) {
            //将堆顶元素与末尾元素进行交换
            Util.swap(arr, 0, j);
            //重新对堆进行调整
            adjustHeap(arr, 0, j);
        }

    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     *
     * @param arr
     * @param i
     * @param length
     */
    private static void adjustHeap(int[] arr, int i, int length) {
        //先取出当前元素i
        int temp = arr[i];
        //根节点 I 根左节点 2i+1 , 根右节点2i+2
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            //从i结点的左子结点开始，也就是2i+1处开始
            //如果有右节点，判断左右节点谁大
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                //如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            //子节点根父节点比较,如果子节点大于父节点，交换
            //优化：子节点赋值给父节点,设置最后一次交换节点
            if (arr[k] > temp) {
                //如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        //根节点与最后一次交换节点交换
        //将temp值放到最终的位置
        arr[i] = temp;

    }
}