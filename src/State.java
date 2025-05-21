import java.util.*;

class State {
    final char[][] board;                 // immutable snapshot
    final Map<Character, Piece> pieces;   // metadata orientation & length
    final int primaryRow, primaryCol;     // head posisi P (baris, kolom)
    final int g;                          // cost so far (langkah)
    final Move move;                      // move that produced this state (null untuk start)
    final State prev;                     // parent link

    State(char[][] board, Map<Character, Piece> pieces, int pRow, int pCol, int g, Move move, State prev) {
        this.board = board;
        this.pieces = pieces;
        this.primaryRow = pRow;
        this.primaryCol = pCol;
        this.g = g;
        this.move = move;
        this.prev = prev;
    }

    // hash representation – serialize board ke String
    @Override public int hashCode() {
        return Arrays.deepHashCode(board);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State)) return false;
        State s = (State) o;
        return Arrays.deepEquals(board, s.board);
    }
}