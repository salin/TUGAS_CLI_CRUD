import java.io.*;
import java.util.*;

class Mahasiswa {
    String nim, nama, alamat;
    int semester, sks;
    double ipk;

    public Mahasiswa(String nim, String nama, String alamat, int semester, int sks, double ipk) {
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.semester = semester;
        this.sks = sks;
        this.ipk = ipk;
    }

    public String toCSV() {
        return nim + "," + nama + "," + alamat + "," + semester + "," + sks + "," + ipk;
    }

    @Override
    public String toString() {
        return nim + " | " + nama + " | " + alamat + " | Semester: " + semester + " | SKS: " + sks + " | IPK: " + ipk;
    }
}
