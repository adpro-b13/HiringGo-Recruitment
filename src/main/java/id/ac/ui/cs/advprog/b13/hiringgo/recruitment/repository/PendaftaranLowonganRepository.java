package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface PendaftaranLowonganRepository extends JpaRepository<PendaftaranLowongan, Long> {
    List<PendaftaranLowongan> findByLowonganId(Long lowonganId);
    long countByLowonganIdAndStatus(Long lowonganId, String status);
    @Async
    CompletableFuture<List<PendaftaranLowongan>> findByLowongan_Id(Long lowonganId);
    List<PendaftaranLowongan> findByMahasiswaId(Long mahasiswaId);
    boolean existsByLowonganIdAndMahasiswaId(Long lowonganId, Long mahasiswaId);


}
