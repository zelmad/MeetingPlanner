package com.zelmad.MeetingPlanner.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(notes = "reservation id")
    private long id;
    @ApiModelProperty(notes = "meeting name")
    private String reunion_name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_name")
    private Salle salle;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "meeting start time")
    private LocalDateTime start_date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "meeting start time")
    private LocalDateTime end_date;

    public long getId() {
        return id;
    }

    public String getReunion_name() {
        return reunion_name;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }
}
