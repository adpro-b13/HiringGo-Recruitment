package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LowonganTest {

    @Test
    void testLowonganConstructorAndGetters() {
        Lowongan lowongan = new Lowongan("Matkul A", "2024/2025", "Genap", 3);

        assertEquals("Matkul A", lowongan.getMataKuliah());
        assertEquals("2024/2025", lowongan.getTahunAjaran());
        assertEquals("Genap", lowongan.getSemester());
        assertEquals(3, lowongan.getJumlahAsistenDibutuhkan());
        assertEquals(0, lowongan.getJumlahAsistenMendaftar());
        assertEquals(0, lowongan.getJumlahAsistenDiterima());
    }

    @Test
    void testSetters() {
        Lowongan lowongan = new Lowongan();
        lowongan.setMataKuliah("Matkul B");
        lowongan.setTahunAjaran("2023/2024");
        lowongan.setSemester("Ganjil");
        lowongan.setJumlahAsistenDibutuhkan(5);

        assertEquals("Matkul B", lowongan.getMataKuliah());
        assertEquals("2023/2024", lowongan.getTahunAjaran());
        assertEquals("Ganjil", lowongan.getSemester());
        assertEquals(5, lowongan.getJumlahAsistenDibutuhkan());
    }
}
