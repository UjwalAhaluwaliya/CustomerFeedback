package com.jforce.customer_feedback.controller;

import com.jforce.customer_feedback.Entity.Employee;
import com.jforce.customer_feedback.Entity.Feedback;
import com.jforce.customer_feedback.Service.businesslogic;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class control {

    @Autowired
    public businesslogic businessL;

    @GetMapping("/")
    public String index() {
         return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("employee", new Employee());
        return "login";
    }

    @GetMapping("/register")
    public String empReg(Model model) {
        model.addAttribute("employee", new Employee());
        return "register";
    }

    @PostMapping("/register")
    public String Employee(@ModelAttribute("employee") Employee emp, Model model) {
        if (emp.getPassword() == null || !emp.getPassword().equals(emp.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        if (businessL.usernameExists(emp.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        businessL.save(emp);
        model.addAttribute("success", "Registration successful. Please log in.");
        model.addAttribute("employee", new Employee());
        return "login";
    }

    @PostMapping("/login")
    public String Userlogin(@ModelAttribute("employee") Employee emp, Model model, HttpSession session) {
        if ("admin".equals(emp.getUsername()) && "admin123".equals(emp.getPassword())) {
            session.setAttribute("isAdmin", true);
            return "redirect:/admin/dashboard";
        }

        List<Employee> users = businessL.findByUsernameAndPassword(emp);
        if (!users.isEmpty()) {
            Employee loggedInEmp = users.get(0);
            session.setAttribute("userId", loggedInEmp.getEmpId());
            session.setAttribute("username", loggedInEmp.getUsername());
            return "redirect:/feedback";
        } else {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("employee", new Employee());
            return "login";
        }
    }

    //user to see his her feedback
    @GetMapping("/feedback")
    public String feedbackPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Employee> employee = businessL.findEmployeeById(userId);
        if (employee.isEmpty()) {
            session.invalidate();
            return "redirect:/login";
        }

        Optional<Feedback> feedback = businessL.findFeedbackByEmployeeId(userId);
        model.addAttribute("employee", employee.get());
        model.addAttribute("feedback", feedback.orElse(null));
        return "home";
    }
 //user can save his her
    @PostMapping("/feedback/save")
    public String saveFeedback(@RequestParam("message") String message,
                               @RequestParam("action") String action,
                               HttpSession session,
                               Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Employee> employee = businessL.findEmployeeById(userId);
        if (employee.isEmpty()) {
            session.invalidate();
            return "redirect:/login";
        }

        Optional<Feedback> existingFeedback = businessL.findFeedbackByEmployeeId(userId);

        if (message == null || message.trim().isEmpty()) {
            model.addAttribute("error", "Feedback cannot be empty");
            model.addAttribute("employee", employee.get());
            model.addAttribute("feedback", existingFeedback.orElse(null));
            return "home";
        }

        if ("add".equals(action) && existingFeedback.isPresent()) {
            model.addAttribute("error", "Feedback already exists. Use Edit Feedback.");
            model.addAttribute("employee", employee.get());
            model.addAttribute("feedback", existingFeedback.orElse(null));
            return "home";
        }

        if ("edit".equals(action) && existingFeedback.isEmpty()) {
            model.addAttribute("error", "No feedback found to edit. Use Add Feedback first.");
            model.addAttribute("employee", employee.get());
            model.addAttribute("feedback", null);
            return "home";
        }

        businessL.saveFeedback(employee.get(), message.trim());
        model.addAttribute("success", "Feedback saved successfully");
        model.addAttribute("employee", employee.get());
        model.addAttribute("feedback", businessL.findFeedbackByEmployeeId(userId).orElse(null));
        return "home";
    }
//if admin login
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }

        model.addAttribute("feedbackList", businessL.getAllFeedback());
        return "admin-dashboard";
    }
//admin operation
    @GetMapping("/admin/view/{id}")
    public String viewFeedback(@PathVariable int id, Model model, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }

        Optional<Feedback> feedback = businessL.findFeedbackById(id);
        if (feedback.isEmpty()) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("feedback", feedback.get());
        return "admin-view";
    }

    @GetMapping("/admin/edit/{id}")
    public String editFeedbackPage(@PathVariable int id, Model model, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }

        Optional<Feedback> feedback = businessL.findFeedbackById(id);
        if (feedback.isEmpty()) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("feedback", feedback.get());
        return "admin-edit";
    }

    @PostMapping("/admin/edit/{id}")
    public String editFeedback(@PathVariable int id, @RequestParam("message") String message, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }

        Optional<Feedback> feedbackOptional = businessL.findFeedbackById(id);
        if (feedbackOptional.isPresent() && message != null && !message.trim().isEmpty()) {
            Feedback feedback = feedbackOptional.get();
            feedback.setMessage(message.trim());
            businessL.saveExistingFeedback(feedback);
        }

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteFeedback(@PathVariable int id, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }

        businessL.deleteFeedbackById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
