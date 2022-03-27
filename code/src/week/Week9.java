package week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: wenhongliang
 */
public class Week9 {
    public static void main(String[] args) {
        Week9 w = new Week9();
        System.out.println(w.minDeletion0(new int[]{1, 1, 2, 3, 5}));
        System.out.println(w.minDeletion0(new int[]{1, 1, 2, 2, 3, 3}));
        //System.out.println(w.kthPalindrome0(new int[]{1, 2, 3, 90}, 3));
        System.out.println(w.kthPalindrome0(new int[]{2, 4, 6, 10, 19}, 4));
        List<List<Integer>> list1 = new ArrayList<>();
        list1.add(Arrays.asList(1, 100, 3));
        list1.add(Arrays.asList(7, 8, 9));
        System.out.println(w.maxValueOfCoins(list1, 2));

        List<List<Integer>> list2 = new ArrayList<>();
        list2.add(Arrays.asList(100));
        list2.add(Arrays.asList(100));
        list2.add(Arrays.asList(100));
        list2.add(Arrays.asList(100));
        list2.add(Arrays.asList(100));
        list2.add(Arrays.asList(100));
        list2.add(Arrays.asList(1, 1, 1, 1, 1, 1, 700));
        System.out.println(w.maxValueOfCoins(list2, 7));
    }

    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        List<List<Integer>> res = new ArrayList<>();
        Set<Integer> set1 = new HashSet<>();
        List<Integer> list1 = new ArrayList<>();
        Set<Integer> set2 = new HashSet<>();
        List<Integer> list2 = new ArrayList<>();
        for (int x : nums1) {
            set1.add(x);
        }
        for (int y : nums2) {
            set2.add(y);
            if (!set1.contains(y)) {
                list1.add(y);
            }
        }
        for (int x : nums1) {
            if (!set2.contains(x)) {
                list2.add(x);
            }
        }
        res.add(list2.stream().distinct().collect(Collectors.toList()));
        res.add(list1.stream().distinct().collect(Collectors.toList()));
        return res;
    }

    public int minDeletion(int[] nums) {
        int res = 0;
        int slow = 0, fast = 1;
        while (fast < nums.length) {
            if (slow % 2 == 0 && nums[slow] == nums[fast]) {
                res++;
            } else {
                slow++;
            }
            fast++;
        }
        return res;
    }

    public int minDeletion0(int[] nums) {
        int count = 0;
        for (int i = 1; i < nums.length; i += 2) {
            if (nums[i] == nums[i - 1]) {
                count++;
                i--;
            }
        }
        return count + (nums.length - count) % 2;
    }

    public int minDeletion1(int[] nums) {
        int ans = 0;
        for (int i = 0; i + 1 < nums.length; i++) {
            if (nums[i] == nums[i + 1]) ans++;
            else i++;
        }
        if ((nums.length - ans) % 2 == 1) ans++;
        return ans;
    }

    public long[] kthPalindrome(int[] queries, int intLength) {
        long[] res = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = getPalindrome(intLength, queries[i]);
        }
        return res;
    }

    private long getPalindrome(int len, int idx) {
        long res = 1;
        for (int i = 1; i < len / 2; i++) {
            res = res * 10 * 10;
        }
        return res;
    }

    public long[] kthPalindrome0(int[] queries, int intLength) {
        long[] result = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String s = String.valueOf((long) Math.pow(10, (intLength - 1) / 2) + queries[i] - 1);
            // 生成回文数
            result[i] = s.length() > (intLength + 1) / 2 ? -1 : Long.valueOf(s + new StringBuilder(s.substring(0, s.length() - intLength % 2)).reverse());
        }
        return result;
    }

    // 背包问题
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int[][] sum = new int[piles.size()][];
        Integer[][] dp = new Integer[piles.size()][k + 1];
        for (int i = 0; i < piles.size(); i++) {
            List<Integer> pile = piles.get(i);
            sum[i] = new int[pile.size() + 1];
            for (int j = 1; j <= pile.size(); j++) {
                sum[i][j] = sum[i][j - 1] + pile.get(j - 1);
            }
        }
        return dfs(0, k, sum, dp);
    }

    private int dfs(int index, int k, int[][] sum, Integer[][] dp) {
        if (index == sum.length) {
            return 0;
        } else if (dp[index][k] == null) {
            dp[index][k] = 0;
            for (int i = 0; i <= k && i < sum[index].length; i++) {
                dp[index][k] = Math.max(dp[index][k], sum[index][i] + dfs(index + 1, k - i, sum, dp));
            }
        }
        return dp[index][k];
    }

    public int maxValueOfCoins0(List<List<Integer>> piles, int k) {
        int[] dp = new int[k + 1];
        for (int i = 0; i < piles.size(); i++) {
            int sum = 0;
            int[] tmp = new int[k + 1];
            for (int j = 0; j < piles.get(i).size(); j++) {
                sum += piles.get(i).get(j);
                for (int s = j + 1; s <= k; s++) {
                    tmp[s] = Math.max(Math.max(dp[s], dp[s - j - 1] + sum), tmp[s]);
                }
            }
            dp = tmp;
        }
        return dp[k];
    }
}
