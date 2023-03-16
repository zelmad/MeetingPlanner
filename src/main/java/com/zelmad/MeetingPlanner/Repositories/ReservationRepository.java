package com.zelmad.MeetingPlanner.Repositories;

import com.zelmad.MeetingPlanner.Domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
