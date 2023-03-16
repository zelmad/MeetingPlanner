package com.zelmad.MeetingPlanner.Dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SalleDto {

    @NotNull
    @ApiModelProperty(notes = "meeting name")
    private String reunion_name;
    @NotNull
    @ApiModelProperty(notes = "room name")
    private String salle_name;
    @Future
    @ApiModelProperty(notes = "proposed time to start meeting")
    private LocalDateTime propsed_start;
    @Future
    @ApiModelProperty(notes = "proposed time to end meeting")
    private LocalDateTime propsed_end;
}
