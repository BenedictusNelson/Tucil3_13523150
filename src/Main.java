import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Penggunaan : java Main <input_file.txt>");
            return;
        }
        String inFile = args[0];
        BoardParser parser = new BoardParser();
        State start = parser.parse(inFile);

        Board board = new Board(start.board.length, start.board[0].length);
        board.setExit(parser.getExitRow(), parser.getExitCol());
        Heuristics.setBoard(board);

        Scanner sc = new Scanner(System.in);
        Solver.Algorithm algo = askAlgorithm(sc);
        Heuristic heur   = (algo == Solver.Algorithm.UCS) ? Heuristics.ZERO : askHeuristic(sc);

        // *** output file name ***
        System.out.print("Nama file output (contoh: solution.txt): ");
        sc.nextLine();                       // buang newline sisa nextInt
        String outName = sc.nextLine().trim();
        if(outName.isEmpty()) outName = "solution.txt";

        long t0 = System.nanoTime();
        Solver.Result res = Solver.solve(start, board, algo, heur);
        long t1 = System.nanoTime();

        if (res == null) {
            System.out.println("Tidak ada solusi.");
            return;
        }
        // print stats
        System.out.println("\n== Statistik ==");
        System.out.printf("Algoritma        : %s\n", algo);
        System.out.printf("Heuristik        : %s\n", heur.name());
        System.out.printf("Node dikunjungi  : %d\n", res.visited);
        System.out.printf("Waktu eksekusi   : %.5f detik\n", (t1 - t0) / 1e9);

        // ----- cetak ke layar + simpan ke file -----
        Printer.printSolution(res);                 // ke layar berwarna
        Printer.saveSolution(res, outName);         // ke file txt
        System.out.println("Solusi disimpan ke " + outName);
    }

    private static Solver.Algorithm askAlgorithm(Scanner sc) {
        System.out.println("Pilih algoritma :\n1. Uniform Cost Search (UCS)\n2. Greedy Best First Search\n3. A* Search");
        int opt;
        do {
            System.out.print("> ");
            opt = sc.nextInt();
        } while (opt < 1 || opt > 3);

        Solver.Algorithm algo;
        switch (opt) {
            case 1:
                algo = Solver.Algorithm.UCS;
                break;
            case 2:
                algo = Solver.Algorithm.GREEDY;
                break;
            default:
                algo = Solver.Algorithm.ASTAR;
                break;
        }
        return algo;
    }

    private static Heuristic askHeuristic(Scanner sc) {
        System.out.println("Pilih heuristic :\n1. Blok penghalang (admissible)\n2. Manhattan distance (approx)\n3. Nol (non-informatif)");
        int opt;
        do {
            System.out.print("> ");
            opt = sc.nextInt();
        } while (opt < 1 || opt > 3);

        Heuristic heur;
        switch (opt) {
            case 1:
                heur = Heuristics.BLOCKING;
                break;
            case 2:
                heur = Heuristics.MANHATTAN;
                break;
            default:
                heur = Heuristics.ZERO;
                break;
        }
        return heur;
    }
}