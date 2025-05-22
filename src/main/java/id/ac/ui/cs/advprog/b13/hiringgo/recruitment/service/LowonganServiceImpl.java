package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.LowonganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LowonganServiceImpl implements LowonganService {

    private final LowonganRepository lowonganRepository;

    @Override
    public Lowongan save(Lowongan lowongan) {
        return lowonganRepository.save(lowongan);
    }

    @Override
    public Optional<Lowongan> findById(Long id) {
        return lowonganRepository.findById(id);
    }

    @Override
    public List<Lowongan> findAll() {
        return lowonganRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        lowonganRepository.deleteById(id);
    }

    @Override
    public List<Lowongan> findByCreatedBy(Long userId) {
        return lowonganRepository.findByCreatedBy(userId);
    }

}
