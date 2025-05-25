package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.PendaftaranLowonganRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PendaftaranLowonganServiceImplTest {

    @Mock
    private PendaftaranLowonganRepository repository;

    @InjectMocks
    private PendaftaranLowonganServiceImpl service;

    @Test
    void testSave() {

        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        pendaftaran.setMahasiswaId(123L);

        when(repository.save(pendaftaran)).thenReturn(pendaftaran);

        PendaftaranLowongan saved = service.save(pendaftaran);

        assertNotNull(saved);
        assertEquals(pendaftaran, saved);
        verify(repository).save(pendaftaran);
    }

    @Test
    void testFindByLowonganId() {

        Long lowonganId = 1L;
        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        pendaftaran.setId(1L);

        List<PendaftaranLowongan> list = List.of(pendaftaran);
        when(repository.findByLowonganId(lowonganId)).thenReturn(list);

        List<PendaftaranLowongan> result = service.findByLowongan(lowonganId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(repository).findByLowonganId(lowonganId);
    }

    @Test
    void testSaveAsync_Success() throws ExecutionException, InterruptedException {

        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        pendaftaran.setMahasiswaId(456L);
        pendaftaran.setIpk(3.5);

        when(repository.save(pendaftaran)).thenReturn(pendaftaran);

        CompletableFuture<PendaftaranLowongan> future = service.saveAsync(pendaftaran);
        PendaftaranLowongan result = future.get();

        assertNotNull(result);
        assertEquals(pendaftaran, result);
        assertEquals(456L, result.getMahasiswaId());
        verify(repository).save(pendaftaran);
    }

    @Test
    void testSaveAsync_Exception() {

        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        pendaftaran.setMahasiswaId(789L);

        RuntimeException exception = new RuntimeException("Database error");
        when(repository.save(any())).thenThrow(exception);

        CompletableFuture<PendaftaranLowongan> future = service.saveAsync(pendaftaran);

        assertTrue(future.isCompletedExceptionally());
        assertThrows(ExecutionException.class, future::get);
        verify(repository).save(pendaftaran);
    }

    @Test
    void testFindByLowonganAsync_Success() throws ExecutionException, InterruptedException {

        Long lowonganId = 2L;
        PendaftaranLowongan pendaftaran1 = new PendaftaranLowongan();
        pendaftaran1.setId(1L);
        pendaftaran1.setIpk(3.8);

        PendaftaranLowongan pendaftaran2 = new PendaftaranLowongan();
        pendaftaran2.setId(2L);
        pendaftaran2.setIpk(3.2);

        List<PendaftaranLowongan> list = List.of(pendaftaran1, pendaftaran2);
        when(repository.findByLowonganId(lowonganId)).thenReturn(list);

        CompletableFuture<List<PendaftaranLowongan>> future = service.findByLowonganAsync(lowonganId);
        List<PendaftaranLowongan> result = future.get();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(3.8, result.get(0).getIpk());
        assertEquals(3.2, result.get(1).getIpk());
        verify(repository).findByLowonganId(lowonganId);
    }

    @Test
    void testFindByLowonganAsync_EmptyResult() throws ExecutionException, InterruptedException {

        Long lowonganId = 999L;
        when(repository.findByLowonganId(lowonganId)).thenReturn(List.of());

        CompletableFuture<List<PendaftaranLowongan>> future = service.findByLowonganAsync(lowonganId);
        List<PendaftaranLowongan> result = future.get();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findByLowonganId(lowonganId);
    }

    @Test
    void testFindByLowonganAsync_Exception() {

        Long lowonganId = 3L;
        RuntimeException exception = new RuntimeException("Database connection failed");
        when(repository.findByLowonganId(lowonganId)).thenThrow(exception);

        CompletableFuture<List<PendaftaranLowongan>> future = service.findByLowonganAsync(lowonganId);

        assertTrue(future.isCompletedExceptionally());
        assertThrows(ExecutionException.class, future::get);
        verify(repository).findByLowonganId(lowonganId);
    }
}