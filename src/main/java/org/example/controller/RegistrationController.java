package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.dto.UserDTO;
import org.example.service.RegistrationService;
import org.example.util.DtoToEntity;
import org.example.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/auth")
@Log4j2
public class RegistrationController {
    private final
    UserValidator userValidator;

    private final
    RegistrationService registrationService;

    @Autowired
    public RegistrationController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String registrationUserGet(@ModelAttribute("userEntity") UserDTO person) {
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String registrationUserPost(@ModelAttribute("userEntity") @Valid UserDTO userEntity, BindingResult bindingResult) {
        userValidator.validate(userEntity,bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        log.info(userEntity);
        log.info(bindingResult.getAllErrors());
        userEntity.setPath("/photos/01.png");
        registrationService.registration(new DtoToEntity().convert(userEntity));
        return "redirect:/auth/login";
    }


}
