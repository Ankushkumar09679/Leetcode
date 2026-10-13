import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        int[] squares = new int[n];

        for (int i = 0; i < n; i++) {
            squares[i] = nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            if (i < n / 2) {
                ans -= squares[i];
            } else {
                ans += squares[i];
            }
        }

        return ans;
    }
}