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
     * 
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
    
    /**
     * 中等题：打印所有可能的路径，根据题意：一共有n个节点，和一个图的数组，记录此节点可以到达的其他节点，目标是打印所有从0到n-1节点的路径并打印
     * 这种就非常适合使用深度优先算法
     * 
     * @param graph
     * @return
     */
    public List<List<Integer>> allPathSourceTarget(int[][] graph) {
        List<List<Integer>> resultList = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.offerLast(0);
        dfs(graph, 0, graph.length - 1, stack, resultList);
        return resultList;
    }
    
    private void dfsAllPathData(int[][] graph, int x, int n, Deque<Integer> stack, List<List<Integer>> resultList) {
        // 先处理终止条件
        if (n == x) {
            resultList.add(new ArrayList<>(stack));
            return;
        }

        // 循环获取每个元素
        for (int t : graph[x]) {
            stack.offerLast(t);
            dfs(graph, t, n, stack, resultList);
            stack.pollLast();
        }
    }
    
    public void rotate(int[] nums, int k) {
        // 做法：先翻转所有的数字，再翻转0，k-1，最后翻转k，n-1
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }
    
    public void reverse(int[] nums, int left, int right) {
        while(left < right) {
            int t = nums[left];
            nums[left++] = nums[right];
            nums[right--] = t;
        }
    }
}
