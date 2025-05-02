package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.PendaftaranLowonganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
