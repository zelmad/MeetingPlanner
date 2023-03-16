package com.zelmad.MeetingPlanner.Services;

import com.zelmad.MeetingPlanner.Domain.Equipement;
import com.zelmad.MeetingPlanner.Domain.Reservation;
import com.zelmad.MeetingPlanner.Domain.Salle;
import com.zelmad.MeetingPlanner.Dtos.ReunionDTO;
import com.zelmad.MeetingPlanner.Dtos.SalleDto;
import com.zelmad.MeetingPlanner.Enums.TypeReunion;
import com.zelmad.MeetingPlanner.Repositories.ReservationRepository;
import com.zelmad.MeetingPlanner.Repositories.SalleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SalleReservationSrvTest {

    @Mock
    private SalleRepository salleRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private SalleReservationSrv salleReservationSrv;

    private List<Salle> salles = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        // RC
        salles.add(Salle.builder().salle_name("E1001").capacity(5)
                .equipements(Arrays.asList(Equipement.builder().name("NEANT").id(1L).build()))
                .reservations(Arrays.asList(Reservation.builder()
                        .end_date(LocalDateTime
                                .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                        LocalDateTime.now().getHour() + 1, 0))
                        .build()))
                .build());
        // VC
        salles.add(Salle.builder().salle_name("E1002").capacity(5)
                .equipements(Arrays.asList(
                        Equipement.builder().name("ECRAN").id(1L).build(),
                        Equipement.builder().name("PIEUVRE").id(1L).build(),
                        Equipement.builder().name("WEBCAM").id(1L).build())
                )
                .reservations(Arrays.asList(Reservation.builder()
                        .end_date(LocalDateTime
                                .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                        LocalDateTime.now().getHour() + 1, 0))
                        .build()))
                .build());
        // SPEC
        salles.add(Salle.builder().salle_name("E1003").capacity(5)
                .equipements(Arrays.asList(Equipement.builder().name("TABLEAU").id(1L).build()))
                .reservations(Arrays.asList(Reservation.builder()
                        .end_date(LocalDateTime
                                .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                        LocalDateTime.now().getHour() + 1, 0))
                        .build()))
                .build());
        // RS
        salles.add(Salle.builder().salle_name("E1004").capacity(5)
                .equipements(Arrays.asList(
                        Equipement.builder().name("ECRAN").id(1L).build(),
                        Equipement.builder().name("PIEUVRE").id(1L).build(),
                        Equipement.builder().name("TABLEAU").id(1L).build())
                )
                .reservations(Arrays.asList(Reservation.builder()
                        .end_date(LocalDateTime
                                .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                        LocalDateTime.now().getHour() + 1, 0))
                        .build()))
                .build());
    }

    @Test
    public void souldReturnRcSalle() {
        ReunionDTO reunionDTO = ReunionDTO.builder()
                .reunionName("reunion_1").type(TypeReunion.RC).nbrOfHours(1).nbrPersonnes(2)
                .heureDprt(LocalDateTime
                        .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                LocalDateTime.now().getHour() + 4, 0))
                .build();
        given(salleRepository.findAll()).willReturn(salles);
        List<SalleDto> sallesDtos = salleReservationSrv.getAllSalleLibres(reunionDTO);
        assertThat(sallesDtos).isNotEmpty();
        assertEquals(1, sallesDtos.size());
        assertEquals("E1001", sallesDtos.get(0).getSalle_name());
        LocalDateTime date = salles.get(0).getReservations().get(0).getEnd_date();
        assertEquals(sallesDtos.get(0).getPropsed_start(), salles.get(0).getReservations().get(0).getEnd_date());
        assertEquals(sallesDtos.get(0).getPropsed_end(),
                LocalDateTime
                        .of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                                date.getHour() + reunionDTO.getNbrOfHours() + 1, 0));
    }

    @Test
    public void souldReturnVcSalle() {
        ReunionDTO reunionDTO = ReunionDTO.builder()
                .reunionName("reunion_2").type(TypeReunion.VC).nbrOfHours(1).nbrPersonnes(2)
                .heureDprt(LocalDateTime
                        .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                LocalDateTime.now().getHour() + 4, 0))
                .build();
        given(salleRepository.findAll()).willReturn(salles);
        List<SalleDto> sallesDtos = salleReservationSrv.getAllSalleLibres(reunionDTO);
        assertThat(sallesDtos).isNotEmpty();
        assertEquals(1, sallesDtos.size());
        assertEquals("E1002", sallesDtos.get(0).getSalle_name());
        LocalDateTime date = salles.get(0).getReservations().get(0).getEnd_date();
        assertEquals(sallesDtos.get(0).getPropsed_start(), salles.get(0).getReservations().get(0).getEnd_date());
        assertEquals(sallesDtos.get(0).getPropsed_end(),
                LocalDateTime
                        .of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                                date.getHour() + reunionDTO.getNbrOfHours() + 1, 0));
    }

    @Test
    public void souldReturnSPECSalle() {
        ReunionDTO reunionDTO = ReunionDTO.builder()
                .reunionName("reunion_3").type(TypeReunion.SPEC).nbrOfHours(1).nbrPersonnes(2)
                .heureDprt(LocalDateTime
                        .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                LocalDateTime.now().getHour() + 4, 0))
                .build();
        given(salleRepository.findAll()).willReturn(salles);
        List<SalleDto> sallesDtos = salleReservationSrv.getAllSalleLibres(reunionDTO);
        assertThat(sallesDtos).isNotEmpty();
        assertEquals(1, sallesDtos.size());
        assertEquals("E1003", sallesDtos.get(0).getSalle_name());
        LocalDateTime date = salles.get(0).getReservations().get(0).getEnd_date();
        assertEquals(sallesDtos.get(0).getPropsed_start(), salles.get(0).getReservations().get(0).getEnd_date());
        assertEquals(sallesDtos.get(0).getPropsed_end(),
                LocalDateTime
                        .of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                                date.getHour() + reunionDTO.getNbrOfHours() + 1, 0));
    }

    @Test
    public void souldReturnRsSalle() {
        ReunionDTO reunionDTO = ReunionDTO.builder()
                .reunionName("reunion_4").type(TypeReunion.RS).nbrOfHours(1).nbrPersonnes(2)
                .heureDprt(LocalDateTime
                        .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(),
                                LocalDateTime.now().getHour() + 4, 0))
                .build();
        given(salleRepository.findAll()).willReturn(salles);
        List<SalleDto> sallesDtos = salleReservationSrv.getAllSalleLibres(reunionDTO);
        assertThat(sallesDtos).isNotEmpty();
        assertEquals(1, sallesDtos.size());
        assertEquals("E1004", sallesDtos.get(0).getSalle_name());
        LocalDateTime date = salles.get(0).getReservations().get(0).getEnd_date();
        assertEquals(sallesDtos.get(0).getPropsed_start(), salles.get(0).getReservations().get(0).getEnd_date());
        assertEquals(sallesDtos.get(0).getPropsed_end(),
                LocalDateTime
                        .of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                                date.getHour() + reunionDTO.getNbrOfHours() + 1, 0));
    }
}