package com.zelmad.MeetingPlanner.Resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zelmad.MeetingPlanner.Domain.Reservation;
import com.zelmad.MeetingPlanner.Domain.Salle;
import com.zelmad.MeetingPlanner.Dtos.ReunionDTO;
import com.zelmad.MeetingPlanner.Dtos.SalleDto;
import com.zelmad.MeetingPlanner.Enums.TypeReunion;
import com.zelmad.MeetingPlanner.Services.SalleReservationSrv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SalleReservationRsc.class)
public class SalleReservationRscTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalleReservationSrv salleReservationSrv;

    @Autowired
    private ObjectMapper objectMapper;

    private List<SalleDto> salleList = new ArrayList<>();
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        this.salleList.add(new SalleDto("reunion_1", "E1001", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)));
        this.salleList.add(new SalleDto("reunion_2", "E1002", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)));
        this.salleList.add(new SalleDto("reunion_3", "E1003", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)));

        this.reservation = Reservation.builder()
                .id(1L).reunion_name("reunion_1").salle(Salle.builder().salle_name("E1001").capacity(4).build())
                .start_date(LocalDateTime.now()).end_date(LocalDateTime.now())
                .build();
    }

    @Test
    public void shouldFetchAllSalles() throws Exception {
        ReunionDTO reunion = ReunionDTO.builder().type(TypeReunion.RC)
                .reunionName("reunion_1").nbrPersonnes(2).nbrOfHours(1)
                .heureDprt(LocalDateTime.now().plusHours(1))
                .build();
        given(salleReservationSrv.getAllSalleLibres(reunion)).willReturn(salleList);

        this.mockMvc.perform(get("/api/v1/salle/propositions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reunion)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReserveSalle() throws Exception {
        SalleDto sal = new SalleDto("reunion_1", "E1001",
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2));
        given(salleReservationSrv.reserveSalle(sal)).willReturn(reservation);

        this.mockMvc.perform(post("/api/v1/salle/reservation")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(sal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reunion_name", is(reservation.getReunion_name())));
    }
}