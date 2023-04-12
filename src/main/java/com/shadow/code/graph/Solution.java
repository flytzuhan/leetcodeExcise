package com.shadow.code.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    /**
     * 简单题，找出星型图的中心节点,中心节点在每个数组列表中每个小数组中都会出现，所以根据题意，那它肯定是是前两个数组中的公共元素
     */
    public int findCenter(int[][] edges) {
        return edges[0][0] == edges[1][0] || edges[0][0] == edges[1][1] ? edges[0][0] : edges[0][1];
    }

    /**
     * 中等题，找出可以到达所有点的最少点数目，根据题意，可以先找到入度不为0的元素，入度不为0，表示肯定有一条线可以到达当前这个节点
     * 因此遍历edges的集合，获取到to节点的值，放入到集合中，接着遍历所有的n节点，找出入度为0的节点，这样就是要返回的值
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        // 循环记录入度不为0的数字，然后再循环遍历n个序号，找不不在集合中的数字
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        // 开始循环
        for (List<Integer> edge : edges) {
            set.add(edge.get(1));
        }

        // 遍历序号
        for (int i = 0; i < n; i++) {
            if (!set.contains(i)) {
                list.add(i);
            }
        }
        return list;
    }
}
