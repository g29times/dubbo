package com.example.demo.state;

import java.util.*;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * 关于缓存更新的思考<p>
 * <a href="https://coolshell.cn/articles/17416.html">google</a>
 * https://www.processon.com/view/link/5f8e99111e085307a0979d69
 * http://dwz.date/cSZF
 *
 * @author li tong
 * @description:
 * @date 2020/10/20 14:52
 * @see Object
 * @since 1.0
 */
public class PermuTest {

    public static void main(String[] args) {
        String[] arr = {"DEL", "UPDATE", "读C", "读D", "S"}; // {0, 1, 2, 3, 4};
        Set<String> set = resolve(new HashSet<>(), arr, 0);
        System.out.println(set.size());
        System.out.println(set);
    }

    private static boolean checkOrder(String[] arr) {
        List<String> temp = new LinkedList<>(Arrays.asList(arr));
        // cache, DB, sync
        String a = "读C", b = "读D", c = "S";
        return temp.indexOf(a) <= temp.indexOf(b) &&
                temp.indexOf(a) <= temp.indexOf(c) &&
                temp.indexOf(b) <= temp.indexOf(c);
    }

    /**
     * 递归函数
     *
     * @param set
     * @param arr
     * @param n
     * @return
     */
    private static Set<String> resolve(Set<String> set, String[] arr, int n) {
        if (n == 5) { // 当尝试对不存在的数组元素进行递归时，标明所有数已经排列完成，输出。
            for (int i = 0; i < 5; i++) {
                if (!set.contains(Arrays.toString(arr)) && checkOrder(arr)) {
                    set.add(Arrays.toString(arr));
                }
            }
            return set;
        }
        // 循环实现交换和之后的全排列
        for (int i = n; i < 5; i++) {
            // i是从n开始 i=n; swap(n,i)相当于固定当前位置，在进行下一位的排列。
            swap(n, i, arr);
            resolve(set, arr, n + 1);
            swap(n, i, arr);
        }
        return set;
    }

    /**
     * 用于交换数组的两个数
     *
     * @param x
     * @param y
     * @param arr
     */
    private static void swap(int x, int y, String[] arr) {
        String temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

}
