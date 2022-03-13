package week;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Author: wenhongliang
 */
public class Week7 {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
    }

    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        Set<Integer> set = new HashSet<>();
        // |i-j|<=k
        // i-j<=k j>=i-k
        // j-i<=k j<=i+k
        for (int i = 0; i < nums.length; i++) {
            int start = i - k < 0 ? 0 : i - k;
            int end = i + k > nums.length - 1 ? nums.length - 1 : i + k;
            for (int j = start; j <= end; j++) {
                if (nums[j] == key) {
                    set.add(i);
                }
            }
        }
        List<Integer> res = new ArrayList<>(set);
        Collections.sort(res);
        return res;
    }

    public List<Integer> findKDistantIndices1(int[] nums, int key, int k) {
        List<Integer> res = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        // 将值等下标加入set
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == key) set.add(i);
        }
        // 模拟
        for (int i = 0; i < nums.length; i++) {
            for (int j : set) {
                // 对当前值等下标进行 绝对值差 判断是否符合条件 <=k 如果符合条件就将其加入结果
                if (Math.abs(i - j) <= k) {
                    res.add(i);
                    break;
                }
            }
        }
        return res;
    }

    public int digArtifacts(int n, int[][] artifacts, int[][] dig) {
        HashSet<List<Integer>> set = new HashSet<>();
        for (int[] i : dig) {
            set.add(Arrays.asList(i[0], i[1]));
        }
        int count = 0;
        for (int[] artifact : artifacts) {
            if (digArtifacts(artifact, set)) {
                count++;
            }
        }
        return count;
    }

    private boolean digArtifacts(int[] artifact, HashSet<List<Integer>> set) {
        for (int i = artifact[0]; i <= artifact[2]; i++) {
            for (int j = artifact[1]; j <= artifact[3]; j++) {
                if (!set.contains(Arrays.asList(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int digArtifacts1(int n, int[][] artifacts, int[][] dig) {
        int res = 0;
        boolean[][] visited = new boolean[n][n];
        for (int[] d : dig) {
            visited[d[0]][d[1]] = true;
        }
        for (int[] artifact : artifacts) {
            boolean flag = true;
            for (int row = artifact[0]; row <= artifact[2] && flag; row++) {
                for (int col = artifact[1]; col <= artifact[3] && flag; col++) {
                    flag = visited[row][col];
                }
            }
            if (flag) res++;
        }
        return res;
    }

    /**
     * 思路： 贪心取出前 k−1 个元素，第 k 次操作可以将已经移除的最大的元素放回栈顶，或取出第 k 个元素，此时栈顶为第 k+1 个元素
     * 当 nums.length=1时，k 为奇数则会把唯一元素拿出，返回-1
     * 当 k > nums.length时，保证最后将最大的元素放在栈顶，返回max
     * 当 k < nums.length时，取出前k−1 个元素，放回已取出的最大数或将第 k 个元素取出
     * 当 k = nums.length时，取出前k−1 个元素，再取出一个会导致空栈，将最大元素放回栈顶
     */
    public int maximumTop(int[] nums, int k) {
        if (nums.length == k % 2) return -1;
        int max = 0;
        for (int i = 0; i < Math.min(k - 1, nums.length); i++) {
            max = Math.max(max, nums[i]);
        }
        return Math.max(max, k < nums.length ? nums[k] : 0);
    }

    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        ArrayList<long[]>[] list1 = new ArrayList[n], list2 = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            list1[i] = new ArrayList<>();
            list2[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            list1[edge[0]].add(new long[]{edge[1], edge[2]});
            list2[edge[1]].add(new long[]{edge[0], edge[2]});
        }
        Long s1[] = minimumWeight(src1, list1), s2[] = minimumWeight(src2, list1), d[] = minimumWeight(dest, list2);
        Long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            min = s1[i] == null || s2[i] == null || d[i] == null ? min : Math.min(min, s1[i] + s2[i] + d[i]);
        }
        return min < Long.MAX_VALUE ? min : -1;
    }

    private Long[] minimumWeight(int src, ArrayList<long[]>[] list) {
        Long[] dist = new Long[list.length];
        PriorityQueue<Long[]> queue = new PriorityQueue<>((a, b) -> (int) (a[0] - b[0]));
        queue.offer(new Long[]{0L, (long) src});
        while (!queue.isEmpty()) {
            Long[] poll = queue.poll();
            if (dist[poll[1].intValue()] == null) {
                dist[poll[1].intValue()] = poll[0];
                for (long[] edge : list[poll[1].intValue()]) {
                    queue.offer(new Long[]{poll[0] + edge[1], edge[0]});
                }
            }
        }
        return dist;
    }
}
