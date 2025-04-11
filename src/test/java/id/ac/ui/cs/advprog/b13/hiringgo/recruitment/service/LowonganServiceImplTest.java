package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.LowonganRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        Lowongan lowongan = new Lowongan("PBO", "2024/2025", "Genap", 2);
        lowongan.setId(1L);

        when(repository.save(any())).thenReturn(lowongan);

        Lowongan result = service.save(lowongan);

        assertEquals(lowongan, result);
        verify(repository, times(1)).save(lowongan);
    }

    @Test
    void testFindAll() {
        Lowongan l1 = new Lowongan("PBO", "2024/2025", "Genap", 2);
        l1.setId(1L);
        l1.setJumlahAsistenMendaftar(0);
        l1.setJumlahAsistenDiterima(0);

        Lowongan l2 = new Lowongan("Algo", "2024/2025", "Ganjil", 1);
        l2.setId(2L);
        l2.setJumlahAsistenMendaftar(0);
        l2.setJumlahAsistenDiterima(0);

        List<Lowongan> expected = List.of(l1, l2);

        when(repository.findAll()).thenReturn(expected);

        List<Lowongan> result = service.findAll();

        assertEquals(expected, result);
        verify(repository, times(1)).findAll();
    }
}
