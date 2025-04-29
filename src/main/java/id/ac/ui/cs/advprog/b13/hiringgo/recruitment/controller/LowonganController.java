package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.LowonganService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lowongan")
@RequiredArgsConstructor  //
public class LowonganController {

    private final LowonganService lowonganService;
    @GetMapping("/list")
    public String listLowongan(Model model) {
        // Dummy content for test to pass
        model.addAttribute("lowonganList", lowonganService.findAll());
        model.addAttribute("message", "Daftar Lowongan");
        return "lowonganList"; // Corresponds to lowonganList.html
    }

    @GetMapping("/create")
    public String createLowonganForm(Model model) {
        // Dummy content
        model.addAttribute("formTitle", "Form Buat Lowongan");
        return "createLowongan"; // Corresponds to createLowongan.html
    }

    @PostMapping("/create")
    public String createLowongan(@RequestParam String matakuliah,
                                 @RequestParam String tahunAjaran,
                                 @RequestParam String semester,
                                 @RequestParam int jumlahAsisten) {
        // Simulate saving to DB
        Lowongan lowongan = new Lowongan();
        lowongan.setMataKuliah(matakuliah);
        lowongan.setTahunAjaran(tahunAjaran);
        lowongan.setSemester(semester);
        lowongan.setJumlahAsistenDibutuhkan(jumlahAsisten);

        lowonganService.save(lowongan);

        return "redirect:/lowongan/list";
    }
    @PostMapping("/delete/{id}")
    public String deleteLowongan(@PathVariable Long id) {
        lowonganService.deleteById(id);
        return "redirect:/lowongan/list";
    }

    @GetMapping("/edit/{id}")
    public String editLowonganForm(@PathVariable Long id, Model model) {
        Lowongan lowongan = lowonganService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lowongan Id:" + id));
        model.addAttribute("lowongan", lowongan);
        return "editLowongan";
    }

    @PostMapping("/edit/{id}")
    public String editLowongan(@PathVariable Long id,
                               @RequestParam String matakuliah,
                               @RequestParam String tahunAjaran,
                               @RequestParam String semester,
                               @RequestParam int jumlahAsisten) {
        Lowongan lowongan = lowonganService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lowongan Id:" + id));

        lowongan.setMataKuliah(matakuliah);
        lowongan.setTahunAjaran(tahunAjaran);
        lowongan.setSemester(semester);
        lowongan.setJumlahAsistenDibutuhkan(jumlahAsisten);

        lowonganService.save(lowongan);

        return "redirect:/lowongan/list";
    }

}
