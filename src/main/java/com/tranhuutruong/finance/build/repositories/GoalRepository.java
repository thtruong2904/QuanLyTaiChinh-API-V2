package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT g FROM Goal g WHERE g.userInformation.id = :id")
    Iterable<Goal> getAllByUser(@Param("id") Long idUser);

    @Query("SELECT g FROM Goal g WHERE g.userInformation.id = :idUser AND g.id = :idGoal")
    Goal findByUserAndGoalId(@Param("idUser") Long idUser, @Param("idGoal") Long idGoal);

    @Query("SELECT g FROM Goal g WHERE g.userInformation.id = :idUser AND g.name = :name AND g.deadline = :deadline")
    Goal findByUserAndNameAndDeadline(@Param("idUser") Long idUser, @Param("name") String name, @Param("deadline") Date deadline);

    @Query("FROM Goal g WHERE g.userInformation.id = :idUser AND g.name LIKE %:name%")
    Iterable<Goal> findByName(@Param("idUser") Long idUser, @Param("name") String name);
}
