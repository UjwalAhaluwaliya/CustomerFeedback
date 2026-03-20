package com.jforce.customer_feedback.Service;

import com.jforce.customer_feedback.Entity.Employee;
import com.jforce.customer_feedback.Entity.Feedback;
import com.jforce.customer_feedback.repo.FeedbackRepository;
import com.jforce.customer_feedback.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class businesslogic {
    @Autowired
    public Repository rep;

    @Autowired
    public FeedbackRepository feedbackRepository;

    public void save(Employee emp) {
        rep.save(emp);
    }

    public List<Employee> findByUsernameAndPassword(Employee emp) {
        return rep.findByUsernameAndPassword(emp.getUsername(), emp.getPassword());
    }

    public boolean usernameExists(String username) {
        return rep.existsByUsername(username);
    }

    public Optional<Employee> findEmployeeById(int id) {
        return rep.findById(id);
    }

    public Optional<Feedback> findFeedbackByEmployeeId(int empId) {
        // return feedbackRepository.findByEmployeeId(empId);
        return feedbackRepository.findByEmployeeEmpId(empId);

    }

    public Feedback saveFeedback(Employee employee, String message) {
        Feedback feedback = findFeedbackByEmployeeId(employee.getEmpId()).orElse(new Feedback());
        feedback.setEmployee(employee);
        feedback.setMessage(message);
        feedback.setSubmittedDate(LocalDate.now());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAllByOrderBySubmittedDateDescIdDesc();
    }

    public Optional<Feedback> findFeedbackById(int id) {
        return feedbackRepository.findById(id);
    }

    public Feedback saveExistingFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedbackById(int id) {
        feedbackRepository.deleteById(id);
    }
}
