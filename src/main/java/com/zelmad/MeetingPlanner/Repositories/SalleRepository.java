package com.zelmad.MeetingPlanner.Repositories;

import com.zelmad.MeetingPlanner.Domain.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, String> {

}
