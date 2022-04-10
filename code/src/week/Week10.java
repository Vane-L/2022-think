package week;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * @Author: wenhongliang
 */
public class Week10 {
    public static void main(String[] args) {
        Week10 w = new Week10();
        System.out.println(w.minimizeResult("247+38"));
        System.out.println(w.minimizeResult("12+34"));
        System.out.println(w.maximumProduct0(new int[]{0, 4}, 5));
    }


    public int largestInteger(int num) {
        String s = String.valueOf(num);
        char[] ch = s.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            for (int j = i + 1; j < ch.length; j++) {
                if ((ch[i] - '0') % 2 == 0 && (ch[j] - '0') % 2 == 0) {
                    swap(ch, i, j);
                } else if ((ch[i] - '0') % 2 == 1 && (ch[j] - '0') % 2 == 1) {
                    swap(ch, i, j);
                }
            }
        }
        return Integer.parseInt(new String(ch));
    }

    public void swap(char[] ch, int i, int j) {
        if (ch[i] < ch[j]) {
            char tmp = ch[i];
            ch[i] = ch[j];
            ch[j] = tmp;
        }
    }


    Map<Integer, String> map = new HashMap<>();

    public String minimizeResult(String expression) {
        int idx = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+') {
                idx = i;
                break;
            }
        }
        for (int i = 0; i < idx; i++) {
            for (int j = expression.length() - 1; j > idx; j--) {
                min = Math.min(min, calculate(expression, i, j, idx));
            }
        }
        return map.get(min);
    }

    public int calculate(String s, int i, int j, int idx) {
        String str = s.substring(0, i) + "(" + s.substring(i, j + 1) + ")" + s.substring(j + 1);
        int a = 0, b = 0, c = 0;
        int x = i, y = j, z = idx + 1;

        int tmp = 0;
        int start = 0, end = s.length() - 1;
        while (start < i) {
            tmp = tmp * 10 + s.charAt(start) - '0';
            start++;
        }
        a = tmp == 0 ? 1 : tmp;

        tmp = 0;
        while (y + 1 <= end) {
            tmp = tmp * 10 + s.charAt(y + 1) - '0';
            y++;
        }
        b = tmp == 0 ? 1 : tmp;

        tmp = 0;
        int sum = 0;
        while (x < idx) {
            tmp = tmp * 10 + s.charAt(x) - '0';
            x++;
        }
        sum += tmp;

        tmp = 0;
        while (z <= j) {
            tmp = tmp * 10 + s.charAt(z) - '0';
            z++;
        }
        sum += tmp;

        int res = a * sum * b;
        map.put(res, str);
        return res;
    }

    public String minimizeResult0(String expression) {
        String result = "";
        String[] split = expression.split("\\+");
        for (int i = 0, min = Integer.MAX_VALUE; i < split[0].length(); i++) {
            for (int j = 1; j <= split[1].length(); j++) {
                int cur = (i > 0 ? Integer.parseInt(split[0].substring(0, i)) : 1)
                        * (Integer.parseInt(split[0].substring(i)) + Integer.parseInt(split[1].substring(0, j)))
                        * (j < split[1].length() ? Integer.parseInt(split[1].substring(j)) : 1);
                if (cur < min) {
                    min = cur;
                    result = split[0].substring(0, i)
                            + '(' + split[0].substring(i) + '+' + split[1].substring(0, j) + ')'
                            + split[1].substring(j);
                }
            }
        }
        return result;
    }

    public int maximumProduct(int[] nums, int k) {
        Arrays.sort(nums);
        int max = nums[nums.length - 1];
        for (int i = nums.length - 1; i >= 0; i--) {
            while (nums[i] <= max && k > 0) {
                nums[i] += 1;
                k -= 1;
            }
        }
        long res = 1;
        for (int x : nums) {
            res *= x;
            res = res % 1000000007;
        }
        return (int) res;
    }

    public int maximumProduct0(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : nums) {
            queue.offer(num);
        }
        for (int i = 0; i < k; i++) {
            queue.offer(queue.poll() + 1);
        }
        long res = 1;
        for (int num : queue) {
            res = res * num % 1000000007;
        }
        return (int) res;
    }

    public long maximumBeauty(int[] flowers, long newFlowers, int target, long full, int partial) {
        Arrays.sort(flowers);
        TreeMap<Long, Integer> map = new TreeMap<>();
        map.put(Long.MAX_VALUE, flowers.length);
        long max = 0, sum[] = new long[flowers.length + 1];
        for (int i = 0; i < flowers.length; i++) {
            sum[i + 1] = flowers[i] + sum[i];
            map.putIfAbsent((long) flowers[i], i);
        }
        for (int i = flowers.length - 1; i >= 0 && newFlowers > 0; i--) {
            if (flowers[i] < target) {
                long left = 0, right = target - 1;
                while (left < right) {
                    long mid = (left + right + 1) / 2, index = Math.min(i + 1, map.higherEntry(mid).getValue());
                    if (mid * index - sum[(int) index] > newFlowers) {
                        right = mid - 1;
                    } else {
                        left = mid;
                    }
                }
                max = Math.max(max, full * (flowers.length - 1 - i) + partial * left);
            }
            newFlowers -= Math.max(0, target - flowers[i]);
        }
        return Math.max(max, newFlowers < 0 ? 0 : full * flowers.length);
    }
}
