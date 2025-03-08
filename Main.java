import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MahasiswaCLI.loadFromFile();
        Scanner scanner = new Scanner(System.in);
        String pilihan;

        while (true) {
            System.out.println("===============");
            System.out.println("| Pilih menu: |");
            System.out.println("| [C] : Create |");
            System.out.println("| [R] : Read   |");
            System.out.println("| [U] : Update |");
            System.out.println("| [D] : Delete |");
            System.out.println("| [X] : Exit   |");
            System.out.println("===============");
            System.out.print("Masukkan pilihan: ");
            pilihan = scanner.nextLine().toUpperCase();

            switch (pilihan) {
                case "C":
                    MahasiswaCLI.create(scanner);
                    break;
                case "R":
                    MahasiswaCLI.read();
                    break;
                case "U":
                    MahasiswaCLI.update(scanner);
                    break;
                case "D":
                    MahasiswaCLI.delete(scanner);
                    break;
                case "X":
                    System.out.print("Apakah Anda yakin ingin keluar? (Y/N): ");
                    String konfirmasi = scanner.nextLine().toUpperCase();
                    if (konfirmasi.equals("Y")) {
                        System.out.println("Program selesai");
                        scanner.close();
                        return;
                    }
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }
}
