package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Lowongan lowongan = new Lowongan("Matematika", "2024", "Genap", 3);
    }

    @Test
    public void testRegisterForLowonganValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lowongan/1/register")
                        .param("sks", "18")
                        .param("ipk", "3.5"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"));

        verify(lowonganService, times(1)).registerForLowongan(anyLong(), anyInt(), anyDouble());
    }

    @Test
    public void testRegisterForLowonganInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lowongan/1/register")
                        .param("sks", "0")
                        .param("ipk", "5"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("failure"));
    }
}
