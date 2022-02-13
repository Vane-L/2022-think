package week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wenhongliang
 */
public class Week3 {
    public static void main(String[] args) {
        System.out.println(new Week3().countOperations(2, 3));
        System.out.println(new Week3().minimumOperations(new int[]{1, 2, 2, 2, 2}));
        System.out.println(new Week3().minimumRemoval(new int[]{2, 10, 3, 2}));
        System.out.println(new Week3().maximumANDSum(new int[]{1, 2, 3, 4, 5, 6}, 3));
    }

    /**
     * Easy 6004. 得到 0 的操作数
     * 给你两个 非负 整数 num1 和 num2 。
     * 每一步 操作 中，如果 num1 >= num2 ，你必须用 num1 减 num2 ；否则，你必须用 num2 减 num1 。
     * 例如，num1 = 5 且 num2 = 4 ，应该用 num1 减 num2 ，因此，得到 num1 = 1 和 num2 = 4 。
     * 然而，如果 num1 = 4且 num2 = 5 ，一步操作后，得到 num1 = 4 和 num2 = 1 。
     * 返回使 num1 = 0 或 num2 = 0 的 操作数 。
     */
    public int countOperations(int num1, int num2) {
        int count = 0;
        while (num1 != 0 && num2 != 0) {
            if (num1 >= num2) {
                num1 -= num2;
            } else {
                num2 -= num1;
            }
            count++;
        }
        return count;
    }

    /**
     * Medium 6005. 使数组变成交替数组的最少操作数
     * 给你一个下标从 0 开始的数组 nums ，该数组由 n 个正整数组成。
     * 如果满足下述条件，则数组 nums 是一个 交替数组 ：
     * nums[i - 2] == nums[i] ，其中 2 <= i <= n - 1 。
     * nums[i - 1] != nums[i] ，其中 1 <= i <= n - 1 。
     * 在一步 操作 中，你可以选择下标 i 并将 nums[i] 更改 为 任一 正整数。
     * 返回使数组变成交替数组的 最少操作数 。
     * !!! 统计出现次数，再排序
     * ps: 参考 arignote
     */
    public int minimumOperations(int[] nums) {
        HashMap<Integer, Integer> m1 = new HashMap<>();
        m1.put(0, 0);
        HashMap<Integer, Integer> m2 = new HashMap<>();
        m2.put(0, 0);
        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 1) {
                m1.put(nums[i], m1.getOrDefault(nums[i], 0) + 1);
            } else {
                m2.put(nums[i], m2.getOrDefault(nums[i], 0) + 1);
            }
        }
        ArrayList<Map.Entry<Integer, Integer>> l1 = new ArrayList<>(m1.entrySet());
        ArrayList<Map.Entry<Integer, Integer>> l2 = new ArrayList<>(m2.entrySet());
        l1.sort((o, p) -> p.getValue() - o.getValue());
        l2.sort((o, p) -> p.getValue() - o.getValue());
        return nums.length - Math.max(l1.get(0).getValue() + l2.get(1).getValue(),
                l2.get(0).getValue() + l1.get(l1.get(0).getKey().equals(l2.get(0).getKey()) ? 1 : 0).getValue());
    }

    /**
     * Medium 6006. 拿出最少数目的魔法豆
     * 给你一个 正 整数数组 beans ，其中每个整数表示一个袋子里装的魔法豆的数目。
     * 请你从每个袋子中 拿出 一些豆子（也可以 不拿出），使得剩下的 非空 袋子中（即 至少 还有 一颗 魔法豆的袋子）魔法豆的数目 相等 。
     * 一旦魔法豆从袋子中取出，你不能将它放到任何其他的袋子中。
     * 请你返回你需要拿出魔法豆的 最少数目。
     * 输入：beans = [4,1,6,5]
     * 输出：4
     * 解释：
     * - 我们从有 1 个魔法豆的袋子中拿出 1 颗魔法豆。剩下袋子中魔法豆的数目为：[4,0,6,5]
     * - 然后我们从有 6 个魔法豆的袋子中拿出 2 个魔法豆。剩下袋子中魔法豆的数目为：[4,0,4,5]
     * - 然后我们从有 5 个魔法豆的袋子中拿出 1 个魔法豆。剩下袋子中魔法豆的数目为：[4,0,4,4]
     * 总共拿出了 1 + 2 + 1 = 4 个魔法豆，剩下非空袋子中魔法豆的数目相等。
     * 没有比取出 4 个魔法豆更少的方案。
     * !!! 计算总和
     * ps: 参考 arignote
     */
    public long minimumRemoval(int[] beans) {
        Arrays.sort(beans);
        long sum = 0;
        long min = Long.MAX_VALUE;
        for (int bean : beans) {
            sum += bean;
        }
        for (int i = 0; i < beans.length; i++) {
            min = Math.min(min, sum - (long) beans[i] * (beans.length - i));
        }
        return min;
    }

    /**
     * 给你一个长度为 n 的整数数组 nums 和一个整数 numSlots ，满足2 * numSlots >= n 。总共有 numSlots 个篮子，编号为 1 到 numSlots 。
     * 你需要把所有 n 个整数分到这些篮子中，且每个篮子 至多 有 2 个整数。一种分配方案的 与和 定义为每个数与它所在篮子编号的 按位与运算 结果之和。
     * 比方说，将数字 [1, 3] 放入篮子 1 中，[4, 6] 放入篮子 2 中，这个方案的与和为 (1 AND 1) + (3 AND 1) + (4 AND 2) + (6 AND 2) = 1 + 1 + 0 + 2 = 4 。
     * 请你返回将 nums 中所有数放入 numSlots 个篮子中的最大与和。
     * ps: 参考 arignote
     */
    public int maximumANDSum(int[] nums, int numSlots) {
        return maximumANDSum(0, 0, nums, numSlots, new Integer[1 << 2 * numSlots]);
    }

    private int maximumANDSum(int i, int j, int[] nums, int numSlots, Integer[] dp) {
        if (dp[j] == null) {
            dp[j] = 0;
            if (i < nums.length) {
                for (int k = 0; k < numSlots; k++) {
                    if ((j & 1 << 2 * k + 1) == 0) {
                        dp[j] = Math.max(dp[j],
                                (nums[i] & (k + 1)) + maximumANDSum(i + 1, j + (1 << 2 * k), nums, numSlots, dp));
                    }
                }
            }
        }
        return dp[j];
    }
}
