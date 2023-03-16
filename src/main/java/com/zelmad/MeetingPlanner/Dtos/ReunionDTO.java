package com.zelmad.MeetingPlanner.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zelmad.MeetingPlanner.Enums.TypeReunion;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReunionDTO {

    @NotNull
    @ApiModelProperty(notes = "meeting name")
    private String reunionName;
    @NotNull
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "meeting type")
    private TypeReunion type;
    @Min(1)
    @ApiModelProperty(notes = "number of personnes will be present on the room")
    private int nbrPersonnes;
    @Future
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "planified starting time of the meeting")
    private LocalDateTime heureDprt;
    @Min(1) //La reservation se fait heure par heure selon la spec.
    @ApiModelProperty(notes = "meeting number of hours")
    private int nbrOfHours;
}
