package week;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * @Author: wenhongliang
 */
public class Week4 {
    public static void main(String[] args) {
        System.out.println(new Week4().repeatLimitedString("cczazcc", 3));// zzcccac
        System.out.println(new Week4().repeatLimitedString0("cczazcc", 3));// zzcccac
        System.out.println(new Week4().repeatLimitedString("aababab", 2));// bbabaa
        System.out.println(new Week4().coutPairs(new int[]{1, 2, 3, 4, 5}, 2));// 7
    }

    public String repeatLimitedString(String s, int repeatLimit) {
        Map<Character, Integer> map = new TreeMap<>((a, b) -> (b - a));
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : map.keySet()) {
            int count = Math.min(repeatLimit, map.get(c));
            for (int i = 0; i < count; i++) {
                sb.append(c);
                map.put(c, map.get(c) - 1);
            }
        }
        return sb.toString();
    }

    public String repeatLimitedString1(String s, int repeatLimit) {
        // 用于存放每个字符的个数
        int[] count = new int[26];
        // 逐一进行遍历，并且记录每个字符出现的次数
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        // 用于返回结果字符串
        StringBuilder res = new StringBuilder();
        // 按照字符在26个字符当中的顺序倒叙添加到优先队列当中
        PriorityQueue<Integer> pq = new PriorityQueue<>((c1, c2) -> c2 - c1);
        // 遍历每个字符的个数 如果存在字符就将其添加到优先队列当中
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) pq.add(i);
        }
        int checkLimitNum = 0;
        // 如果当前队列中还存在元素就继续构建结果字符串
        while (!pq.isEmpty()) {
            // 取出当前字符的字符序号 例如 0-a 1-b ... 25-z
            int maxCntCharIdx = pq.poll();
            // 如果当前字符串长度为0 或者 当前拼接的字符与上一个字符相同时, 此时我们就要考虑repeatLimit的限制了
            if (res.length() == 0 || (maxCntCharIdx + 'a') == res.charAt(res.length() - 1)) {
                // 连续相同字符数量+1
                checkLimitNum++;
            } else {
                // 反之重置为1
                checkLimitNum = 1;
            }
            // 然后对连续相同字符的限制进行判断
            if (checkLimitNum <= repeatLimit) {
                // 如果满足条件就直接将其加入到我们的结果字符串当中，并且字符数量减少
                res.append((char) ('a' + maxCntCharIdx));
                // 如果当前还有剩余数量的字符 我们还需要继续把他返回到优先队列中进行下次判断 直到用完该字符序号的所有字符
                if (--count[maxCntCharIdx] > 0) pq.add(maxCntCharIdx);
            } else {
                // 反之到达了限制时 我们此时就要取出次高字典序的字符序号了 这和上周280周赛的第二题有点像
                if (!pq.isEmpty()) {
                    // 重新将连续相同字符数量置为1
                    checkLimitNum = 1;
                    // 取出次字典序高的字符序号
                    int subMaxCntCharIdx = pq.poll();
                    // 将次高的字符序号字符加入其中
                    res.append((char) ('a' + subMaxCntCharIdx));
                    // 将我们之前取出的第一个最高数量的字符返回到优先队列当中，因为咱们没有用这个 重复操作
                    pq.add(maxCntCharIdx);
                    // 如果当前次高的字符数量还有剩余 则也将其返回到优先队列当中 重复操作
                    if (--count[subMaxCntCharIdx] > 0) pq.add(subMaxCntCharIdx);
                } else {
                    // 返回结果
                    return res.toString();
                }
            }
        }
        return res.toString();
    }

    public String repeatLimitedString0(String s, int repeatLimit) {
        TreeMap<Character, Integer> map = new TreeMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        Map.Entry<Character, Integer> entry = null;
        StringBuilder sb = new StringBuilder();
        for (int count = 0; !map.isEmpty(); count++) {
            if (count == repeatLimit) {
                entry = map.lastEntry();
                map.remove(map.lastKey());
            } else {
                sb.append(map.lastKey());
                map.put(map.lastKey(), map.get(map.lastKey()) - 1);
                if (map.get(map.lastKey()) == 0) {
                    map.remove(map.lastKey());
                    count = -1;
                }
                if (entry != null) {
                    map.put(entry.getKey(), entry.getValue());
                    entry = null;
                    count = -1;
                }
            }
        }
        return sb.toString();
    }

    public long coutPairs(int[] nums, int k) {
        long count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % k == 0) {
                count += nums.length - 1 - i;
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] * nums[j] % k == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public long coutPairs0(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        long res = 0;
        for (int num : nums) {
            int d = gcd(num, k);
            map.put(d, map.getOrDefault(d, 0) + 1);
        }

        for (int num : nums) {
            int cur = gcd(num, k);
            map.put(cur, map.get(cur) - 1);
            if (map.get(cur) == 0) map.remove(cur);
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int key = entry.getKey();
                if ((long) key * cur % k == 0) res += entry.getValue(); //防止溢出
            }
        }
        return res;
    }

    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public long coutPairs1(int[] nums, int k) {
        long res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        // 统计每个n个k的最大公因数
        for (int n : nums) {
            int x = gcd(n, k);
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        // k1 <= k2,组合即可
        for (int k1 : map.keySet()) {
            for (int k2 : map.keySet()) {
                long tt = (long) k1 * (long) k2 % k;
                if (tt != 0) continue;
                if (k1 < k2) {
                    long a = (long) map.get(k1);
                    long b = (long) map.get(k2);
                    res += a * b;
                } else if (k1 == k2) {
                    long a = (long) map.get(k1);
                    long b = a - 1;
                    long tmp = a * b / 2;
                    res += tmp;
                }
            }
        }
        return res;
    }

}
