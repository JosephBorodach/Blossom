package algo.practice;

import java.util.*;

/**
 * This class provides a solution to the problem of determining whether it is possible to complete all the courses
 * given a list of prerequisite courses in a directed graph.
 *
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/course-schedule/
 *
 * General Problem is to detect cycle in directed graph.
 *
 * Constraints:
 *      1 <= numCourses <= 2000
 *      0 <= prerequisites.length <= 5000
 *      prerequisites[i].length == 2
 *      0 <= ai, bi < numCourses
 *      All the pairs prerequisites[i] are unique.
 *
 * Edge Cases:
 *      prerequisites is null
 *      numCourses is not equal to # of courses in prerequisites
 *
 * Notation:
 *      E is number of edges = length of prerequisites
 *      V = numCourses
 *
 * Two approaches have been implemented in this class:
 * 1. Breadth-first search (BFS)
 * 2. Recursive depth-first search (DFS)
 */
public class CourseSchedule {
    private int numCourses;
    private int[][] prerequisites;
    private List<List<Integer>> adj;

    /**
     * Determines whether it is possible to complete all the courses given a list of prerequisite courses
     *
     * @param numCourses The number of courses to be taken
     * @param prerequisites A list of prerequisite courses
     * @return true if it is possible to complete all the courses, false otherwise
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses < 0) {
            throw new IllegalArgumentException("There cannot be negative numCourses.");
        }
        if (prerequisites == null) {
            return true;
        }
        this.numCourses = numCourses;
        this.prerequisites = prerequisites;

        int i = 0;
        // Choose the approach to be used based on the value of i
        return i == 0 ? bfs() : dfs();
    }

    /**
     * Implements the depth-first search (DFS) approach to solve the problem
     *
     * Pseudocode:
     *      a. Build an adjacency list
     *      b. Recurse over each node to check for cycles
     *      c. hasCycle():
     *          if (inStack[u]):
     *              return true
     *          if (visited[u]):
     *              return false
     *          visited[u] = true
     *          inStack[u] = true
     *              if (hasCycle(each child)):
     *                  return true
     *          inStack[node] = false
     *          return false
     *      d. Return true if there are no cycles
     *
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     *
     * @return true if it is possible to complete all the courses, false otherwise
     */
    public boolean dfs() {
        adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int a = prerequisites[i][0];
            int b = prerequisites[i][1];
            check(a);
            check(b);
            if (a == b) {
                return false;
            }
            adj.get(b).add(a);
        }
        boolean[] inStack = new boolean[numCourses];
        boolean[] visited = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(i, inStack, visited)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method uses Depth First Search to check if there is a cycle in the graph starting at node u.
     *
     * @param u the node to start the search from
     * @param inStack an array of booleans to keep track of whether a node is currently in the recursion stack
     * @param visited an array of booleans to keep track of whether a node has been visited
     * @return true if there is a cycle, false otherwise
     */
    private boolean hasCycle(int u, boolean[] inStack, boolean[] visited) {
        if (inStack[u]) {
            return true;
        }
        if (visited[u]) {
            return false;
        }
        visited[u] = true;
        inStack[u] = true;
        for (int v : adj.get(u)) {
            if (hasCycle(v, inStack, visited)) {
                return true;
            }
        }
        inStack[u] = false;
        return false;
    }

    /**
     * Performs a breadth-first search to detect whether there are any cycles in the prerequisites
     * graph. Returns true if there are no cycles, false otherwise.
     *
     * Pseudocode:
     *      a. Build an adj list
     *      b. Update in degrees
     *      c. Add all nodes to queue with inDegrees of 0
     *      d. while (queue is not empty):
     *          decrement in degree for all children
     *          enqueue child if their inDegree is 0
     *      e. return:
     *          true if nodes visited == V
     *          otherwise false
     *
     * Time: O(V + E)
     * Space: O(V)
     *
     * @return  true if all courses can be completed, false otherwise
     */
    public boolean bfs() {
        adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            int u = prerequisites[i][0];
            int v = prerequisites[i][1];
            check(u);
            check(v);
            if (u == v) {
                return false;
            }
            inDegree[u]++;
            adj.get(v).add(u);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        int visited = 0;
        while(!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj.get(u)) {
                if (--inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
            visited++;
        }
        return visited == numCourses;
    }

    /**
     * @param i
     */
    private void check(int i) {
        if (i < 0 || i >= numCourses) {
            throw new IllegalArgumentException("Courses must be 0 to numCourses - 1.");
        }
    }
}