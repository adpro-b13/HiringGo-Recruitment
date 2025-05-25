package id.ac.ui.cs.advprog.b13.hiringgo.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.Lowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.model.PendaftaranLowongan;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.LowonganRepository;
import id.ac.ui.cs.advprog.b13.hiringgo.recruitment.repository.PendaftaranLowonganRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import java.util.List;

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

    @Autowired
    private PendaftaranLowonganRepository pendaftaranRepository;

    private static final String INVALID_ROLE_TOKEN =
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsInVzZXJJZCI6NCwibmFtYUxlbmdrYXAiOiJhIiwicm9sZXMiOjEsImlhdCI6MTc0Nzk5MDMwNSwiZXhwIjoxNzQ4MDc2NzA1fQ.9SVGjRpJMFJwXwWIQlpo79pa7zaofabbyPmMs9d9X5YewloUuVVuiDEI1LTpW123prYMEvXwNs5n2fjaBZ7jxQ";

    private static final String TOKEN =
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsInVzZXJJZCI6NCwibmFtYUxlbmdrYXAiOiJhIiwicm9sZXMiOiJST0xFX0RPU0VOIiwiaWF0IjoxNzQ3OTkwMzA1LCJleHAiOjE3Nzk2MTI3MDV9.se5JVH9peoT7e8EZHnYL0zebsLGo-s_PP1EK3Wq1P7c3oTTfMHw70WAtr1q4sQlusIFBKunNFU9saExZ83ziEw";

    private static final String MAHASISWA_TOKEN =
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYXNpc3dhQGV4YW1wbGUuY29tIiwidXNlcklkIjo2LCJuYW1hTGVuZ2thcCI6IkZ1bGFuIE1haGFzaXN3YSIsInJvbGVzIjoiUk9MRV9NQUhBU0lTV0EiLCJpYXQiOjE3NDgwNTY3MTUsImV4cCI6MTc4OTY3OTExNX0.EG4Z2D7ikg7WrS2Uc9zSBsLN-PKIcEuSI_cU2WGRxex5xZioQjz8Sj01EdKOiK5Rgr1GKRQ75TSt6-jbLZTFtw";

    @Test
    public void testCreateEditDeleteFlow() throws Exception {
        String mataKuliah = "Pengujian " + System.currentTimeMillis();
        Lowongan lowongan = new Lowongan(mataKuliah, "2024", "Genap", 3);

        String response = mvc.perform(post("/api/lowongan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TOKEN)
                        .content(objectMapper.writeValueAsString(lowongan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mataKuliah", is(mataKuliah)))
                .andReturn().getResponse().getContentAsString();

        Lowongan created = objectMapper.readValue(response, Lowongan.class);

        created.setMataKuliah("Rekayasa Perangkat Lunak");
        created.setJumlahAsistenDibutuhkan(10);

        mvc.perform(put("/api/lowongan/edit/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TOKEN)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mataKuliah", is("Rekayasa Perangkat Lunak")))
                .andExpect(jsonPath("$.jumlahAsistenDibutuhkan", is(10)));

        mvc.perform(delete("/api/lowongan/delete/" + created.getId())
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLowonganAndListMyLowongan() throws Exception {
        Lowongan lowongan = new Lowongan("Sistem Operasi", "2024/2025", "Ganjil", 2);
        lowongan.setCreatedBy(4L);
        lowongan = lowonganRepository.save(lowongan);

        mvc.perform(get("/api/lowongan/" + lowongan.getId())
                        .header("Authorization", TOKEN)) // add this line
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mataKuliah", is("Sistem Operasi")));


        mvc.perform(get("/api/lowongan/my")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    public void testListLowongan() throws Exception {
        mvc.perform(get("/api/lowongan/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDaftarLihatStatusTerimaTolakPelamar() throws Exception {
        Lowongan lowongan = new Lowongan("Teori Bahasa", "2024", "Genap", 1);
        lowongan.setCreatedBy(4L);
        lowongan = lowonganRepository.save(lowongan);

        mvc.perform(post("/api/lowongan/daftar/" + lowongan.getId())
                        .param("ipk", "3.5")
                        .param("jumlahSks", "100")
                        .header("Authorization", MAHASISWA_TOKEN))
                .andExpect(status().isOk());

        mvc.perform(get("/api/lowongan/status")
                        .header("Authorization", MAHASISWA_TOKEN))
                .andExpect(status().isOk());

        mvc.perform(get("/api/lowongan/pelamar/" + lowongan.getId())
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        List<PendaftaranLowongan> daftar = pendaftaranRepository.findByLowonganId(lowongan.getId());
        Long pendaftaranId = daftar.get(0).getId();

        mvc.perform(put("/api/lowongan/pelamar/" + pendaftaranId + "/terima")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("DITERIMA")));

        mvc.perform(put("/api/lowongan/pelamar/" + pendaftaranId + "/tolak")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("DITOLAK")));
    }


    @Test
    public void testDaftarLowonganInvalidIPK() throws Exception {
        Lowongan lowongan = new Lowongan("Validasi IPK", "2024", "Genap", 1);
        lowongan.setCreatedBy(4L);
        lowongan = lowonganRepository.save(lowongan);

        mvc.perform(post("/api/lowongan/daftar/" + lowongan.getId())
                        .param("ipk", "4.5")
                        .param("jumlahSks", "90")
                        .header("Authorization", MAHASISWA_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTerimaPelamarKuotaPenuh() throws Exception {
        Lowongan lowongan = new Lowongan("Kuota Penuh", "2024/2025", "Ganjil", 1);
        lowongan.setCreatedBy(4L);
        lowongan = lowonganRepository.save(lowongan);

        // Pre-fill with 1 accepted applicant to fill the quota
        PendaftaranLowongan accepted = new PendaftaranLowongan();
        accepted.setMahasiswaId(100L);
        accepted.setLowongan(lowongan);
        accepted.setStatus("DITERIMA");
        pendaftaranRepository.save(accepted);

        // Now add the new one to test rejection
        PendaftaranLowongan test = new PendaftaranLowongan();
        test.setMahasiswaId(6L);
        test.setLowongan(lowongan);
        test = pendaftaranRepository.save(test);

        mvc.perform(put("/api/lowongan/pelamar/" + test.getId() + "/terima")
                        .header("Authorization", TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Kuota sudah penuh")));
    }
    @Test
    public void testEditLowonganForbidden() throws Exception {
        Lowongan lowongan = new Lowongan("Unauthorized Edit", "2024/2025", "Ganjil", 2);
        lowongan.setCreatedBy(999L); // not the same as userId in TOKEN (which is 4)
        lowongan = lowonganRepository.save(lowongan);

        lowongan.setMataKuliah("Should Not Update");

        mvc.perform(put("/api/lowongan/edit/" + lowongan.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TOKEN)
                        .content(objectMapper.writeValueAsString(lowongan)))
                .andExpect(status().isForbidden());
    }
    @Test
    public void testDeleteLowonganForbidden() throws Exception {
        Lowongan lowongan = new Lowongan("Unauthorized Delete", "2024/2025", "Ganjil", 2);
        lowongan.setCreatedBy(999L); // not owned by user
        lowongan = lowonganRepository.save(lowongan);

        mvc.perform(delete("/api/lowongan/delete/" + lowongan.getId())
                        .header("Authorization", TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testLihatPelamarForbidden() throws Exception {
        Lowongan lowongan = new Lowongan("Pelamar Access Check", "2024/2025", "Genap", 3);
        lowongan.setCreatedBy(999L); // not user 4
        lowongan = lowonganRepository.save(lowongan);

        mvc.perform(get("/api/lowongan/pelamar/" + lowongan.getId())
                        .header("Authorization", TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateLowonganWithoutToken() throws Exception {
        Lowongan lowongan = new Lowongan("No Token Create", "2024", "Ganjil", 1);

        mvc.perform(post("/api/lowongan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lowongan)))
                .andExpect(status().isForbidden());
    }
    @Test
    public void testCreateLowonganWithInvalidRole() throws Exception {
        Lowongan lowongan = new Lowongan("Invalid Role", "2024", "Ganjil", 1);

        mvc.perform(post("/api/lowongan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", INVALID_ROLE_TOKEN)
                        .content(objectMapper.writeValueAsString(lowongan)))
                .andExpect(status().isForbidden());
    }
    @Test
    public void testInvalidPrefixAuthHeader() throws Exception {
        Lowongan lowongan = new Lowongan("Invalid Prefix", "2024/2025", "Genap", 2);
        mvc.perform(post("/api/lowongan/create")
                        .header("Authorization", "Token xyz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lowongan)))
                .andExpect(status().isForbidden()); // or Forbidden
    }



}
