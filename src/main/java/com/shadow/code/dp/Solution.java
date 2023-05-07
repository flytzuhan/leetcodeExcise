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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 先从前序遍历中获取到根节点，然后在中序遍历中找到左子树的节点数据，构造左子树，剩下的就是右子树的节点
        return build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public TreeNode build(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        // 先找到根节点
        int rootVal = preorder[preStart];
        // 在中序遍历中找到根节点所在的索引位置
        int index = 0;
        for(int i = inStart; i < inEnd; i++) {
            if (inorder[i] == rootVal) {
                index = i;
                break;
            }
        }
        // 左子树的节点数量
        int leftSize = index - inStart;
        TreeNode root = new TreeNode(rootVal);
        root.left = build(preorder, preStart + 1, preStart + leftSize, inorder, inStart + 1, index - 1);
        root.right = build(preorder, preStart + leftSize + 1, preEnd, inorder, index + 1, inEnd);
        return root;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public boolean hasCycle(ListNode head) {
        // 使用快慢指针来操作，如果相遇的话，说明有环，没有的话，说明没有环
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                // 说明相遇了
                return true;
            }
        }

        return false;
    }

    public int countPrimes(int n) {
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrimes(n)) {
                count++;
            }
        }

        return count;
    }

    private boolean isPrimes(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 先使用两个指针指向这两个节点
        ListNode p1 = l1, p2 = l2;
        ListNode result = new ListNode(-1);
        ListNode p = result;
        // 定义进位情况
        int carry = 0;
        while (p1 != null || p2 != null || carry > 0) {
            // 先加上进位的情况
            int val = carry;
            if (p1 != null) {
                val += p1.val;
                p1 = p1.next;
            }
            if (p2 != null) {
                val += p2.val;
                p2 = p2.next;
            }
            // 先计算进位
            carry = val / 10;
            val = val % 10;
            // 构造出下一个节点
            p.next = new ListNode(val);
            p = p.next;
        }
        return result.next;
    }

    /**
     * leetcode 206 翻转链表，使用递归的方式翻转
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        // 使用递归的方式翻转链表
        if (head == null || head.next == null) {
            return head;
        }

        ListNode last = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return last;
    }

    /**
     * 反转链表中的前n个节点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode successorNode;
    public ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            successorNode = head.next;
            return head;
        }

        ListNode last = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = successorNode;

        return last;
    }

    /**
     * leetcode 92 反转链表中的指定部分
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 加入left如果是1的话，那就相当于反转前right个节点
        if (left == 1) {
            return reverseN(head, right);
        }

        head.next = reverseBetween(head.next, left - 1, right - 1);

        return head;
    }

    /**
     * leetcode 445 两数相加II，链表的形式，所以可以使用栈的结构进行相加
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
        Stack<Integer> num1 = new Stack<>();
        while(l1 != null) {
            num1.push(l1.val);
            l1 = l1.next;
        }
        Stack<Integer> num2 = new Stack<>();
        while (l2 != null) {
            num2.push(l2.val);
            l2 = l2.next;
        }
        // 声明返回结果的链表
        ListNode result = new ListNode(-1);
        // 声明进位的标志
        int carry = 0;
        while (!num1.isEmpty() || !num2.isEmpty() || carry > 0) {
            // 先处理进位
            int val = carry;
            if (!num1.isEmpty()) {
                val += num1.pop();
            }
            if (!num2.isEmpty()) {
                val += num2.pop();
            }
            carry = val / 10;
            val = val % 10;
            ListNode newNode = new ListNode(val);
            // 使用头插法添加链表数据
            newNode.next = result.next;
            result.next = newNode;
        }
        return result.next;
    }
}
