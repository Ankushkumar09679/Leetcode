
class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        // Step 1: Precompute minimum changes
        // required for every substring.
        for (int len = 2; len <= n; len++) {
            for (int start = 0; start + len <= n; start++) {
                int end = start + len - 1;
                int best = Integer.MAX_VALUE;

                // Find proper divisors of substring length
                for (int d = 1; d < len; d++) {
                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Compare characters in each group
                    for (int offset = 0; offset < d; offset++) {
                        int left = start + offset;
                        int right = end - d + 1 + offset;

                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    best = Math.min(best, changes);
                }

                cost[start][end] = best;
            }
        }

        // Step 2: Dynamic Programming
        int INF = 1000000;
        int[][] dp = new int[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = INF;
            }
        }

        dp[0][0] = 0;

        // dp[i][parts] = minimum changes to divide
        // first i characters into exactly parts substrings
        for (int i = 2; i <= n; i++) {
            for (int parts = 1; parts <= k; parts++) {
                for (int j = 0; j <= i - 2; j++) {
                    if (dp[j][parts - 1] == INF) {
                        continue;
                    }

                    dp[i][parts] = Math.min(
                        dp[i][parts],
                        dp[j][parts - 1] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
