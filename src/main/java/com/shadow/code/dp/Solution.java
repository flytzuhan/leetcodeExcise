package com.shadow.code.dp;

import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class Solution {

    /**
     * 2023年04月11日 今日刷题记录：动态规划，题目类型中，两道中等，1道简单
     */

    /**
     * 题目含义：给一个正整数，返回这个正整数的二进制中1的数量
     *
     * @param n
     * @return
     */
    public int[] countBits(int n) {
        // 使用奇偶数来判断，奇数的话，会比前一个数多一个1，偶数的话，跟它的一半是一样的
        int[] result = new int[n];
        result[0] = 0;
        for (int i = 0; i <= n; i++) {
            if (i % 2 == 1) {
                result[i] = result[i - 1] + 1;
            } else {
                result[i] = result[i / 2];
            }
        }
        return result;
    }

    /**
     * 生成匹配的括号，这个可以说使用深度优先的算法，比较好理解
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<String>();

        dfs("", n, n, list);

        return list;
    }

    private void dfs(String cur, int left, int right, List<String> list) {
        // 先处理完全匹配的情况
        if (left == 0 && right == 0) {
            list.add(cur);
            return;
        }

        // 剪枝
        if (left > right) {
            // 说明是先写入的是右边的括号，这样就不符合题意了，所以可以省略掉
            return;
        }

        if (left > 0) {
            dfs(cur + "(", left - 1, right, list);
        }

        if (right > 0) {
            dfs(cur + ")", left, right - 1, list);
        }
    }

    /**
     * 统计元音字符串的数目，使用动态规划计算
     *
     * @param n
     * @return
     */
    public int countVowelStrings(int n) {
        int[] dp = new int[5];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < 5; j++) {
                dp[j] += dp[j - 1];
            }
        }

        return Arrays.stream(dp).sum();
    }
    
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    Map<Integer, List<TreeNode>> map = new HashMap<>();

    /**
     * 题意：给一个正整数，返回所有可能的满二叉树，满二叉树，即每个节点要么有0个子节点，要么有2个子节点
     * @param n
     * @return
     */
    public List<TreeNode> allPossibleFBT(int n) {
        if (!map.containsKey(n)) {
            List<TreeNode> list = new ArrayList<>();
            if (n == 1) {
                list.add(new TreeNode(0));
            } else if (n % 2 == 1) {
                // 表示一个奇数
                for (int i = 0; i < n; i++) {
                    // 计算右子树的长度
                    int j = n - i - 1;
                    for (TreeNode left : allPossibleFBT(i)) {
                        for (TreeNode right : allPossibleFBT(j)) {
                            TreeNode node = new TreeNode(0);
                            node.left = left;
                            node.right = right;
                            list.add(node);
                        }
                    }
                }
            }

            map.put(n, list);
        }

        return map.get(n);
    }

    /**
     * 简单题：给一个由整数组成的非空数组，给这个数+1，返回结果，根据题意：其他的情况都比较好处理，只有一个是如果全部是9的情况
     * 这种就会多出来一位，即最高位为1，其他位全部是0
     *
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        // 先循环遍历所有的数字，判断是否为9，如果为9的话，就直接给加上，如果不是的话，
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] != 9) {
                // 这样就会在其他的位数不是9的情况下，直接返回了
                digits[i]++;
                return digits;
            } else {
                // 如果所有的位数都是9的情况下，+1的话，就会使得这些位数都为0
                digits[i] = 0;
            }
        }
        // 这样就剩下最后一种情况了，所有的位数都是9，+1之后，最高位是1，其余都是0，所以特殊处理这种情况
        int[] result = new int[n + 1];
        result[0] = 1;
        return result;
    }

    @Test
    public void testPartitionLabels() {
        Assert.assertEquals("[9, 7, 8]", partitionLabels("ababcbacadefegdehijhklij").toString());
    }

    /**
     * 中等题：划分字母区间，给你一个字符串s，需要将这个字符串划分成尽可能多的片段，同一个字母最多出现在一个片段中，
     * 根据题意：需要知道每个字符最后出现的位置，可以使用字典进行记录，接着循环这个字符串，根据每个字符的最后出现位置，判断当前循环的字符，
     * 是否在最大的索引位置内部，如果在的话，以最大的索引位置为准，如果不在，那就是要将最大的索引位置替换成当前的这个字符最后出现的位置上
     *
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        List<Integer> list = new ArrayList<>();
        // 首先要知道每个字符出现的最后位置
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            map.put(s.charAt(i), i);
        }

        // 设置开始和结束的索引位置
        int start = 0, end = 0;
        for (int i = 0; i < n; i++) {
            end = Math.max(end, map.get(s.charAt(i)));
            if (i == end) {
                list.add(end - start + 1);
                start = end + 1;
            }
        }

        return list;
    }
}
