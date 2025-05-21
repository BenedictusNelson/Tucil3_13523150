class Heuristics {
    // Static reference to the Board instance
    private static Board boardInstance;
    
    // Method to set the board reference
    public static void setBoard(Board board) {
        boardInstance = board;
    }
    
    static final Heuristic ZERO = new Heuristic() {
        public int estimate(State s) { return 0; }
        public String name() { return "Zero (tidak ada heuristik)"; }
    };
    static final Heuristic BLOCKING = new Heuristic() {
        public int estimate(State s) {
            Piece p = s.pieces.get('P');
            int row = s.primaryRow, colTail = s.primaryCol + p.length - 1;
            int dist = 0, blockers = 0;
            for (int c = colTail + 1; c < s.board[0].length; c++) {
                if (s.board[row][c] == '.') dist++;
                else if (s.board[row][c] == 'K') break;
                else { blockers++; }
            }
            return blockers * 2 + dist; // sederhana & admissible
        }
    };

    static final Heuristic MANHATTAN = s -> {
        // Check if board is set
        if (boardInstance == null) {
            throw new IllegalStateException("Papan belum diatur di kelas Heuristik. Panggil aturPapan() terlebih dahulu.");
        }
        
        Piece p = s.pieces.get('P');
        int relevantRow = (p.ori == Orientation.VERTICAL)
                            ? s.primaryRow + p.length - 1   // tail row
                            : s.primaryRow;
        int relevantCol = (p.ori == Orientation.HORIZONTAL)
                            ? s.primaryCol + p.length - 1   // tail col
                            : s.primaryCol;
        return Math.abs(relevantRow - boardInstance.getExitRow())
            + Math.abs(relevantCol - boardInstance.getExitCol());
    };
}