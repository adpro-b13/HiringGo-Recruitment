package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import java.util.concurrent.CompletableFuture;


import java.util.List;
import java.util.Optional;

public interface LowonganService {
    Lowongan save(Lowongan lowongan);
    Optional<Lowongan> findById(Long id);
    CompletableFuture<List<Lowongan>> findAll();

    void deleteById(Long id);
    List<Lowongan> findByCreatedBy(Long userId);

}
