import java.io.*;
import java.util.*;

public class MahasiswaCLI {
    private static final String FILE_NAME = "C:\\Users\\Asus\\IdeaProjects\\tugas_2\\src\\data_mahasiswa.csv";
    private static ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
    private static HashMap<String, Mahasiswa> mahasiswaMap = new HashMap<>();

    public static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("File tidak ditemukan, membuat file baru...");
            saveToFile();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 6) continue;

                try {
                    Mahasiswa mhs = new Mahasiswa(data[0], data[1], data[2],
                            Integer.parseInt(data[3]),
                            Integer.parseInt(data[4]),
                            Double.parseDouble(data[5]));
                    mahasiswaList.add(mhs);
                    mahasiswaMap.put(mhs.nim, mhs);
                } catch (NumberFormatException e) {
                    System.out.println("Format data salah, dilewati: " + Arrays.toString(data));
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }
    }

    public static void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write("NIM,Nama,Alamat,Semester,SKS,IPK");
            bw.newLine();
            for (Mahasiswa mhs : mahasiswaList) {
                bw.write(mhs.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data ke file!");
        }
    }

    public static void create(Scanner scanner) {
        System.out.println("============================================================================");
        System.out.printf("| %-10s | %-20s | %-15s | %-8s | %-5s | %-4s |\n",
                "NIM", "Nama", "Alamat", "Semester", "SKS", "IPK");
        System.out.println("============================================================================");
        for (Mahasiswa mhs : mahasiswaList) {
            System.out.printf("| %-10s | %-20s | %-15s | %-8d | %-5d | %-4.2f |\n",
                    mhs.nim, mhs.nama, mhs.alamat, mhs.semester, mhs.sks, mhs.ipk);
        }
        System.out.print("NIM: ");
        String nim = scanner.nextLine();
        if (mahasiswaMap.containsKey(nim)) {
            System.out.println("Mahasiswa dengan NIM ini sudah ada!");
            return;
        }

        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Alamat: ");
        String alamat = scanner.nextLine();

        int semester = getIntInput(scanner, "Semester: ");
        int sks = getIntInput(scanner, "SKS: ");
        double ipk = getDoubleInput(scanner, "IPK: ");


        Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
        mahasiswaList.add(mhs);
        mahasiswaMap.put(nim, mhs);
        System.out.println("Data berhasil ditambahkan!");

        saveToFile();

    }


    public static void read() {
        if (mahasiswaList.isEmpty()) {
            System.out.println("Tidak ada data mahasiswa.");
            return;
        }
        System.out.println("============================================================================");
        System.out.printf("| %-10s | %-20s | %-15s | %-8s | %-5s | %-4s |\n",
                "NIM", "Nama", "Alamat", "Semester", "SKS", "IPK");
        System.out.println("============================================================================");
        for (Mahasiswa mhs : mahasiswaList) {
            System.out.printf("| %-10s | %-20s | %-15s | %-8d | %-5d | %-4.2f |\n",
                    mhs.nim, mhs.nama, mhs.alamat, mhs.semester, mhs.sks, mhs.ipk);
        }
    }

    public static void update(Scanner scanner) {
        System.out.println("============================================================================");
        System.out.printf("| %-10s | %-20s | %-15s | %-8s | %-5s | %-4s |\n",
                "NIM", "Nama", "Alamat", "Semester", "SKS", "IPK");
        System.out.println("============================================================================");
        for (Mahasiswa mhs : mahasiswaList) {
            System.out.printf("| %-10s | %-20s | %-15s | %-8d | %-5d | %-4.2f |\n",
                    mhs.nim, mhs.nama, mhs.alamat, mhs.semester, mhs.sks, mhs.ipk);
        }

        System.out.print("Masukkan NIM mahasiswa yang ingin diperbarui: ");
        String nimLama = scanner.nextLine();
        if (!mahasiswaMap.containsKey(nimLama)) {
            System.out.println("Mahasiswa tidak ditemukan!");
            return;
        }

        Mahasiswa mhs = mahasiswaMap.get(nimLama);
        System.out.print("Masukkan NIM baru: ");
        String nimBaru = scanner.nextLine();

        // Cek apakah NIM baru sudah ada, tetapi bukan NIM yang sedang diupdate
        if (!nimLama.equals(nimBaru) && mahasiswaMap.containsKey(nimBaru)) {
            System.out.println("NIM sudah digunakan oleh mahasiswa lain! Perubahan dibatalkan.");
            return;
        }

        System.out.print("Nama baru: ");
        mhs.nama = scanner.nextLine();
        System.out.print("Alamat baru: ");
        mhs.alamat = scanner.nextLine();
        mhs.semester = getIntInput(scanner, "Semester baru: ");
        mhs.sks = getIntInput(scanner, "SKS baru: ");
        mhs.ipk = getDoubleInput(scanner, "IPK baru: ");

        // Hapus NIM lama jika NIM berubah
        if (!nimLama.equals(nimBaru)) {
            mahasiswaMap.remove(nimLama);
            mhs.nim = nimBaru;
            mahasiswaMap.put(nimBaru, mhs);
        }

        // Perbarui dalam ArrayList
        for (int i = 0; i < mahasiswaList.size(); i++) {
            if (mahasiswaList.get(i).nim.equals(nimLama)) {
                mahasiswaList.set(i, mhs);
                break;
            }
        }

        System.out.println("Data berhasil diperbarui!");
        saveToFile();
    }

    public static void delete(Scanner scanner) {
        System.out.println("============================================================================");
        System.out.printf("| %-10s | %-20s | %-15s | %-8s | %-5s | %-4s |\n",
                "NIM", "Nama", "Alamat", "Semester", "SKS", "IPK");
        System.out.println("============================================================================");
        for (Mahasiswa mhs : mahasiswaList) {
            System.out.printf("| %-10s | %-20s | %-15s | %-8d | %-5d | %-4.2f |\n",
                    mhs.nim, mhs.nama, mhs.alamat, mhs.semester, mhs.sks, mhs.ipk);
        }
        System.out.print("Masukkan NIM mahasiswa yang ingin dihapus: ");
        String nim = scanner.nextLine();
        if (!mahasiswaMap.containsKey(nim)) {
            System.out.println("Mahasiswa tidak ditemukan!");
            return;
        }
        System.out.print("Apakah Anda yakin ingin menghapus data ini? (Y/N): ");
        String konfirmasi = scanner.nextLine().toUpperCase();
        if (konfirmasi.equals("Y")) {
            mahasiswaList.remove(mahasiswaMap.get(nim));
            mahasiswaMap.remove(nim);
            System.out.println("Data berhasil dihapus!");
            saveToFile();
        }
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! Harap masukkan angka.");
            }
        }
    }

    private static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! Harap masukkan angka desimal.");
            }
        }
    }
}
