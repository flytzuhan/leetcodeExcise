package com.shadow.code.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
