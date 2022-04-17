package week;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Author: wenhongliang
 */
public class Week11 {
    public static void main(String[] args) {
        Week11 w = new Week11();
        System.out.println(w.digitSum("11111222223", 3));
        System.out.println(w.digitSum("000", 3));
        System.out.println(w.minimumRounds(new int[]{2, 2, 3, 3, 2, 4, 4, 4, 4, 4}));
        System.out.println(w.minimumRounds(new int[]{2, 2, 3}));
        System.out.println(w.minimumRounds(new int[]{7, 7, 7, 7, 7, 7}));
    }

    public String digitSum(String s, int k) {
        while (s.length() > k) {
            s = digit(s, k);
        }
        return s;
    }

    private String digit(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i += k) {
            sb.append(sum(s.substring(i, Math.min(i + k, s.length()))));
        }
        return sb.toString();
    }

    private int sum(String tmp) {
        int res = 0;
        for (char c : tmp.toCharArray()) {
            res += c - '0';
        }
        return res;
    }

    public String digitSum0(String s, int k) {
        while (s.length() > k) {
            StringBuilder sb = new StringBuilder();
            int count = 0;
            int sum = 0;
            for (char c : s.toCharArray()) {
                if (count == k) {
                    sb.append(sum);
                    count = 0;
                    sum = 0;
                }
                count++;
                sum += c - '0';
            }
            sb.append(sum);
            s = sb.toString();
        }
        return s;
    }

    public int minimumRounds(int[] tasks) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : tasks) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        int res = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int value = entry.getValue();
            if (value < 2) return -1;
            while (value > 0) {
                if (value % 3 == 0) {
                    value -= 3;
                } else {
                    value -= 2;
                }
                res++;
            }
        }
        return res;
    }

    public int minimumRounds0(int[] tasks) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : tasks) {
            map.compute(i, (k, v) -> v == null ? 1 : v + 1);
        }
        int res = 0;
        for (int count : map.values()) {
            if (count == 1) {
                return -1;
            }
            res += count / 3 + (count % 3 == 0 ? 0 : 1);
        }
        return res;
    }


    public int maxTrailingZeros(int[][] grid) {
        int res = 0;
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] % 2 == 0 || grid[i][j] % 5 == 0 || grid[i][j] % 10 == 0) {
                    res = Math.max(res, dfs(grid, i, j, visited, 1));
                }
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int i, int j, boolean[][] visited, int cur) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j]) return 0;
        visited[i][j] = true;
        cur *= grid[i][j];
        int a = dfs(grid, i + 1, j, visited, cur);
        int b = dfs(grid, i - 1, j, visited, cur);
        int c = dfs(grid, i, j + 1, visited, cur);
        int d = dfs(grid, i, j - 1, visited, cur);
        int res = cur;
        return res;
    }

    public int maxTrailingZeros0(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[][][][] map = new int[n][m][4][2];
        // from left to right
        for (int i = 0; i < n; i++) {
            int count2 = 0;
            int count5 = 0;
            for (int j = 0; j < m; j++) {
                count2 += getCount(grid[i][j], 2);
                count5 += getCount(grid[i][j], 5);
                map[i][j][0][0] = count2;
                map[i][j][0][1] = count5;
            }
        }

        // from right to left
        for (int i = 0; i < n; i++) {
            int count2 = 0;
            int count5 = 0;
            for (int j = m - 1; j >= 0; j--) {
                count2 += getCount(grid[i][j], 2);
                count5 += getCount(grid[i][j], 5);
                map[i][j][1][0] = count2;
                map[i][j][1][1] = count5;
            }
        }

        // from up to down
        for (int j = 0; j < m; j++) {
            int count2 = 0;
            int count5 = 0;
            for (int i = 0; i < n; i++) {
                map[i][j][2][0] = count2;
                map[i][j][2][1] = count5;
                count2 += getCount(grid[i][j], 2);
                count5 += getCount(grid[i][j], 5);
            }
        }

        // from down to up
        for (int j = 0; j < m; j++) {
            int count2 = 0;
            int count5 = 0;
            for (int i = n - 1; i >= 0; i--) {
                map[i][j][3][0] = count2;
                map[i][j][3][1] = count5;
                count2 += getCount(grid[i][j], 2);
                count5 += getCount(grid[i][j], 5);
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int left2 = map[i][j][0][0];
                int right2 = map[i][j][1][0];
                int up2 = map[i][j][2][0];
                int down2 = map[i][j][3][0];
                int left5 = map[i][j][0][1];
                int right5 = map[i][j][1][1];
                int up5 = map[i][j][2][1];
                int down5 = map[i][j][3][1];
                res = Math.max(res, Math.min(left2 + up2, left5 + up5));
                res = Math.max(res, Math.min(left2 + down2, left5 + down5));
                res = Math.max(res, Math.min(right2 + up2, right5 + up5));
                res = Math.max(res, Math.min(right2 + down2, right5 + down5));
            }
        }
        return res;
    }

    private int getCount(int val, int base) {
        int res = 0;
        while (val > 0) {
            if (val % base != 0) {
                return res;
            }
            val /= base;
            res++;
        }
        return res;
    }

    //-1,0,0,1,1,2
    // a b a c b e
    public int longestPath(int[] parent, String s) {
        Map<Character, List<Character>> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, new ArrayList<>());
        }
        for (int i = 0; i < parent.length; i++) {
            for (int j = 0; j < parent.length; j++) {
                if (parent[j] == i) {
                    map.get(s.charAt(i)).add(s.charAt(j));
                }
            }
        }
        return 1;
    }

    private int path(int[] parent, char[] ch) {
        Set<Character> set = new HashSet<>();
        set.add(ch[0]);

        return set.size();
    }

    public int longestPath0(int[] parent, String s) {
        ArrayList<Integer>[] list = new ArrayList[parent.length];
        for (int i = 0; i < parent.length; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 1; i < parent.length; i++) {
            list[parent[i]].add(i);
        }
        return getPath(0, s, list)[1];
    }

    private int[] getPath(int index, String s, ArrayList<Integer>[] list) {
        int max = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i : list[index]) {
            int[] result = getPath(i, s, list);
            max = Math.max(max, result[1]);
            if (s.charAt(i) != s.charAt(index)) {
                queue.offer(-result[0]);
            }
        }
        return new int[]{1 - (queue.isEmpty() ? 0 : queue.peek()),
                Math.max(max, 1 - (queue.isEmpty() ? 0 : queue.poll()) - (queue.isEmpty() ? 0 : queue.poll()))};
    }

    int res = 0;

    public int longestPath1(int[] parent, String s) {
        int n = s.length();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(s.charAt(i));
        }
        for (int i = 0; i < n; i++) {
            if (parent[i] != -1) {
                nodes[parent[i]].child.add(nodes[i]);
            }
        }
        dfs(nodes[0]);
        return res + 1;
    }

    private int dfs(Node cur) {
        int maxLen = 0;
        for (Node next : cur.child) {
            int len = dfs(next);
            if (next.val != cur.val) {
                res = Math.max(res, maxLen + len + 1);
                maxLen = Math.max(maxLen, len + 1);
            }
        }
        return maxLen;
    }
}

class Node {
    char val;
    List<Node> child;

    public Node(char val) {
        this.val = val;
        child = new ArrayList<>();
    }
}
