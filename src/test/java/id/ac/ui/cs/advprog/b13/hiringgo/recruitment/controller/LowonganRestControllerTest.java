package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.LowonganRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LowonganRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LowonganRepository lowonganRepository;

    @Test
    public void testCreateLowongan() throws Exception {
        Lowongan newLowongan = new Lowongan("Pemrograman Lanjut", "2024/2025", "Ganjil", 3);

        mvc.perform(post("/api/lowongan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newLowongan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mataKuliah").value("Pemrograman Lanjut"))
                .andExpect(jsonPath("$.tahunAjaran").value("2024/2025"))
                .andExpect(jsonPath("$.semester").value("Ganjil"))
                .andExpect(jsonPath("$.jumlahAsistenDibutuhkan").value(3));
    }

    @Test
    public void testGetAllLowongan() throws Exception {
        mvc.perform(get("/api/lowongan/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateLowongan() throws Exception {
        // First save a Lowongan to database
        Lowongan saved = lowonganRepository.save(new Lowongan("AI", "2024/2025", "Genap", 5));

        // Update fields
        saved.setMataKuliah("AI dan Data Science");
        saved.setJumlahAsistenDibutuhkan(7);

        mvc.perform(put("/api/lowongan/edit/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mataKuliah").value("AI dan Data Science"))
                .andExpect(jsonPath("$.jumlahAsistenDibutuhkan").value(7));
    }

    @Test
    public void testDeleteLowongan() throws Exception {
        // First save a Lowongan to database
        Lowongan saved = lowonganRepository.save(new Lowongan("Cloud Computing", "2024/2025", "Ganjil", 2));

        mvc.perform(delete("/api/lowongan/delete/" + saved.getId()))
                .andExpect(status().isOk());
    }
}
