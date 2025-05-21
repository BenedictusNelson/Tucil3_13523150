import java.util.*;

class Board {
    final int R, C;
    private int exitRow = -1, exitCol = -1;
    
    int getExitRow() { 
        return exitRow; 
    }
    
    int getExitCol() { 
        return exitCol; 
    }

    Board(int R, int C) { 
        this.R = R; this.C = C; 
    }

    void setExit(int r, int c) { 
        this.exitRow = r; this.exitCol = c; 
    }    
    boolean isGoal(State s) {
        // Handle the case where K is outside the board boundary
        if (exitCol >= C) {
            // For horizontal P pieces adjacent to the right edge
            Piece p = s.pieces.get('P');
            if (p.ori == Orientation.HORIZONTAL) {
                int pTailRow = s.primaryRow;
                int pTailCol = s.primaryCol + p.length - 1;
                return pTailRow == exitRow && pTailCol == C - 1;
            }
            return false;
        } else if (exitRow >= R) {
            // For vertical P pieces adjacent to the bottom edge
            Piece p = s.pieces.get('P');
            if (p.ori == Orientation.VERTICAL) {
                int pTailRow = s.primaryRow + p.length - 1;
                int pTailCol = s.primaryCol;
                return pTailCol == exitCol && pTailRow == R - 1;
            }
            return false;
        } else {
            // Normal case where K is inside the board
            char[][] board = s.board;
            return board[exitRow][exitCol] == 'P';
        }
    }

    List<State> expand(State s) {
        List<State> res = new ArrayList<>();
        char[][] board = s.board;
        for (Map.Entry<Character, Piece> entry : s.pieces.entrySet()) {
            char id = entry.getKey();
            Piece pc = entry.getValue();
            // cari posisi head piece (paling kiri/atas) di board
            int headR = -1, headC = -1;
            outer:
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (board[r][c] == id) { 
                        headR = r; headC = c; break outer; 
                    }
                }
            }
            // coba gerak -1 dan +1 pada orientasinya
            if (pc.ori == Orientation.HORIZONTAL) {
                // ke kiri
                int cLeft = headC - 1;
                if (cLeft >= 0 && board[headR][cLeft] == '.') {
                    res.add(makeMove(s, id, headR, headC, Direction.LEFT));
                }
                // ke kanan
                int tailC = headC + pc.length - 1;
                int cRight = tailC + 1;
                if (cRight < C && board[headR][cRight] == '.') {
                    res.add(makeMove(s, id, headR, headC, Direction.RIGHT));
                }                // special: primary piece menuju exit
                if (id == 'P') {
                    // Check if piece can move right to reach exit
                    if (headR == exitRow && cRight == exitCol) {
                        res.add(makeMove(s, id, headR, headC, Direction.RIGHT));
                    }
                    // Check if piece can move left to reach exit
                    if (headR == exitRow && cLeft == exitCol && cLeft >= 0) {
                        res.add(makeMove(s, id, headR, headC, Direction.LEFT));
                    }
                }
            } else { // VERTICAL
                // ke atas
                int rUp = headR - 1;
                if (rUp >= 0 && board[rUp][headC] == '.') {
                    res.add(makeMove(s, id, headR, headC, Direction.UP));
                }
                // ke bawah
                int tailR = headR + pc.length - 1;
                int rDown = tailR + 1;
                if (rDown < R && board[rDown][headC] == '.') {
                    res.add(makeMove(s, id, headR, headC, Direction.DOWN));
                }                if (id == 'P') {
                    // Check if piece can move down to reach exit
                    if (headC == exitCol && rDown == exitRow) {
                        res.add(makeMove(s, id, headR, headC, Direction.DOWN));
                    }
                    // Check if piece can move up to reach exit
                    if (headC == exitCol && rUp == exitRow && rUp >= 0) {
                        res.add(makeMove(s, id, headR, headC, Direction.UP));
                    }
                }
            }
        }
        return res;
    }

    private State makeMove(State s, char id, int headR, int headC, Direction dir) {
        Piece pc = s.pieces.get(id);
        char[][] nb = deepCopy(s.board);
        // hapus piece dari board
        for (int i = 0; i < pc.length; i++) {
            int r = headR + (pc.ori == Orientation.VERTICAL ? i : 0);
            int c = headC + (pc.ori == Orientation.HORIZONTAL ? i : 0);
            nb[r][c] = '.';
        }
        // posisi baru head
        int newHeadR = headR + dir.dr;
        int newHeadC = headC + dir.dc;
        // tempatkan kembali piece
        for (int i = 0; i < pc.length; i++) {
            int r = newHeadR + (pc.ori == Orientation.VERTICAL ? i : 0);
            int c = newHeadC + (pc.ori == Orientation.HORIZONTAL ? i : 0);
            nb[r][c] = id;
        }
        // jika primary keluar, tandai K dengan P lalu isGoal akan succeed pada next loop
        return new State(nb, s.pieces, id == 'P' ? newHeadR : s.primaryRow,
                                      id == 'P' ? newHeadC : s.primaryCol,
                                      s.g + 1, new Move(id, dir), s);
    }

    private char[][] deepCopy(char[][] src) {
        char[][] dst = new char[src.length][];
        for (int i = 0; i < src.length; i++) dst[i] = Arrays.copyOf(src[i], src[i].length);
        return dst;
    }
}

// Heuristic Interface
interface Heuristic {
    int estimate(State s);
    default String name() { 
        return this.getClass().getSimpleName(); 
    }
}