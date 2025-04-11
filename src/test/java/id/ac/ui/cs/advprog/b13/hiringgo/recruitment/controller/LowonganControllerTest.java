package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.service.LowonganService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LowonganController.class)
public class LowonganControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LowonganService lowonganService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllLowongan() throws Exception {
        Lowongan lowongan = new Lowongan("PBO", "2024/2025", "Genap", 2);
        lowongan.setId(1L);
        when(lowonganService.findAll()).thenReturn(List.of(lowongan));

        mockMvc.perform(get("/lowongan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mataKuliah").value("PBO"))
                .andExpect(jsonPath("$[0].tahunAjaran").value("2024/2025"));
    }

    @Test
    void testCreateLowongan() throws Exception {
        Lowongan lowongan = new Lowongan("PBO", "2024/2025", "Genap", 2);
        lowongan.setId(1L);

        when(lowonganService.save(any())).thenReturn(lowongan);

        mockMvc.perform(post("/lowongan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lowongan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.mataKuliah").value("PBO"));
    }
}
