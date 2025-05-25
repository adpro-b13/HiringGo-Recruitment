package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
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

    private Long createdBy;

    public Lowongan() {
    }

    public Lowongan(String mataKuliah, String tahunAjaran, String semester, int jumlahAsistenDibutuhkan) {
        this.mataKuliah = mataKuliah;
        this.tahunAjaran = tahunAjaran;
        this.semester = semester;
        this.jumlahAsistenDibutuhkan = jumlahAsistenDibutuhkan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { // Added setter
        this.id = id;
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

    public void setJumlahAsistenMendaftar(int jumlahAsistenMendaftar) { // Optional if needed
        this.jumlahAsistenMendaftar = jumlahAsistenMendaftar;
    }

    public int getJumlahAsistenDiterima() {
        return jumlahAsistenDiterima;
    }

    public void setJumlahAsistenDiterima(int jumlahAsistenDiterima) { // Optional if needed
        this.jumlahAsistenDiterima = jumlahAsistenDiterima;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
