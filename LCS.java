import java.util.Scanner;
/**
 * 最长公共子序列：动态规划将时间复杂度降低，动态数组降低空间复杂度
 * */
public class LCS {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String A = sc.nextLine();
        String B = sc.nextLine();
        LCS ts = new LCS();
        ts.longestCommonSubsequence_1(A, B);
        ts.longestCommonSubsequence_2(A, B);
    }
    public int longestCommonSubsequence_1(String text1, String text2){
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int m = str1.length;
        int n = str2.length;

        int[][] dp = new int[m][n];
        boolean flag = false;
        for (int i = 0; i < n; i++){
            if (str1[0] == str2[i] && !flag){
                flag = true;
            }
            if(flag){
                dp[0][i] = 1;
            }
        }
        flag = false;
        for (int i = 0; i < m; i++){
            if(str1[i] == str2[0] && !flag ){
                flag = true;
            }
            if(flag){
                dp[i][0] = 1;
            }
        }
        for (int i = 1; i < m; i++){
            for (int j = 1; j < n; j++){
                if(str1[i] == str2[j]){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j-1]);
                }
            }
        }
        return dp[m-1][n-1];
    }
    public int longestCommonSubsequence_2(String text1, String text2){
        int m = text1.length();
        int n = text2.length();
        m++;
        n++;
        int[] dp= new int[n];

        char[] str1 = ("#" + text1).toCharArray();
        char[] str2 = ("#" + text2).toCharArray();

        int now;
        int tmp;
        for(int i = 1; i < m; i++){
            tmp = 0;
            for(int j = 1; j < n; j++){
                now = dp[j];
                if(str1[i] == str2[j]){
                    dp[j] = tmp + 1;
                }else {
                    dp[j] = Math.max(dp[j-1], dp[j]);
                }
                tmp = now;
            }
        }
        return dp[n - 1];
    }
}
