package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object errorMessage = model.getAttribute("jakarta.servlet.error.message");
        int errorCode = (int) request.getAttribute("jakarta.servlet.error.status_code");
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
        } else {
            model.addAttribute("error", "Unknown error occurred");
        }
        model.addAttribute("errorCode", errorCode);

        return "/info/error";
    }

}