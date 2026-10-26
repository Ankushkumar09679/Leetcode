import java.util.LinkedList;
import java.util.Queue;

class Solution {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // When we find unvisited land, explore the whole island
                if (grid[i][j] > 0) {
                    long islandSum = bfs(grid, i, j);
                    if (islandSum % k == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private long bfs(int[][] grid, int startRow, int startCol) {
        int m = grid.length;
        int n = grid[0].length;
        long sum = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        sum += grid[startRow][startCol];
        grid[startRow][startCol] = 0; // Mark as visited by setting to 0

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int[] dir : DIRECTIONS) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] > 0) {
                    sum += grid[nr][nc];
                    grid[nr][nc] = 0; // Mark as visited
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return sum;
    }
}