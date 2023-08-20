package com.tranhuutruong.finance.build.repositories;

import com.tranhuutruong.finance.build.entities.GoalProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalProgressRepository extends JpaRepository<GoalProgress, Long> {

    @Query("SELECT g FROM GoalProgress g WHERE g.goal.id = :id")
    List<GoalProgress> findAllByGoalId(@Param("id") Long idGoal);
}
