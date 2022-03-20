package week;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author: wenhongliang
 */
public class Week8 {
    public static void main(String[] args) {
        Week8 w = new Week8();
        System.out.println(w.countHillValley(new int[]{2, 4, 1, 1, 6, 5}));
        System.out.println(w.countHillValley(new int[]{6, 6, 5, 5, 4, 1}));
        //System.out.println(w.countCollisions("RLRSLL"));
        System.out.println(w.countCollisions("SSRSSRLLRSLLRSRSSRLRRRRLLRRLSSRR"));
        System.out.println(w.countCollisions("LLSRSSRSSLLSLLLRSLSRL"));
        System.out.println(w.maximumBobPoints(9, new int[]{1, 1, 0, 1, 0, 0, 2, 1, 0, 1, 2, 0}));
        System.out.println(w.maximumBobPoints(3, new int[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2}));
        System.out.println(w.longestRepeating("babacc", "bcb", new int[]{1, 3, 3}));
    }

    // 2,4,1,1,6,5
    public int countHillValley(int[] nums) {
        int res = 0;
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] == nums[i - 1]) continue;
            int j = i + 1;
            while (j < nums.length - 1 && nums[i] == nums[j]) j++;
            if (nums[i - 1] < nums[i] && nums[i] > nums[j]) res++;
            if (nums[i - 1] > nums[i] && nums[i] < nums[j]) res++;
        }
        return res;
    }

    // RLRSLL
    public int countCollisions(String directions) {
        int res = 0;
        Stack<Character> stack = new Stack<>();
        for (char cur : directions.toCharArray()) {
            if (stack.isEmpty()) {
                stack.push(cur);
            } else {
                char pre = stack.pop();
                if (pre == 'L') {
                    stack.push(cur);
                } else if (pre == 'R') {
                    if (cur == 'L') {
                        res += 2;
                        stack.push('S');
                    } else if (cur == 'S') {
                        res += 1;
                        stack.push('S');
                    }
                } else if (pre == 'S') {
                    if (cur == 'L') {
                        res += 1;
                        stack.push('S');
                    } else {
                        stack.push(cur);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 当两辆移动方向相反的车相撞时，碰撞次数加 2 。
     * 当一辆移动的车和一辆静止的车相撞时，碰撞次数加 1 。
     * 显然，左侧的L和右侧的R不会被撞停；
     * 而中间的车辆都会最终停止，因此统计中间的一开始没有停止的车辆数（即不是S的车辆数）即可。
     * >> 比如RL，两辆车被撞停，RS一辆车被装停
     */
    public int countCollisions0(String directions) {
        char[] s = directions.toCharArray();
        int l = 0, r = s.length - 1;
        while (l <= r && s[l] == 'L') ++l;
        while (l <= r && s[r] == 'R') --r;
        int res = 0;
        for (int i = l; i <= r; ++i) if (s[i] == 'L' || s[i] == 'R') ++res;
        return res;
    }

    public int countCollisions1(String directions) {
        int res = 0, right = 0, stop = 0;
        for (char c : directions.toCharArray()) {
            if (c == 'L') {
                if (stop != 0) {
                    res++;
                }
                if (right != 0) {
                    res += right + 1;
                    stop = 1;
                    right = 0;
                }
            }
            if (c == 'R') {
                right++;
                stop = 0;
            }
            if (c == 'S') {
                res += right;
                right = 0;
                stop = 1;
            }
        }
        return res;
    }

    /**
     * 输入：numArrows = 9,aliceArrows = [1,1,0,1,0,0,2,1,0,1,2,0]
     * 输出：                            [0,0,0,0,1,1,0,0,1,2,3,1]
     * 解释：上表显示了比赛得分情况。
     * Bob 获得总分 4 + 5 + 8 + 9 + 10 + 11 = 47 。
     * 可以证明 Bob 无法获得比 47 更高的分数。
     * 对于i，要么不射，要么比alice射的多
     * 还要保证最后得分最大？
     */
    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {
        int[] dp = new int[12];
        int count = numArrows;
        int max = 0;
        for (int i = 11; i >= 0; i--) {
            if (aliceArrows[i] == 0 && count > 0) {
                dp[i] = 1;
                count -= 1;
                max += i;
            }

        }
        for (int i = 11; i >= 0; i--) {
            if (count > aliceArrows[i]) {
                dp[i] = aliceArrows[i] + 1;
                count -= aliceArrows[i] + 1;
                max += i;
            }
        }
        return dp;
    }

    //max为当前最大得分数
    int max = 0;
    //list0用于记录当前最大分数的方法。
    List<Integer> list = new ArrayList<>();

    public int[] maximumBobPoints1(int numArrows, int[] aliceArrows) {
        dfsMax(aliceArrows, 0, numArrows, 0, new ArrayList<>()); //开始暴力尝试
        int[] arr = new int[12];
        for (int i = 0; i < 12; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    void dfsMax(int[] aliceArrows, int value, int num, int idx, List<Integer> path) {
        if (max < value && path.size() == 12) { // 有得分更大的方法出现 && 长度必须和length相同
            max = value;
            list = new ArrayList<>(path);
            if (num != 0) { //如果有剩余的箭，随便射到哪里都可以，这里就假设都射到0区域了
                list.set(0, list.get(0) + num);
            }
        }

        for (int i = idx; i < 12; i++) { //for循环从now开始
            if (num >= aliceArrows[i] + 1) { //手中剩余的箭满足能得分的条件
                List<Integer> tmp = new ArrayList<>(path);
                tmp.add(aliceArrows[i] + 1);
                dfsMax(aliceArrows, value + i, num - (aliceArrows[i] + 1), i + 1, tmp);
            }
            // 不得分
            path.add(0);
            if (i == aliceArrows.length - 1) {
                dfsMax(aliceArrows, value, num, i + 1, new ArrayList<>(path));
            }
        }
    }

    /**
     * 输入：s = "babacc", queryCharacters = "bcb", queryIndices = [1,3,3]
     * 输出：[3,3,4]
     * 解释：
     * - 第 1 次查询更新后 s = "bbbacc" 。由单个字符重复组成的最长子字符串是 "bbb" ，长度为 3 。
     * - 第 2 次查询更新后 s = "bbbccc" 。由单个字符重复组成的最长子字符串是 "bbb" 或 "ccc"，长度为 3 。
     * - 第 3 次查询更新后 s = "bbbbcc" 。由单个字符重复组成的最长子字符串是 "bbbb" ，长度为 4 。
     * 因此，返回 [3,3,4] 。
     */
    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = queryIndices.length;
        int[] res = new int[n];
        char[] ch = s.toCharArray();
        for (int i = 0; i < n; i++) {
            int count = dfs(ch, queryCharacters.charAt(i), queryIndices[i]);
            res[i] = count;
        }
        return res;
    }

    private int dfs(char[] ch, char c, int idx) {
        ch[idx] = c;
        int max = 0;
        int i = 0;
        while (i < ch.length) {
            int j = i + 1;
            while (j < ch.length && ch[i] == ch[j]) j++;
            max = Math.max(max, j - i);
            i = j;
        }
        return max;
    }
}
