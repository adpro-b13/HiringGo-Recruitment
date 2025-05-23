package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.LowonganRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
public class LowonganRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LowonganRepository lowonganRepository;

    private static final String HARDCODED_TOKEN =
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsInVzZXJJZCI6NCwibmFtYUxlbmdrYXAiOiJhIiwicm9sZXMiOiJST0xFX0RPU0VOIiwiaWF0IjoxNzQ3OTkwMzA1LCJleHAiOjE3NDgwNzY3MDV9.x0pVEJt-wpx7BJ8sq0t-F5Oe07tLcA1a0tTZ1s90_9h2awIUm8vlGiRf9Oehputz3ZrkR8kQhnTyNKlZ6YKvpg";

    @Test
    public void testCreateLowongan() throws Exception {
        String mataKuliah = "Pemrograman Lanjut " + System.currentTimeMillis();
        Lowongan newLowongan = new Lowongan(mataKuliah, "2024/2025", "Ganjil", 3);

        mvc.perform(post("/api/lowongan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", HARDCODED_TOKEN)
                        .content(objectMapper.writeValueAsString(newLowongan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mataKuliah").value(mataKuliah))
                .andExpect(jsonPath("$.tahunAjaran").value("2024/2025"))
                .andExpect(jsonPath("$.semester").value("Ganjil"))
                .andExpect(jsonPath("$.jumlahAsistenDibutuhkan").value(3));
    }

    @Test
    public void testGetAllLowongan() throws Exception {
        Lowongan l = new Lowongan("AI " + System.currentTimeMillis(), "2024/2025", "Genap", 5);
        l.setCreatedBy(4L);
        lowonganRepository.save(l);

        mvc.perform(get("/api/lowongan/list"))
                .andExpect(status().isOk());
    }



    @Test
    public void testUpdateLowongan() throws Exception {
        String mataKuliah = "AI " + System.currentTimeMillis();
        Lowongan saved = new Lowongan(mataKuliah, "2024/2025", "Genap", 5);
        saved.setCreatedBy(4L);
        saved = lowonganRepository.save(saved);

        saved.setMataKuliah("AI dan Data Science");
        saved.setJumlahAsistenDibutuhkan(7);

        mvc.perform(put("/api/lowongan/edit/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", HARDCODED_TOKEN)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mataKuliah").value("AI dan Data Science"))
                .andExpect(jsonPath("$.jumlahAsistenDibutuhkan").value(7));
    }

    @Test
    public void testDeleteLowongan() throws Exception {
        String mataKuliah = "Cloud Computing " + System.currentTimeMillis();
        Lowongan saved = new Lowongan(mataKuliah, "2024/2025", "Ganjil", 2);
        saved.setCreatedBy(4L);
        saved = lowonganRepository.save(saved);

        mvc.perform(delete("/api/lowongan/delete/" + saved.getId())
                        .header("Authorization", HARDCODED_TOKEN))
                .andExpect(status().isOk());
    }
}
