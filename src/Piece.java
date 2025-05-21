class Piece {
    final char id;                 // character symbol (A,B,C, …, P)
    final Orientation ori;         // horizontal / vertical
    final int length;              // 2 atau 3 (default)

    Piece(char id, Orientation ori, int length) {
        this.id = id;
        this.ori = ori;
        this.length = length;
    }
}

enum Orientation { HORIZONTAL, VERTICAL }
