class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long k = (long) k1 + k2;
        int[] freq = new int[100001];
        long sum = 0;

        for (int i = 0; i < n; i++) {
            int d = Math.abs(nums1[i] - nums2[i]);
            freq[d]++;
            sum += d;
        }

        if (sum <= k) return 0;

        for (int d = 100000; d > 0 && k > 0; d--) {
            if (freq[d] == 0) continue;

            int next = d - 1;
            long operations = Math.min(k, (long) freq[d]);

            freq[d] -= operations;
            freq[next] += operations;
            k -= operations;
        }

        long ans = 0;

        for (int d = 1; d <= 100000; d++) {
            ans += (long) d * d * freq[d];
        }

        return ans;
    }
}