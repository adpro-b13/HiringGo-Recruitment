package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LowonganControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetAllLowongan() throws Exception {
        mvc.perform(get("/lowongan/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateLowonganPost() throws Exception {
        mvc.perform(post("/lowongan/create")
                        .param("matakuliah", "Pemrograman Lanjut")
                        .param("tahunAjaran", "2024/2025")
                        .param("semester", "Ganjil")
                        .param("jumlahAsisten", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lowongan/list"));
    }

}
