package com.sunvalley.festivalFlowbe.repository;

import com.sunvalley.festivalFlowbe.entity.ShiftEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {

    List<ShiftEntity> findByLocationId(int location);

    @Query("SELECT s FROM ShiftEntity s JOIN LocationEntity l ON s.location.id = l.id WHERE l.adultsOnly=false and s.id=?1")
    ShiftEntity findByIdOnlyMinors(int id);

    @Query("SELECT s FROM ShiftEntity s JOIN LocationEntity l ON s.location.id = l.id WHERE l.id=?1 and l.adultsOnly=false")
    List<ShiftEntity> findAllByLocationIdAndOnlyMinors(int locationId);

    @Query("SELECT s FROM ShiftEntity s join AssociationEntity a ON s.id = a.id.shiftId JOIN CollaboratorEntity c ON c.id = a.id.collaboratorId WHERE a.status = 1 AND c.id= ?1")
    List<ShiftEntity> findAllByCollaboratorIdAndShiftAccepted(int id);

}