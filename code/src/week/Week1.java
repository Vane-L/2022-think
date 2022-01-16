package week;

/**
 * @Author: wenhongliang
 */
public class Week1 {
    public static void main(String[] args) {
        System.out.println(new Week1().minMoves(10, 4));
        System.out.println(new Week1().minMoves(766972377, 92));

        System.out.println(new Week1().mostPoints(new int[][]{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}}));
        System.out.println(new Week1().mostPoints(new int[][]{{21, 5}, {92, 3}, {74, 2}, {39, 4}, {58, 2}, {5, 5}, {49, 4}, {65, 3}}));
    }

    /**
     * https://leetcode-cn.com/problems/divide-a-string-into-groups-of-size-k/
     * 5980. 先拼接再切分
     */
    public String[] divideString(String s, int k, char fill) {
        int len = s.length();
        int count = len / k + (len % k == 0 ? 0 : 1);
        String[] res = new String[count];
        for (int i = 0; i < count * k - len; i++) {
            s += fill;
        }
        for (int i = 0; i < count; i++) {
            res[i] = s.substring(i * k, (i + 1) * k);
        }
        return res;
    }


    /**
     * https://leetcode-cn.com/problems/minimum-moves-to-reach-target-score/
     * 5194. 从target倒推，偶数翻倍，奇数减一，如果maxDoubles等于0直接返回结果
     */
    public int minMoves(int target, int maxDoubles) {
        if (maxDoubles == 0) return target - 1;
        int count = 0;
        for (int i = target; i > 1; ) {
            if (i % 2 == 0 && maxDoubles > 0) {
                maxDoubles--;
                i /= 2;
            } else {
                i--;
            }
            count++;
            if (maxDoubles == 0) {
                return count + i - 1;
            }
        }
        return count;
    }

    /**
     * https://leetcode-cn.com/problems/solving-questions-with-brainpower/
     * 5982. 动态规划计算跳过与不跳过的最大分数
     */
    public long mostPoints(int[][] questions) {
        int len = questions.length;
        long[] dp = new long[len + 1];
        for (int i = len - 1; i >= 0; i--) {
            int points = questions[i][0];
            int brainpower = questions[i][1];
            int j = i + brainpower + 1;
            dp[i] = Math.max(dp[i + 1], points + (j < len ? dp[j] : 0));
        }
        return dp[0];
    }


    /**
     * https://leetcode-cn.com/problems/maximum-running-time-of-n-computers/
     * 5983. 二分查找,可以让 n 台电脑同时运行的 最长 分钟数
     */
    public long maxRunTime(int n, int[] batteries) {
        long left = 0, right = 100000000000000L;
        while (left < right) {
            long mid = (left + right + 1) / 2;
            long sum = 0;
            for (int battery : batteries) {
                sum += Math.min(mid, battery);
            }
            if (sum / n < mid) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        return left;
    }
}
