package week;

import week.dto.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Week6 {

    public static void main(String[] args) {
        System.out.println(new Week6().cellsInRange("K1:L2"));
        System.out.println(new Week6().minimalKSum(new int[]{5, 6}, 6));
        System.out.println(new Week6().createBinaryTree(new int[][]{{20, 15, 1}, {20, 17, 0}, {50, 20, 1}, {50, 80, 0}, {80, 19, 1}}));
        System.out.println(new Week6().createBinaryTree(new int[][]{{1, 2, 1}, {2, 3, 0}, {3, 4, 1}}));
    }

    public List<String> cellsInRange(String s) {
        List<String> res = new ArrayList<>();
        char[] ch = s.toCharArray();
        for (char i = ch[0]; i <= ch[3]; i++) {
            for (char j = ch[1]; j <= ch[4]; j++) {
                res.add("" + i + j);
            }
        }
        return res;
    }

    public long minimalKSum(int[] nums, int k) {
        long res = 0;
        Arrays.sort(nums);
        int min = 1;
        for (int x : nums) {
            while (x > min) {
                res += min;
                k--;
                min++;
                if (k == 0) return res;
            }
            min = x + 1;
        }
        while (k-- > 0) {
            res += min;
            min += 1;
        }
        return res;
    }

    public long minimalKSum0(int[] nums, int k) {
        Arrays.sort(nums);
        long ans = 0;
        long start = 1;
        for (int i = 0; i < nums.length && k > 0; i++) {
            if (start < nums[i]) {
                int cnt = Math.min((int) (nums[i] - start), k);
                ans += (2L * start + cnt - 1) * cnt / 2;
                k -= cnt;
            }
            start = (nums[i] + 1);
        }

        if (k > 0) {
            ans += (2L * start + k - 1) * k / 2;
        }
        return ans;
    }

    public TreeNode createBinaryTree(int[][] descriptions) {
        Map<Integer, TreeNode> treeNodeMap = new HashMap<>();
        for (int[] desc : descriptions) {
            TreeNode root = treeNodeMap.getOrDefault(desc[0], new TreeNode(desc[0]));
            TreeNode child = treeNodeMap.getOrDefault(desc[1], new TreeNode(desc[1]));
            if (desc[2] == 1) {
                root.left = child;
            }
            if (desc[2] == 0) {
                root.right = child;
            }
            treeNodeMap.put(root.val, root);
            treeNodeMap.put(child.val, child);
        }
        Map<Integer, int[]> colorMap = new HashMap<>();
        for (int node : treeNodeMap.keySet()) {
            colorMap.put(node, new int[]{0, 0});
        }
        for (int[] desc : descriptions) {
            if (colorMap.containsKey(desc[0])) colorMap.get(desc[0])[0] = 1;
            if (colorMap.containsKey(desc[1])) colorMap.get(desc[1])[1] = 1;
        }
        TreeNode root = null;
        for (int node : treeNodeMap.keySet()) {
            if (colorMap.get(node)[1] == 0) root = treeNodeMap.get(node);
        }
        return root;
    }

    public TreeNode createBinaryTree0(int[][] descriptions) {
        // 存储所有的子节点
        Set<Integer> childs = new HashSet<>();

        Map<Integer, TreeNode> map = new HashMap<>();
        for (int[] desc : descriptions) {
            TreeNode pNode = map.getOrDefault(desc[0], new TreeNode(desc[0]));
            TreeNode cNode = map.getOrDefault(desc[1], new TreeNode(desc[1]));
            if (desc[2] == 1) {
                pNode.left = cNode;
            } else {
                pNode.right = cNode;
            }
            map.put(desc[0], pNode);
            map.put(desc[1], cNode);
            childs.add(cNode.val);
        }

        // 找到根节点
        for (int key : map.keySet()) {
            if (!childs.contains(key)) {
                return map.get(key);
            }
        }
        return null;
    }


    public List<Integer> replaceNonCoprimes(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int n : nums) {
            if (res.isEmpty())
                res.add(n);
            else {
                long b = n;
                while (!res.isEmpty()) {
                    int a = res.get(res.size() - 1);
                    long gcd = gcd(a, b);
                    if (gcd == 1) break;
                    res.remove(res.size() - 1);
                    b = a * b / gcd;
                }
                res.add((int) b);
            }
        }
        return res;
    }

    long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
