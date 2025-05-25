package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.LowonganService;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.PendaftaranLowonganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.Claims;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.security.JwtTokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.PendaftaranLowonganService;
import java.util.concurrent.CompletableFuture;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.template.DosenLowonganAction;




import java.util.List;
import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/api/lowongan")
@RequiredArgsConstructor
public class LowonganRestController {
    private final LowonganService lowonganService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PendaftaranLowonganService pendaftaranLowonganService;

    @GetMapping("/my")
    @PreAuthorize("hasRole('ROLE_DOSEN')")
    public List<Lowongan> myLowongan(HttpServletRequest request) {
        Long userId = jwtTokenProvider
                .getAllClaimsFromToken(request.getHeader("Authorization").substring(7))
                .get("userId", Integer.class).longValue();

        return lowonganService.findByCreatedBy(userId);
    }

    @GetMapping("/list")
    public CompletableFuture<List<Lowongan>> listLowongan() {
        return lowonganService.findAll();
    }



    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_DOSEN')")
    public Lowongan createLowongan(@RequestBody Lowongan newLowongan,
                                   HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(token);
        Long userId = claims.get("userId", Integer.class).longValue();

        if (!"Ganjil".equalsIgnoreCase(newLowongan.getSemester()) &&
                !"Genap".equalsIgnoreCase(newLowongan.getSemester())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Semester harus 'Ganjil' atau 'Genap'");
        }

        if (!newLowongan.getTahunAjaran().matches("\\d{4}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tahun ajaran harus dalam format 'YYYY'");
        }

        newLowongan.setCreatedBy(userId);
        return lowonganService.save(newLowongan);
    }



    @GetMapping("/{id}")
    public Lowongan getLowongan(@PathVariable Long id) {
        return lowonganService.findById(id)
                .orElseThrow(() -> new RuntimeException("Lowongan not found"));
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_DOSEN')")
    public Lowongan editLowongan(@PathVariable Long id,
                                 @RequestBody Lowongan edited,
                                 HttpServletRequest request) {

        return new DosenLowonganAction<Lowongan>() {
            @Override
            protected Lowongan doAction(Lowongan existing) {

                if (!"Ganjil".equalsIgnoreCase(edited.getSemester()) &&
                        !"Genap".equalsIgnoreCase(edited.getSemester())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Semester harus 'Ganjil' atau 'Genap'");
                }

                if (!edited.getTahunAjaran().matches("\\d{4}")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tahun ajaran harus dalam format 'YYYY'");
                }

                existing.setMataKuliah(edited.getMataKuliah());
                existing.setSemester(edited.getSemester());
                existing.setTahunAjaran(edited.getTahunAjaran());
                existing.setJumlahAsistenDibutuhkan(edited.getJumlahAsistenDibutuhkan());

                return lowonganService.save(existing);
            }
        }.execute(id, request, lowonganService, jwtTokenProvider);
    }



    @GetMapping("/status")
    @PreAuthorize("hasRole('ROLE_MAHASISWA')")
    public List<PendaftaranLowongan> lihatStatusLamaran(HttpServletRequest request) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(request.getHeader("Authorization").substring(7));
        Long mahasiswaId = claims.get("userId", Integer.class).longValue();

        return pendaftaranLowonganService.findByMahasiswaId(mahasiswaId);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_DOSEN')")
    public void deleteLowongan(@PathVariable Long id, HttpServletRequest request) {
        new DosenLowonganAction<Void>() {
            @Override
            protected Void doAction(Lowongan lowongan) {
                lowonganService.deleteById(lowongan.getId());
                return null;
            }
        }.execute(id, request, lowonganService, jwtTokenProvider);
    }


    @PostMapping("/daftar/{id}")
    @PreAuthorize("hasRole('ROLE_MAHASISWA')")
    public PendaftaranLowongan daftarLowongan(@PathVariable Long id,
                                              @RequestParam double ipk,
                                              @RequestParam int jumlahSks,
                                              HttpServletRequest request) {

        if (ipk < 0 || ipk > 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IPK harus antara 0 dan 4");
        }

        Claims claims = jwtTokenProvider.getAllClaimsFromToken(request.getHeader("Authorization").substring(7));
        Long mahasiswaId = claims.get("userId", Integer.class).longValue();

        Lowongan lowongan = lowonganService.findById(id)
                .orElseThrow(() -> new RuntimeException("Lowongan not found"));

        if (pendaftaranLowonganService.existsByLowonganIdAndMahasiswaId(id, mahasiswaId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anda sudah melamar lowongan ini");
        }

        PendaftaranLowongan pendaftaran = new PendaftaranLowongan();
        pendaftaran.setIpk(ipk);
        pendaftaran.setJumlahSks(jumlahSks);
        pendaftaran.setLowongan(lowongan);
        pendaftaran.setMahasiswaId(mahasiswaId);

        pendaftaranLowonganService.save(pendaftaran);

        lowongan.setJumlahAsistenMendaftar(lowongan.getJumlahAsistenMendaftar() + 1);
        lowonganService.save(lowongan);

        return pendaftaran;
    }


    @GetMapping("/pelamar/{id}")
    @PreAuthorize("hasRole('ROLE_DOSEN')")
    public List<PendaftaranLowongan> lihatPelamar(@PathVariable Long id,
                                                  HttpServletRequest request) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(request.getHeader("Authorization").substring(7));
        Long userId = claims.get("userId", Integer.class).longValue();

        Lowongan lowongan = lowonganService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lowongan not found"));

        if (!lowongan.getCreatedBy().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the creator of this Lowongan");
        }

        return pendaftaranLowonganService.findByLowongan(id);
    }

    @PutMapping("/pelamar/{pendaftaranId}/terima")
    @PreAuthorize("hasRole('ROLE_DOSEN')")
    public PendaftaranLowongan terimaPelamar(@PathVariable Long pendaftaranId, HttpServletRequest request) {
        return prosesPelamar(pendaftaranId, request, "DITERIMA");
    }

    @PutMapping("/pelamar/{pendaftaranId}/tolak")
    @PreAuthorize("hasRole('ROLE_DOSEN')")
    public PendaftaranLowongan tolakPelamar(@PathVariable Long pendaftaranId, HttpServletRequest request) {
        return prosesPelamar(pendaftaranId, request, "DITOLAK");
    }

    private PendaftaranLowongan prosesPelamar(Long pendaftaranId, HttpServletRequest request, String status) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(request.getHeader("Authorization").substring(7));
        Long userId = claims.get("userId", Integer.class).longValue();

        PendaftaranLowongan pendaftaran = pendaftaranLowonganService.findById(pendaftaranId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pendaftaran not found"));

        Lowongan lowongan = pendaftaran.getLowongan();

        if (!lowongan.getCreatedBy().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the creator of this Lowongan");
        }

        if ("DITERIMA".equals(pendaftaran.getStatus()) && "DITERIMA".equals(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mahasiswa sudah diterima");
        }

        if ("DITERIMA".equals(status)) {
            long diterima = pendaftaranLowonganService.countAcceptedByLowonganId(lowongan.getId());
            if (diterima >= lowongan.getJumlahAsistenDibutuhkan()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kuota sudah penuh");
            }

            lowongan.setJumlahAsistenDiterima(lowongan.getJumlahAsistenDiterima() + 1);
            lowonganService.save(lowongan);
        }

        pendaftaran.setStatus(status);
        return pendaftaranLowonganService.save(pendaftaran);
    }







}
