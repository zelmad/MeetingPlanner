package com.zelmad.MeetingPlanner.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipement {

    @Id
    private long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_name")
    private Salle salle;

    public String getName() {
        return name;
    }
}
