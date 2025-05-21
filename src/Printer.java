import java.util.*;
import java.io.*;

class Printer {
    private static final String RESET = "\u001B[0m";
    private static final String RED   = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE  = "\u001B[34m";

    static void printSolution(Solver.Result res) {
        List<State> path = new ArrayList<>();
        for (State cur = res.goal; cur != null; cur = cur.prev) path.add(cur);
        Collections.reverse(path);
        render(path, System.out, true); 
    }
    
    static void saveSolution(Solver.Result res, String fileName) throws IOException {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            // Convert result to path list, similar to printSolution
            List<State> path = new ArrayList<>();
            for (State cur = res.goal; cur != null; cur = cur.prev) path.add(cur);
            Collections.reverse(path);
            render(path, pw, false);          // false = tanpa warna
        }
    }
    
    private static void render(List<State> path, Appendable out, boolean colored) { 
        append(out, "\n== Urutan Gerakan ==\nPapan Awal\n"); 
        printBoard(out, path.get(0).board, null, colored); 
        for (int i = 1; i < path.size(); i++) { 
            State s = path.get(i); 
            append(out, String.format("\nGerakan %d: %s\n", i, s.move)); 
            printBoard(out, s.board, s.move, colored); 
        } 
    }
    
    private static void printBoard(Appendable out, char[][] board, 
                                   Move highlight, boolean colored) {
        for (char[] row : board) {
            for (char ch : row) {
                if (colored) {
                    if (ch == 'P')              
                        append(out, RED + ch + RESET);
                    else if (ch == 'K')         
                        append(out, GREEN + ch + RESET);
                    else if (highlight != null && ch == highlight.id)                          
                        append(out, BLUE + ch + RESET);
                    else                       
                        append(out, ch);
                } else {
                    append(out, ch);
                }
            }
            append(out, '\n');
        }
    }
    
    private static void append(Appendable a, Object s) {
        try { 
            a.append(String.valueOf(s)); 
        } catch (IOException e) {
            /* ignore */ 
        }
    }
}
