class Move {
    final char id;
    final Direction dir;
    Move(char id, Direction dir) { this.id = id; this.dir = dir; }
    @Override public String toString() { return id + "-" + dir.text; }
}

enum Direction {
    UP(-1, 0, "atas"), DOWN(1, 0, "bawah"), LEFT(0, -1, "kiri"), RIGHT(0, 1, "kanan");
    final int dr, dc; final String text;
    Direction(int dr, int dc, String t) { this.dr = dr; this.dc = dc; this.text = t; }
}