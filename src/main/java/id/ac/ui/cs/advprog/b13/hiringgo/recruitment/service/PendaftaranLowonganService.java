package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import java.util.List;

public interface PendaftaranLowonganService {

    PendaftaranLowongan save(PendaftaranLowongan pendaftaran);

    List<PendaftaranLowongan> findByLowongan(Long lowonganId);
}
