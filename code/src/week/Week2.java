package week;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: wenhongliang
 */
public class Week2 {

    public static void main(String[] args) {
        System.out.println(new Week2().maxScoreIndices(new int[]{0, 0, 1, 0}));
        System.out.println(new Week2().subStrHash1("xmmhdakfursinye", 96, 45, 15, 21));
    }

    public List<Integer> maxScoreIndices(int[] nums) {
        int len = nums.length;
        int[] left = new int[len + 1];
        int[] right = new int[len + 1];
        // 1234
        for (int i = 1; i <= len; i++) {
            if (nums[i - 1] == 0) {
                left[i] = left[i - 1] + 1; // 0 0 0
            } else {
                left[i] = left[i - 1];
            }
        }
        // 3210
        for (int i = len - 1; i >= 0; i--) {
            if (nums[i] == 1) {
                right[i] = right[i + 1] + 1; // 0 2 1
            } else {
                right[i] = right[i + 1];
            }
        }
        int[] sum = new int[len + 1];
        int max = 0;
        for (int i = 0; i <= len; i++) {
            sum[i] = left[i] + right[i];
            max = Math.max(max, sum[i]);
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= len; i++) {
            if (sum[i] == max) {
                res.add(i);
            }
        }
        return res;
    }

    public List<Integer> maxScoreIndicesTreeMap(int[] nums) {
        int left = 0, right = 0;
        for (int num : nums) {
            right += num;
        }
        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<>();
        for (int i = 0; i <= nums.length; i++) {
            map.computeIfAbsent(left + right, t -> new ArrayList<>()).add(i);
            left += i < nums.length ? 1 - nums[i] : 0;
            right -= i < nums.length ? nums[i] : 0;
        }
        return map.lastEntry().getValue();
    }

    /**
     * 给定整数 p 和 m ，一个长度为 k 且下标从 0 开始的字符串 s 的哈希值按照如下函数计算：
     * hash(s, p, m) = (val(s[0]) * p0 + val(s[1]) * p1 + ... + val(s[k-1]) * pk-1) mod m.
     * 其中 val(s[i]) 表示 s[i] 在字母表中的下标，从 val('a') = 1 到 val('z') = 26 。
     * 给你一个字符串 s 和整数 power，modulo，k 和 hashValue 。请你返回 s 中 第一个 长度为 k 的 子串 sub ，满足 hash(sub, power, modulo) == hashValue 。
     * 测试数据保证一定 存在 至少一个这样的子串。
     */
    public String subStrHash1(String s, int power, int modulo, int k, int hashValue) {
        for (int i = 0; i < s.length() - k; i++) {
            String sub = s.substring(i, i + k);
            long hash = 0;
            int idx = 0;
            for (char c : sub.toCharArray()) {
                hash += (c - 'a' + 1) * Math.pow(power, idx++);
            }
            if (hash % modulo == hashValue) {
                return sub;
            }
        }
        if (s.length() == k) {
            long hash = 0;
            int idx = 0;
            for (char c : s.toCharArray()) {
                hash += (c - 'a' + 1) * Math.pow(power, idx++);
            }
            if (hash % modulo == hashValue) {
                return s;
            }
        }
        return "";
    }

    public String subStrHash0(String s, int power, int m, int k, int hashValue) {
        long h = 0, p = 1;
        int index = s.length() - k;
        for (int i = s.length() - 1; i >= s.length() - k; i--) {
            h = (h * power + s.charAt(i) - 'a' + 1) % m;
            p = p * power % m;
        }
        for (int i = s.length() - k - 1; i >= 0; i--) {
            if ((h = (h * power + s.charAt(i) - 'a' + 1 - (s.charAt(i + k) - 'a' + 1) * p % m + m) % m) == hashValue) {
                index = i;
            }
        }
        return s.substring(index, index + k);
    }

    /**
     * 给你一个下标从 0 开始的字符串数组 words 。每个字符串都只包含 小写英文字母 。words 中任意一个子串中，每个字母都至多只出现一次。
     * 如果通过以下操作之一，我们可以从 s1 的字母集合得到 s2 的字母集合，那么我们称这两个字符串为 关联的 ：
     * 往 s1 的字母集合中添加一个字母。
     * 从 s1 的字母集合中删去一个字母。
     * 将 s1 中的一个字母替换成另外任意一个字母（也可以替换为这个字母本身）。
     * 数组 words 可以分为一个或者多个无交集的 组 。一个字符串与一个组如果满足以下 任一 条件，它就属于这个组：
     * 它与组内 至少 一个其他字符串关联。
     * 它是这个组中 唯一 的字符串。
     * 注意，你需要确保分好组后，一个组内的任一字符串与其他组的字符串都不关联。可以证明在这个条件下，分组方案是唯一的。
     * 请你返回一个长度为 2 的数组 ans ：
     * ans[0] 是 words 分组后的 总组数 。
     * ans[1] 是字符串数目最多的组所包含的字符串数目。
     */
    public int[] groupStrings(String[] words) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (String word : words) {
            int mask = 0;
            for (char c : word.toCharArray()) {
                mask |= 1 << c - 'a';
            }
            map.put(mask, map.getOrDefault(mask, 0) + 1);
        }
        int[] r = new int[2];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            r = entry.getValue() > 0 ? new int[]{r[0] + 1, Math.max(r[1], groupStrings(entry.getKey(), map))} : r;
        }
        return r;
    }

    private int groupStrings(int mask, HashMap<Integer, Integer> map) {
        int sum = map.getOrDefault(mask, 0);
        if (sum > 0) {
            map.put(mask, 0);
            for (int i = 0; i < 26; i++) {
                sum += groupStrings(mask ^ 1 << i, map);
                for (int j = i + 1; j < 26; j++) {
                    sum += (mask >> i & 1) == (mask >> j & 1) ? 0 : groupStrings(mask ^ 1 << i ^ 1 << j, map);
                }
            }
        }
        return sum;
    }
}
