package com.zelmad.MeetingPlanner.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Salle {

    @Id
    private String salle_name;

    private int capacity;

    @OneToMany(mappedBy = "salle")
    private List<Equipement> equipements;

    @OneToMany(mappedBy = "salle")
    private List<Reservation> reservations;
}
