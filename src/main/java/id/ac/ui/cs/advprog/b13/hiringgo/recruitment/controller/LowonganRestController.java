package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.LowonganService;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.PendaftaranLowonganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lowongan")
@RequiredArgsConstructor
public class LowonganRestController {

    private final LowonganService lowonganService;
    private final PendaftaranLowonganService pendaftaranLowonganService;

    @GetMapping("/list")
    public List<Lowongan> listLowongan() {
        return lowonganService.findAll();
    }

    @PostMapping("/create")
    public Lowongan createLowongan(@RequestBody Lowongan newLowongan) {
        return lowonganService.save(newLowongan);
    }

    @GetMapping("/{id}")
    public Lowongan getLowongan(@PathVariable Long id) {
        return lowonganService.findById(id)
                .orElseThrow(() -> new RuntimeException("Lowongan not found"));
    }

    @PutMapping("/edit/{id}")
    public Lowongan editLowongan(@PathVariable Long id, @RequestBody Lowongan editedLowongan) {
        Lowongan existing = lowonganService.findById(id)
                .orElseThrow(() -> new RuntimeException("Lowongan not found"));

        existing.setMataKuliah(editedLowongan.getMataKuliah());
        existing.setTahunAjaran(editedLowongan.getTahunAjaran());
        existing.setSemester(editedLowongan.getSemester());
        existing.setJumlahAsistenDibutuhkan(editedLowongan.getJumlahAsistenDibutuhkan());

        return lowonganService.save(existing);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLowongan(@PathVariable Long id) {
        lowonganService.deleteById(id);
    }

    @PostMapping("/daftar/{id}")
    public PendaftaranLowongan daftarLowongan(
            @PathVariable Long id,
            @RequestParam double ipk,
            @RequestParam int jumlahSks) {

        if (ipk < 0 || ipk > 4) {
            throw new IllegalArgumentException("IPK harus antara 0 dan 4");
        }

        Lowongan lowongan = lowonganService.findById(id)
                .orElseThrow(() -> new RuntimeException("Lowongan not found"));

        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        pendaftaran.setIpk(ipk);
        pendaftaran.setJumlahSks(jumlahSks);
        pendaftaran.setLowongan(lowongan);

        pendaftaranLowonganService.save(pendaftaran);

        lowongan.setJumlahAsistenMendaftar(lowongan.getJumlahAsistenMendaftar() + 1);
        lowonganService.save(lowongan);

        return pendaftaran;
    }
}
