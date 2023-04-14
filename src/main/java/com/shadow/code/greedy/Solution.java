package com.shadow.code.greedy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    /**
     * 简单题：2357，使数组中所有元素都等于0，根据题意，就是先从数组中找到最小的一个元素，大家都减去这个元素，得到每个元素的值之后，
     * 再找到最小的元素，接着减去这个元素，直到所有的元素都是0，统计一共需要操作多少次
     * 这种可以利用哈希集合，只要统计出不为0的数字的个数，当然可能会存在相同的元素，这个没关系的，只要统计所有的元素即可
     *
     * @param nums
     * @return
     */
    public int minimumOperations(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                set.add(nums[i]);
            }
        }
        return set.size();
    }

    /**
     * 中等题：2279，装满石头的背包的最大数量
     * @param capacity
     * @param rocks
     * @param additionalRocks
     * @return
     */
    public int maximumBags(int[] capacity, int[] rocks, int additionalRocks) {
        // 设置一个数组用来存储还需要放多少石头
        int[] diff = new int[capacity.length];
        for (int i = 0; i < capacity.length; i++) {
            diff[i] = capacity[i] - rocks[i];
        }

        // 数据初始化好了之后，先进行排序
        Arrays.sort(diff);
        for (int i = 0; i < diff.length; i++) {
            if (additionalRocks < diff[i]) {
                // 说明只能装满i个背包
                return i;
            }
            additionalRocks -= diff[i];
        }
        return capacity.length;
    }
}
