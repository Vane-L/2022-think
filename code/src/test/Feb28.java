package test;

/**
 * @Author: wenhongliang
 */
public class Feb28 {
    public static void main(String[] args) {
        System.out.println(new Feb28().getSize("10K"));
        System.out.println(new Feb28().getSize("10"));
        System.out.println(new Feb28().getSize("5.7M"));
        System.out.println(new Feb28().getSize("1.2G"));
        System.out.println(new Feb28().getSize("9G"));
    }

    public int getSize(String str) {
        if (str == null || str.length() == 0) return -1;
        // 单位
        char kmg = str.charAt(str.length() - 1);
        double res;
        // 前面的数字
        double prefix = Double.parseDouble(str.substring(0, str.length() - 1));
        switch (kmg) {
            case 'K':
                res = prefix * 1024;
                break;
            case 'M':
                res = prefix * 1024 * 1024;
                break;
            case 'G':
                res = prefix * 1024 * 1024 * 1024;
                break;
            default:
                res = Double.parseDouble(str);
        }
        return res > Integer.MAX_VALUE ? -1 : (int) res;
    }
}
