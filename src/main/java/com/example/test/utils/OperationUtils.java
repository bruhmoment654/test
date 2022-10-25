package com.example.test.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OperationUtils {
    private Long a, b;

    private List<Long> swap(List<Long> list, int i, int j) {
        Long temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
        return list;
    }

    private int Partition(List<Long> list, int l, int r) {
        Long lst = list.get(r);
        int i = l, j = l;
        while (j < r) {
            if (list.get(j) < lst) {
                list = swap(list, i, j);
                i++;
            }
            j++;
        }
        swap(list, i, r);
        return i;
    }

    private int randomPartition(List<Long> list, int l, int r) {
        int n = r - l + 1;
        int pivot = (int) (Math.random() % n);
        list = swap(list, l + pivot, r);
        return Partition(list, l, r);
    }

    private int MedianUtil(List<Long> list, int l, int r, int k) {

        // if l < r
        if (l <= r) {

            int partitionIndex = randomPartition(list, l, r);
            if (partitionIndex == k) {
                b = list.get(partitionIndex);
                if (a != -1)
                    return Integer.MIN_VALUE;
            } else if (partitionIndex == k - 1) {
                a = list.get(partitionIndex);
                if (b != -1)
                    return Integer.MIN_VALUE;
            }
            if (partitionIndex >= k)
                return MedianUtil(list, l, partitionIndex - 1, k);
            else
                return MedianUtil(list, partitionIndex + 1, r, k);
        }

        return Integer.MIN_VALUE;
    }

    public Long findMedian(List<Long> list, int n) {
        Long ans;
        a = -1L;
        b = -1L;
        if (n % 2 == 1) {
            MedianUtil(list, 0, n - 1, n / 2);
            ans = b;
        } else {
            MedianUtil(list, 0, n - 1, n / 2);
            ans = (a + b) / 2;
        }
        return ans;
    }

    public List<List<Long>> getIncSubarrays(List<Long> list) {
        List<Long> temp = new ArrayList<>();
        List<List<Long>> subArrays = new ArrayList<>();

        int n = list.size();
        int max = lenOfLongIncSubArr(list, n);
        int len = 1;

        for (int i = 1; i < n; i++) {
            if (list.get(i) > list.get(i - 1)) {
                temp.add(list.get(i - 1));
                if (i == n - 1) {
                    temp.add(list.get(i));
                    len++;
                    if (len == max) {
                        subArrays.add(temp);
                    }
                }
                len++;
            } else {
                temp.add(list.get(i - 1));
                if (max == len)
                    subArrays.add(List.copyOf(temp));
                len = 1;
                temp = new ArrayList<>();
            }
        }

        return subArrays;
    }

    public int lenOfLongIncSubArr(List<Long> list, int n) {
        int max = 1, len = 1;

        for (int i = 1; i < n; i++) {

            if (list.get(i) > list.get(i - 1))
                len++;
            else {
                if (max < len)
                    max = len;
                len = 1;
            }
        }

        if (max < len)
            max = len;

        return max;
    }

    public List<List<Long>> getDecSubarrays(List<Long> list) {
        List<Long> temp = new ArrayList<>();
        List<List<Long>> subArrays = new ArrayList<>();

        int n = list.size();
        int max = lenOfLongDecSubArr(list, n);
        int len = 1;

        for (int i = 1; i < n; i++) {
            if (list.get(i) < list.get(i - 1)) {
                temp.add(list.get(i - 1));
                if (i == n - 1) {
                    temp.add(list.get(i));
                    len++;
                    if (len == max) {
                        subArrays.add(temp);
                    }
                }
                len++;
            } else {
                temp.add(list.get(i - 1));
                if (max == len)
                    subArrays.add(List.copyOf(temp));
                len = 1;
                temp = new ArrayList<>();
            }
        }

        return subArrays;
    }

    public int lenOfLongDecSubArr(List<Long> list, int n) {
        int max = 1, len = 1;

        for (int i = 1; i < n; i++) {

            if (list.get(i) < list.get(i - 1))
                len++;
            else {
                if (max < len)
                    max = len;
                len = 1;
            }
        }

        if (max < len)
            max = len;

        return max;
    }


}
