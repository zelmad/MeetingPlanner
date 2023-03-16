package com.zelmad.MeetingPlanner.Services;

import com.zelmad.MeetingPlanner.Domain.Reservation;
import com.zelmad.MeetingPlanner.Domain.Salle;
import com.zelmad.MeetingPlanner.Dtos.ReunionDTO;
import com.zelmad.MeetingPlanner.Dtos.SalleDto;
import com.zelmad.MeetingPlanner.Exceptions.SalleNotFoundException;
import com.zelmad.MeetingPlanner.Repositories.ReservationRepository;
import com.zelmad.MeetingPlanner.Repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalleReservationSrv {

    @Autowired
    private SalleRepository salleRepos;

    @Autowired
    private ReservationRepository reservationRepos;

    @Transactional(readOnly = true)
    public List<SalleDto> getAllSalleLibres(ReunionDTO reunion) {
        List<Salle> salles = salleRepos.findAll();
        List<Salle> selectSpecifiedSalles = null;
        switch (reunion.getType().name()){
            case "VC":
                selectSpecifiedSalles = getAllVcSalles(salles);
                break;
            case "SPEC":
                selectSpecifiedSalles = getAllSpecSalles(salles, reunion.getNbrPersonnes());
                break;
            case "RC":
                selectSpecifiedSalles = getAllRcSalles(salles, reunion.getNbrPersonnes());
                break;
            case "RS":
                selectSpecifiedSalles = getAllRsSalles(salles);
                break;
        }
        List<SalleDto> proposedSalle = getAllProposedSalles(selectSpecifiedSalles, reunion);
        return proposedSalle;
    }

    private List<Salle> getAllRcSalles(List<Salle> salles, int nbrPersonnes) {
        return salles.stream()
                .filter(s -> s.getEquipements().stream().allMatch(e->"NEANT".equals(e.getName()))
                        && s.getCapacity() > 3 && s.getCapacity() >= nbrPersonnes).collect(Collectors.toList());
    }

    private List<Salle> getAllVcSalles(List<Salle> salles) {
        return salles.stream()
                .filter(s -> s.getEquipements().stream().anyMatch(e->"ECRAN".equals(e.getName()))
                        && s.getEquipements().stream().anyMatch(e->"PIEUVRE".equals(e.getName()))
                        && s.getEquipements().stream().anyMatch(e->"WEBCAM".equals(e.getName()))
                ).collect(Collectors.toList());
    }

    private List<Salle> getAllSpecSalles(List<Salle> salles, int nbrPersonnes) {
        return salles.stream()
                .filter(s -> s.getEquipements().stream().allMatch(e->"TABLEAU".equals(e.getName()))
                        && s.getCapacity() >= nbrPersonnes
                ).collect(Collectors.toList());
    }

    private List<Salle> getAllRsSalles(List<Salle> salles) {
        return salles.stream()
                .filter(s -> s.getEquipements().stream().anyMatch(e->"ECRAN".equals(e.getName()))
                        && s.getEquipements().stream().anyMatch(e->"PIEUVRE".equals(e.getName()))
                        && s.getEquipements().stream().anyMatch(e->"TABLEAU".equals(e.getName()))
                ).collect(Collectors.toList());
    }

    private List<SalleDto> getAllProposedSalles(List<Salle> salles, ReunionDTO reunion) {
        List<SalleDto> proposedSalles = new ArrayList<>();
        salles.stream().forEach(salle -> {
            // The Order of date is used because Reservation are
            List<Reservation> sallesVcReservees = salle.getReservations()
                    .stream().sorted(Comparator.comparing(Reservation::getEnd_date)).collect(Collectors.toList());
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastReserveEndDate = null, proposedEndDate = null;
            if(sallesVcReservees.isEmpty()){
                lastReserveEndDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 8, 0);
                proposedEndDate = lastReserveEndDate;
            } else {
                lastReserveEndDate = getProposedHoraire(sallesVcReservees, reunion);
                proposedEndDate = lastReserveEndDate.plusHours(reunion.getNbrOfHours() + 1);
            }
            proposedSalles.add(buidSalle(reunion, salle, lastReserveEndDate, proposedEndDate));
        });
        // Suggest Sort on begining date on FRONT-END Project.
        return proposedSalles;
    }

    private LocalDateTime getProposedHoraire(List<Reservation> sallesVcReservees, ReunionDTO reunion) {
        LocalDateTime lastReserveEndDate = null;
        if(LocalDateTime.now().isAfter(sallesVcReservees.get(sallesVcReservees.size()-1).getEnd_date())){
            lastReserveEndDate = LocalDateTime.now();
        } else if(sallesVcReservees.get(sallesVcReservees.size()-1).getEnd_date().isBefore(reunion.getHeureDprt())) {
            lastReserveEndDate = sallesVcReservees.get(sallesVcReservees.size() - 1).getEnd_date();
        }
        // Pour decaler a la journée suivante.
        else if(reunion.getHeureDprt().getHour() > 20
                || lastReserveEndDate.plusHours(reunion.getNbrOfHours() +1).getHour() > 20){
            // Take care of week end.
            if(lastReserveEndDate.plusDays(1).getDayOfWeek() == DayOfWeek.SATURDAY){
                lastReserveEndDate.plusDays(3);
            } else {
                lastReserveEndDate.plusDays(1);
            }
            lastReserveEndDate = LocalDateTime.of(lastReserveEndDate.getYear(), lastReserveEndDate.getMonth(),
                    lastReserveEndDate.getDayOfMonth(), 8, 0);
        }
        return lastReserveEndDate;
    }

    private SalleDto buidSalle(ReunionDTO reunion, Salle salle, LocalDateTime lastReserveEndDate, LocalDateTime proposedEndDate) {
        return SalleDto.builder()
                .reunion_name(reunion.getReunionName())
                .salle_name(salle.getSalle_name())
                .propsed_start(lastReserveEndDate)
                .propsed_end(proposedEndDate)
                .build();
    }

    @Transactional
    public Reservation reserveSalle(SalleDto salle) {
        Optional<Salle> toReserveSalle = salleRepos.findById(salle.getSalle_name());
        if(toReserveSalle.isPresent()) {
            Reservation reservation = Reservation.builder()
                    .reunion_name(salle.getReunion_name())
                    .salle(toReserveSalle.get())
                    .start_date(salle.getPropsed_start())
                    .end_date(salle.getPropsed_end())
                    .build();
            return reservationRepos.save(reservation);
        } else {
            throw new SalleNotFoundException("Salle Not Found in DB");
        }

    }
}
