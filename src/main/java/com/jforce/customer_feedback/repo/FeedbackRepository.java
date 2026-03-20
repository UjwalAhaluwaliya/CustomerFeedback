package com.jforce.customer_feedback.repo;

import com.jforce.customer_feedback.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    // @Query("select f from Feedback f where f.employee.empId = :empId")
    // Optional<Feedback> findByEmployeeId(@Param("empId") int empId);
    Optional<Feedback> findByEmployeeEmpId(int empId);

    List<Feedback> findAllByOrderBySubmittedDateDescIdDesc();
}
