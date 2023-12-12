package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    
    @GetMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorTitle = "";
        String errorMessage = "";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // TODO: Put error messages and use the same page.
                errorTitle = "Not found";
                errorMessage = "The requested page does not exist.";
                
            }

            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorTitle = "Internal server error";
                errorMessage = "There are an internal server error. Our staff are solving the problem..";
            }
        
        }
        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
