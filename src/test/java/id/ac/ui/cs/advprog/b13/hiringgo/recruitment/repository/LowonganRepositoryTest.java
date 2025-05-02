package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LowonganRepositoryTest {

    @Autowired
    private LowonganRepository repository;

    @Test
    void testSaveAndFind() {
        Lowongan l = new Lowongan("Matkul A", "2024", "Genap", 2);
        repository.save(l);

        List<Lowongan> all = repository.findAll();

        assertEquals(1, all.size());
        assertEquals("Matkul A", all.get(0).getMataKuliah());
    }

    @Test
    void testDelete() {
        Lowongan l = new Lowongan("Matkul B", "2024", "Ganjil", 1);
        Lowongan saved = repository.save(l);

        repository.deleteById(saved.getId());

        Optional<Lowongan> found = repository.findById(saved.getId());
        assertTrue(found.isEmpty());
    }
}
