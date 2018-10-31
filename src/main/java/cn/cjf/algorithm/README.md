# 算法
## 选择排序SelectionSort
每一趟从待排序的数组中选出最小值，顺序的放在已排好序的序列最后面，直到所有记录排序完毕
+ 时间复杂度O(n^2)
+ [《Java中的经典算法之选择排序（SelectionSort）》](https://www.cnblogs.com/shen-hua/p/5424059.html)
## 冒泡排序(BubbleSort)
相邻元素前后交换，把最大的排到最后
+ 时间复杂度O(n^2)
+ [《冒泡排序的2种写法》](https://blog.csdn.net/shuaizai88/article/details/73250615)
## 插值排序(InsertSort)
每一趟排序前都保障，前一趟比较过的元素都是有序的
+ 时间复杂度O(N^2)
+ [《排序算法总结之插入排序》](https://www.cnblogs.com/hapjin/p/5517667.html)
## 快速排序(QuickSort)
利用二分的思想，定一个基准数，使这个基准数左边的数都小于基准数，右边的都大于基准数
+ 时间复杂度O(NlogN)
+ [《坐在马桶上看算法：快速排序》](http://developer.51cto.com/art/201403/430986.htm)
## 归并排序(MergeSort)
归并采用分而治之的策略，将一个大问题拆分多个小问题，最后整合多个小问题，合并一个结果
+ [《图解排序算法(四)之归并排序》](http://www.cnblogs.com/chengxiao/p/6194356.html)
+ 时间复杂度O(N^2)
## 希尔排序(ShellSort)
希尔排序，又称缩小增量排序，是简单插入排序改进后的版本。
希尔排序，按数组下表的一定增量分组，每组使用插入排序算法排序，随着增量逐渐减少，每组的元素越来越多
当增量减至1时，恰好整个数组被分为一组，至此算法终止
+ 时间复杂度O(NlogN)
+ [《图解排序算法(二)之希尔排序》](https://www.cnblogs.com/chengxiao/p/6104371.html)