package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PendaftaranLowonganService {

    PendaftaranLowongan save(PendaftaranLowongan pendaftaran);

    List<PendaftaranLowongan> findByLowongan(Long lowonganId);
    Optional<PendaftaranLowongan> findById(Long id);
    long countAcceptedByLowonganId(Long lowonganId);
    List<PendaftaranLowongan> findByMahasiswaId(Long mahasiswaId);

    CompletableFuture<PendaftaranLowongan> saveAsync(PendaftaranLowongan pendaftaran);
    CompletableFuture<List<PendaftaranLowongan>> findByLowonganAsync(Long lowonganId);
}
