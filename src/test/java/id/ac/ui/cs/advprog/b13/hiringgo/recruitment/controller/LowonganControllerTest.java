package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.LowonganService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    @Mock
    private LowonganService lowonganService; // Mocking LowonganService

    @InjectMocks
    private LowonganController lowonganController; // Injecting the controller

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testDaftarLowonganPost() throws Exception {
        Lowongan lowongan = new Lowongan("PBO", "2024", "Genap", 2);
        lowongan.setId(1L);

        when(lowonganService.findById(1L)).thenReturn(Optional.of(lowongan));

        mvc.perform(post("/lowongan/daftar/1")
                        .param("ipk", "3.9")
                        .param("jumlahSks", "100"))
                .andExpect(status().is3xxRedirection());
    }
}

