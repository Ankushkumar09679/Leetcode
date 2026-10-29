import java.util.*;

class Solution {
    private List<Integer>[] graph;
    private int[][] up;
    private int[] depth, tin, tout, parent;
    private int[] bit;
    private int timer, LOG;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1L << LOG) <= n) LOG++;

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        parent = new int[n];

        int[] order = new int[n];
        int[] stack = new int[n];
        boolean[] seen = new boolean[n];
        int top = 0, count = 0;
        timer = 0;

        stack[top++] = 0;
        seen[0] = true;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = timer++;
            order[count++] = u;

            for (int v : graph[u]) {
                if (!seen[v]) {
                    seen[v] = true;
                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    stack[top++] = v;
                }
            }
        }

        int[] size = new int[n];
        Arrays.fill(size, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            size[parent[u]] += size[u];
        }

        for (int u = 0; u < n; u++) {
            tout[u] = tin[u] + size[u] - 1;
            up[0][u] = parent[u];
        }

        for (int j = 1; j < LOG; j++) {
            for (int u = 0; u < n; u++) {
                up[j][u] = up[j - 1][up[j - 1][u]];
            }
        }

        char[] chars = s.toCharArray();
        bit = new int[n + 2];

        int[] prefix = new int[n];

        for (int u : order) {
            int mask = 1 << (chars[u] - 'a');
            prefix[u] = mask;

            if (u != 0) {
                prefix[u] ^= prefix[parent[u]];
            }

            rangeXor(tin[u], tin[u], prefix[u]);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                if (delta != 0) {
                    rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int lca = getLCA(u, v);

                int maskU = pointQuery(tin[u]);
                int maskV = pointQuery(tin[v]);
                int maskLCA = 1 << (chars[lca] - 'a');

                int pathMask = maskU ^ maskV ^ maskLCA;

                answer.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return answer;
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }

    private void add(int idx, int val) {
        while (idx < bit.length) {
            bit[idx] ^= val;
            idx += idx & -idx;
        }
    }

    private int prefixQuery(int idx) {
        int result = 0;

        while (idx > 0) {
            result ^= bit[idx];
            idx -= idx & -idx;
        }

        return result;
    }

    private void rangeXor(int l, int r, int val) {
        add(l + 1, val);

        if (r + 2 < bit.length) {
            add(r + 2, val);
        }
    }

    private int pointQuery(int idx) {
        return prefixQuery(idx + 1);
    }
}