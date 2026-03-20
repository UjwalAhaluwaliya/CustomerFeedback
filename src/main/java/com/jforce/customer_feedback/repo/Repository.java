package com.jforce.customer_feedback.repo;

import com.jforce.customer_feedback.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Employee,Integer > {
    List<Employee> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Optional<Employee> findById(int id);

}
