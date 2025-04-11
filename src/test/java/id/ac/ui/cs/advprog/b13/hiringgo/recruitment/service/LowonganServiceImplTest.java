package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.LowonganRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LowonganServiceImplTest {

    private LowonganRepository repository;
    private LowonganServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(LowonganRepository.class);
        service = new LowonganServiceImpl(repository);
    }

    @Test
    void testCreate() {
        Lowongan lowongan = new Lowongan();
        lowongan.setId(UUID.randomUUID());
        lowongan.setMatakuliah("PBO");
        lowongan.setTahunAjaran("2024/2025");
        lowongan.setSemester("Genap");
        lowongan.setKuota(2);

        when(repository.save(any())).thenReturn(lowongan);

        Lowongan result = service.create(lowongan);

        assertEquals(lowongan, result);
        verify(repository, times(1)).save(lowongan);
    }

    @Test
    void testFindAll() {
        List<Lowongan> expected = List.of(
                new Lowongan(UUID.randomUUID(), "PBO", "2024/2025", "Genap", 2, 0, 0),
                new Lowongan(UUID.randomUUID(), "Algo", "2024/2025", "Ganjil", 1, 0, 0)
        );

        when(repository.findAll()).thenReturn(expected);

        List<Lowongan> result = service.findAll();

        assertEquals(expected, result);
        verify(repository, times(1)).findAll();
    }
}
