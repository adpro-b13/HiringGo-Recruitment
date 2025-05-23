package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PendaftaranLowonganService {

    PendaftaranLowongan save(PendaftaranLowongan pendaftaran);

    List<PendaftaranLowongan> findByLowongan(Long lowonganId);

    CompletableFuture<PendaftaranLowongan> saveAsync(PendaftaranLowongan pendaftaran);
    CompletableFuture<List<PendaftaranLowongan>> findByLowonganAsync(Long lowonganId);
}
