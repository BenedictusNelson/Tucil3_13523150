import java.util.*;

class Solver {
    enum Algorithm { UCS, GREEDY, ASTAR }

    static class Result {
        final State goal;
        final long visited;
        Result(State goal, long visited) { this.goal = goal; this.visited = visited; }
    }

    static Result solve(State start, Board board, Algorithm algo, Heuristic h) {
        Set<State> visited = new HashSet<>();
        Comparator<State> comp;
        switch (algo) {
            case UCS:
                comp = Comparator.comparingInt(s -> s.g);
                break;
            case GREEDY:
                comp = Comparator.comparingInt(s -> h.estimate(s));
                break;
            case ASTAR:
                comp = Comparator.comparingInt(s -> s.g + h.estimate(s));
                break;
            default:
                throw new IllegalArgumentException("Algoritma tidak dikenal");
        }

        PriorityQueue<State> pq = new PriorityQueue<>(comp);
        pq.add(start);
        long nodes = 0;
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            nodes++;
            if (visited.contains(cur)) continue;
            visited.add(cur);
            if (board.isGoal(cur)) {
                return new Result(cur, nodes);
            }
            for (State nxt : board.expand(cur)) {
                if (!visited.contains(nxt)) {
                    pq.add(nxt);
                }
            }
        }
        return null;
    }
}
