import java.util.*;

class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        int smaller = 0;
        int equal = 0;

        for (int num : nums) {
            if (num < target) {
                smaller++;
            } else if (num == target) {
                equal++;
            }
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < equal; i++) {
            ans.add(smaller + i);
        }

        return ans;
    }
}