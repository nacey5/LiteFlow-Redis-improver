

import java.util.Arrays;

/**
 * @author dahuang
 * @version : Solution.java, v 0.1 2023-06-04 22:35 dahuang
 */
class Solution {
    public int[] sortedSquares(int[] nums) {
        for(int i=0;i<nums.length;i++){
            nums[i]=multiply(nums[i],nums[i] );
        }
        return nums;
    }

    public static int multiply(int a, int b) {
        int result = 0;

        while (b != 0) {
            // 如果b的最低位是1，则将a加到结果中
            if ((b & 1) != 0) {
                result += a;
            }

            // 将a左移一位，相当于乘以2
            a <<= 1;

            // 将b右移一位，相当于除以2
            b >>= 1;
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solution=new Solution();
        int[] ret = solution.sortedSquares(new int[] {-4,-1,0,3,10});
        System.out.println(Arrays.toString(ret));
    }
}