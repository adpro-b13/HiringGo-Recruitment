package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.PendaftaranLowonganRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendaftaranLowonganServiceImpl implements PendaftaranLowonganService {

    private final PendaftaranLowonganRepository repository;

    @Override
    public PendaftaranLowongan save(PendaftaranLowongan pendaftaran) {
        return repository.save(pendaftaran);
    }

    @Override
    public List<PendaftaranLowongan> findByLowongan(Long lowonganId) {
        return repository.findByLowonganId(lowonganId);
    }
    
    @Async
    @Override
    public CompletableFuture<PendaftaranLowongan> saveAsync(PendaftaranLowongan pendaftaran) {
        try {
            log.info("Saving pendaftaran async for mahasiswa: {}", pendaftaran.getMahasiswaId());
            PendaftaranLowongan saved = repository.save(pendaftaran);
            return CompletableFuture.completedFuture(saved);
        } catch (Exception e) {
            log.error("Error saving pendaftaran: ", e);
            return CompletableFuture.failedFuture(e);
        }
    }
    
    @Async
    @Override
    public CompletableFuture<List<PendaftaranLowongan>> findByLowonganAsync(Long lowonganId) {
        try {
            List<PendaftaranLowongan> result = repository.findByLowonganId(lowonganId);
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            log.error("Error finding pendaftaran: ", e);
            return CompletableFuture.failedFuture(e);
        }
    }
}