import java.io.*;
import java.util.*;

class BoardParser {

    private int rows, cols;
    private int exitRow = -1, exitCol = -1;

    State parse(String filename) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {            
            StringTokenizer st = new StringTokenizer(br.readLine());
            rows = Integer.parseInt(st.nextToken());
            cols = Integer.parseInt(st.nextToken());

            // jumlah piece (tidak dipakai di parser)
            br.readLine();

            String line;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                lines.add(line.trim());
            }
        }

        if (lines.size() != rows)
            throw new IllegalArgumentException("Jumlah baris tidak sesuai. Ditemukan " + lines.size() + ", seharusnya " + rows);

        char[][] boardArr = new char[rows][cols];
        for (char[] row : boardArr) Arrays.fill(row, '.');

        Map<Character, List<int[]>> posMap = new HashMap<>();

        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);

            if (line.length() != cols && !(line.length() == cols + 1 && line.charAt(cols) == 'K'))
                throw new IllegalArgumentException("Panjang baris ke-" + (r + 1) + " = " + line.length() + " ≠ " + cols + " (kecuali K).");

            for (int c = 0; c < line.length(); c++) {
                char ch = line.charAt(c);

                if (ch == '.') continue;

                if (ch == 'K') {               // exit
                    exitRow = r;
                    exitCol = c;               // bisa = cols (di luar papan)
                    continue;
                }

                if (c >= cols)
                    throw new IllegalArgumentException("Karakter '" + ch + "' di luar papan pada baris " + (r + 1));

                boardArr[r][c] = ch;
                posMap.computeIfAbsent(ch, k -> new ArrayList<>()).add(new int[]{r, c});
            }
        }

        if (exitRow == -1)
            throw new IllegalArgumentException("Input tidak valid: karakter 'K' (pintu keluar) hilang.");
        if (!posMap.containsKey('P'))
            throw new IllegalArgumentException("Input tidak valid: karakter 'P' (primary piece) hilang.");

        /* ==== buat objek Piece ==== */
        Map<Character, Piece> pieces = new HashMap<>();
        int pRow = -1, pCol = -1;
        
        for (Map.Entry<Character, List<int[]>> e : posMap.entrySet()) {
            char id = e.getKey();
            List<int[]> pts = e.getValue();
            
            /* -- pastikan kotak‐kotak piece kontigu dan segaris -- */
            int len = pts.size();
            
            // Cek semua row sama (horizontal) atau semua col sama (vertikal)
            boolean sameRow = pts.stream().allMatch(p -> p[0] == pts.get(0)[0]);
            boolean sameCol = pts.stream().allMatch(p -> p[1] == pts.get(0)[1]);

            if (!(sameRow || sameCol)) {
                throw new IllegalArgumentException("Piece '" + id + "' tidak segaris");
            }

            // Sort posisi agar bisa dicek kontiguitas dengan benar
            pts.sort(Comparator.comparingInt((int[] p) -> p[0]).thenComparingInt(p -> p[1]));

            // Setelah sort, cek urutan kontigu
            for (int i = 1; i < pts.size(); i++) {
                int[] prev = pts.get(i - 1);
                int[] curr = pts.get(i);

                if (sameRow && curr[1] != prev[1] + 1)
                    throw new IllegalArgumentException("Piece '" + id + "' tidak kontigu");

                if (sameCol && curr[0] != prev[0] + 1)
                    throw new IllegalArgumentException("Piece '" + id + "' tidak kontigu");
            }
            
            Orientation ori = sameRow ? Orientation.HORIZONTAL : Orientation.VERTICAL;

            pieces.put(id, new Piece(id, ori, len));

            if (id == 'P') {
                pRow = pts.get(0)[0];
                pCol = pts.get(0)[1];
            }
        }

        return new State(boardArr, pieces, pRow, pCol, 0, null, null);
    }

    int getExitRow() { 
        return exitRow; 
    }
    int getExitCol() { 
        return exitCol; 
    }
}