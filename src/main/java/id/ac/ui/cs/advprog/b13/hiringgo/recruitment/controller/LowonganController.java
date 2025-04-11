package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lowongan")
public class LowonganController {

    @GetMapping("/list")
    public String listLowongan(Model model) {
        // Dummy content for test to pass
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
        return "redirect:/lowongan/list";
    }
}
