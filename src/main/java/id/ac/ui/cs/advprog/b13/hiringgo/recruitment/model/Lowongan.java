package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "lowongan",
        uniqueConstraints = @UniqueConstraint(columnNames = {"mataKuliah", "tahunAjaran", "semester"})
)
public class Lowongan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mataKuliah;
    private String tahunAjaran;
    private String semester;
    private int jumlahAsistenDibutuhkan;

    private int jumlahAsistenMendaftar = 0;
    private int jumlahAsistenDiterima = 0;

    public Lowongan() {
    }

    public Lowongan(String mataKuliah, String tahunAjaran, String semester, int jumlahAsistenDibutuhkan) {
        this.mataKuliah = mataKuliah;
        this.tahunAjaran = tahunAjaran;
        this.semester = semester;
        this.jumlahAsistenDibutuhkan = jumlahAsistenDibutuhkan;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getJumlahAsistenDibutuhkan() {
        return jumlahAsistenDibutuhkan;
    }

    public void setJumlahAsistenDibutuhkan(int jumlahAsistenDibutuhkan) {
        this.jumlahAsistenDibutuhkan = jumlahAsistenDibutuhkan;
    }

    public int getJumlahAsistenMendaftar() {
        return jumlahAsistenMendaftar;
    }

    public int getJumlahAsistenDiterima() {
        return jumlahAsistenDiterima;
    }

    public Long getId() {
        return id;
    }
}
