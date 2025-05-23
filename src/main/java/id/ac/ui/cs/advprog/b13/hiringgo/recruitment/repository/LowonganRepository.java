package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;import java.util.List;



@Repository
public interface LowonganRepository extends JpaRepository<Lowongan, Long> {
    List<Lowongan> findByCreatedBy(Long userId);
}
