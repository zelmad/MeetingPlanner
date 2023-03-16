package com.zelmad.MeetingPlanner.Resources;

import com.zelmad.MeetingPlanner.Domain.Reservation;
import com.zelmad.MeetingPlanner.Dtos.ReunionDTO;
import com.zelmad.MeetingPlanner.Dtos.SalleDto;
import com.zelmad.MeetingPlanner.Services.SalleReservationSrv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/salle")
@Api(value = "MeetingPlanner", description = "manage department rooms reservation")
public class SalleReservationRsc {

    private static final Logger logger = LoggerFactory.getLogger(SalleReservationRsc.class);

    @Autowired
    private SalleReservationSrv salleReservationSrv;


    @ApiOperation(value = "View a list of available rooms", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
        }
    )
    @GetMapping("/propositions")
    @ResponseStatus(HttpStatus.OK)
    public List<SalleDto> getPropositionSalle(@Valid @RequestBody ReunionDTO reunion) {
        logger.debug("Reunion type :" + reunion.getType());
        return salleReservationSrv.getAllSalleLibres(reunion);
    }

    @ApiOperation(value = "Reserve the room", response = Reservation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PostMapping("/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation reserveSalle(@Valid @RequestBody SalleDto salle) {
        logger.debug("Salle a reservée :" + salle.getSalle_name());
        return salleReservationSrv.reserveSalle(salle);
    }
}
