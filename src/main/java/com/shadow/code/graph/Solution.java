package com.shadow.code.graph;

import java.util.*;

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
    
    private void dfs(int[][] graph, int x, int n, Deque<Integer> stack, List<List<Integer>> resultList) {
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

    /**
     * 中等题： 网络延迟，这个是使用 Dijkstra 算法来计算，重点是记住这个算法的特点
     *
     * @param times
     * @param n
     * @param k
     * @return
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2;
        // 邻接矩阵存储边信息
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], INF);
        }

        for (int[] time : times) {
            // 边序号从0开始
            int x = time[0] - 1, y = time[1] - 1;
            g[x][y] = time[2];
        }

        // 从源点到某点的距离的数组
        int[] dest = new int[n];
        Arrays.fill(dest, INF);
        // 假设从k开始，也就是此节点为源点
        dest[k - 1] = 0;
        // 节点是否被更新的数组
        boolean[] used = new boolean[n];

        for (int i = 0; i < n; i++) {
            // 在还未确定最短路的点中，寻找距离最小的点
            int x = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j] && (x == -1 || dest[j] < dest[x])) {
                    x = j;
                }
            }

            // 记录该点已经被更新过了
            used[x] = true;
            for (int j = 0; j < n; j++) {
                dest[j] = Math.min(dest[j], dest[x] + g[x][j]);
            }
        }

        // 找到该点的最远距离
        int ans = Arrays.stream(dest).max().getAsInt();
        return ans == INF ? -1 : ans;
    }
}
