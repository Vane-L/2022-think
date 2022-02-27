package week;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Author: wenhongliang
 */
public class Week5 {
    public static void main(String[] args) {
        System.out.println(new Week5().minSteps("leetcode", "coats"));
        System.out.println(new Week5().minimumTime(new int[]{1, 2, 3}, 5));
        System.out.println(new Week5().minimumTime(new int[]{2}, 1));
        System.out.println(new Week5().minimumTime(new int[]{1}, 4));
        System.out.println(new Week5().minimumTime(new int[]{5, 10, 10}, 9));
        System.out.println(new Week5().minimumTime(new int[]{9, 3, 10, 5}, 2));
        System.out.println(new Week5().minimumFinishTime(new int[][]{{2, 3}, {3, 4}}, 5, 4));
    }

    public int minSteps(String s, String t) {
        int res = 0;
        int[] ch = new int[26];
        for (char c : s.toCharArray()) ch[c - 'a']++;
        for (char c : t.toCharArray()) ch[c - 'a']--;
        for (int i = 0; i < 26; i++) {
            if (ch[i] != 0) {
                res += Math.abs(ch[i]);
            }
        }
        return res;
    }

    public long minimumTime(int[] time, int totalTrips) {
        long cost = 0L;
        int count = 0;
        while (count != totalTrips) {
            cost++;
            for (int i : time) {
                if (cost % i == 0) {
                    count++;
                }
            }
        }
        return cost;
    }

    // 二分法
    public long minimumTime0(int[] time, int totalTrips) {
        long left = 1, right = 100000000000000L;
        while (left < right) {
            long mid = (left + right) / 2;
            long count = 0;
            for (int t : time) {
                count += mid / t;
            }
            if (count < totalTrips) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        Queue<int[]> queue = new PriorityQueue<>((a, b) -> (a[1] - b[1]));
        int res = 0;
        for (int[] tire : tires) {
            // 下一次用轮胎时间
            queue.offer(new int[]{tire[0], tire[1], 0, tire[0]});
        }
        int count = 0;
        while (numLaps != count) {
            count++;
            int[] tmp = queue.poll();
            System.out.println("circle:" + count + ",res:" + res);
            if (tmp[3] >= changeTime) {
                res += changeTime;
                res += tmp[0];
                queue.offer(new int[]{tmp[0], tmp[1], 1, (int) (tmp[0] * Math.pow(tmp[1], 1))});
            } else {
                res += tmp[3];
                queue.offer(new int[]{tmp[0], tmp[1], tmp[2] + 1, (int) (tmp[0] * Math.pow(tmp[1], tmp[2] + 1))});
            }
        }
        return res;
    }

    // dp
    public int minimumFinishTime0(int[][] tires, int changeTime, int numLaps) {
        long[] dp = new long[numLaps];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int[] tire : tires) {
            for (long i = 0, f = tire[0], s = tire[0]; i < numLaps && s < Integer.MAX_VALUE; i++, s += f *= tire[1]) {
                dp[(int) i] = Math.min(dp[(int) i], s);
            }
        }
        for (int i = 0; i < numLaps; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] = Math.min(dp[i], dp[j] + dp[i - j - 1] + changeTime);
            }
        }
        return (int) dp[numLaps - 1];
    }
}
