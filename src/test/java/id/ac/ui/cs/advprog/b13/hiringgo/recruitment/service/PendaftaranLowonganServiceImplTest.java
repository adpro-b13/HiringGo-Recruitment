package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.PendaftaranLowonganRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class PendaftaranLowonganServiceImplTest {

    @Mock
    private PendaftaranLowonganRepository repository;

    @InjectMocks
    private PendaftaranLowonganServiceImpl service;

    @Test
    void testSave() {
        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();

        when(repository.save(pendaftaran)).thenReturn(pendaftaran);

        PendaftaranLowongan saved = service.save(pendaftaran);

        assertNotNull(saved);
        assertEquals(pendaftaran, saved);
    }

    @Test
    void testFindByLowonganId() {
        Long id = 1L;
        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();

        pendaftaran.setId(1L);

        List<PendaftaranLowongan> list = List.of(pendaftaran);
        when(repository.findByLowonganId(id)).thenReturn(list);

        List<PendaftaranLowongan> result = service.findByLowongan(id);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
    }
}
