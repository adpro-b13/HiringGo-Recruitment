package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PendaftaranLowonganRepositoryTest {

    @Autowired
    private PendaftaranLowonganRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveAndFindByLowonganId() {
        Lowongan lowongan = new Lowongan("PBO", "2024/2025", "Genap", 2);
        entityManager.persist(lowongan);

        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        pendaftaran.setIpk(3.5);
        pendaftaran.setJumlahSks(90);
        pendaftaran.setLowongan(lowongan);

        repository.save(pendaftaran);
        List<PendaftaranLowongan> result = repository.findByLowonganId(lowongan.getId());

        assertEquals(1, result.size());
        assertEquals(3.5, result.get(0).getIpk());
    }
}
