package cn.cjf.algorithm;

public class TestSort {
    public static void main(String[] args) {
        int scores[] = {67, 69, 44, 75, 88, 33};
        for (int num : scores) {
            System.out.print(num + " ");
        }
        System.out.println();
        CountSort.sort(scores);
        for (int num : scores) {
            System.out.print(num + " ");
        }
    }
}
