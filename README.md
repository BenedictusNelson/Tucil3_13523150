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
```
## ⚙ Requirement dan Instalasi

### Prasyarat
- **Java Development Kit (JDK) ≥ 8**  
  Unduh: https://jdk.java.net/  
- **Git**  
  Unduh: https://git-scm.com/downloads  

## 🚀 Cara Menjalankan Program

1. **Clone repositori**  
   ```bash
   git clone https://github.com/BenedictusNelson/Tucil3_13523150.git
   cd Tucil3_13523150p
   
2. **Compile program**
   ```bash
   javac -d bin src/*.java
   buat input file

3. **Jalankan program**
   ```bash
   java -cp bin Main test/input_file.txt
   
5. **Ikuti prompt**
  * Pilih algoritma (1 – UCS, 2 – Greedy, 3 – A*)
  * Jika memilih Greedy/A*: pilih heuristic (1 – blocking, 2 – Manhattan, 3 – nol)
  * Masukkan nama file output (default solution.txt)

    Hasil akan:
  * Ditampilkan di terminal dengan highlight warna ANSI (P = merah, K = hijau, piece bergerak = biru).
  * Disimpan ke file teks (tanpa warna ANSI).

## 🏆 Author
👤 Benedictus Nelson (13523150)

📧 Email: 13523150@std.stei.itb.ac.id
