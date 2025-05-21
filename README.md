# Puzzle Rush Hour Solver  
## Bahasa: Java

## Deskripsi Singkat
Program ini menyelesaikan **Puzzle Rush Hour** menggunakan algoritma pathfinding:
- **Uniform Cost Search (UCS)**
- **Greedy Best-First Search**
- **A\* Search**  
Bonus: pilihan heuristic (blocking pieces, Manhattan distance, nol) dan penyimpanan output berwarna di terminal serta ke file teks.

## 📁 Struktur Direktori
```bash
├── src/
│   ├── Main.java
│   ├── BoardParser.java
│   ├── State.java
│   ├── Piece.java
│   ├── Move.java
│   ├── Board.java
│   ├── Solver.java
│   ├── Heuristics.java
│   └── Printer.java
├── bin/
│   ├── Main.class
│   ├── BoardParser.class
│   ├── State.class
│   ├── Piece.class
│   └── dst
├── test/
│   ├── TestCase.txt      
│   └── solution.txt         
├── doc/
│   └── Laporan_Tucil3_13523150.pdf
└── README.md