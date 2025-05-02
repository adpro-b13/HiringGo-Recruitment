package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PendaftaranLowonganTest {

    @Test
    void testPendaftaranLowonganGetterSetter() {
        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        Lowongan lowongan = new Lowongan();

        pendaftaran.setId(1L);
        pendaftaran.setIpk(3.75);
        pendaftaran.setJumlahSks(110);
        pendaftaran.setLowongan(lowongan);

        assertEquals(1L, pendaftaran.getId());
        assertEquals(3.75, pendaftaran.getIpk());
        assertEquals(110, pendaftaran.getJumlahSks());
        assertEquals(lowongan, pendaftaran.getLowongan());
    }
}
