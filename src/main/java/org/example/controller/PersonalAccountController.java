package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.dto.UploadImg;
import org.example.dto.UserDTO;
import org.example.entity.UserEntity;
import org.example.security.UserDetailsImpl;
import org.example.service.UserEntityService;
import org.example.util.UserValidatorAndConvert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
@RequestMapping("/personal_account")
@Controller
public class PersonalAccountController {
    private final
    UserValidator userValidator;

    private final
    EmailUserValidator emailUserValidator;

    private final
    LoginUserValidator loginUserValidator;
    private final
    TelephoneUserValidator telephoneUserValidator;
    private final
    PasswordValidator passwordValidator;

    private final
    UserEntityService userEntityService;
    private final
    UserDtoToEntity userDtoToEntity;
    private final
    PhotoValidator photoValidator;

    @Autowired
    public PersonalAccountController(UserValidator userValidator, UserEntityService userEntityService, UserDtoToEntity userDtoToEntity, EmailUserValidator emailUserValidator, LoginUserValidator loginUserValidator, TelephoneUserValidator telephoneUserValidator, PasswordValidator passwordValidator, PhotoValidator photoValidator) {
        this.userValidator = userValidator;
        this.userEntityService = userEntityService;
        this.userDtoToEntity = userDtoToEntity;
        this.emailUserValidator = emailUserValidator;
        this.loginUserValidator = loginUserValidator;
        this.telephoneUserValidator = telephoneUserValidator;
        this.passwordValidator = passwordValidator;
        this.photoValidator = photoValidator;
    }
    @Value("${spring.regex}")
    String regex;
    @Value("${spring.pathImg}")
    String path;

    @GetMapping
    public String showPersonalAccount(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @ModelAttribute("userEntity") UserDTO person, Model model,
                                      @ModelAttribute("uploadFile") UploadImg uploadFile) {
        model.addAttribute("userEntity", userDetails.getUserEntity());
        model.addAttribute("passwordRepeat", true);
        return "/info/personalAccount";
    }

    @PostMapping("/uploader/path")
    public String updateUserPublicPath(@ModelAttribute("uploadFile") @Valid UploadImg uploadFile,
                                       BindingResult bindingResult,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       Model model) {
        model.addAttribute("userEntity",userDetails.getUserEntity());
        log.info(bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            log.error("updateUserPublicPath-PersonalAccountController error");
            return "/info/personalAccount";
        }
        photoValidator.validate(uploadFile,bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("updateUserPublicPath-PersonalAccountController error");
            return "/info/personalAccount";
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + uploadFile.getFile().getOriginalFilename();
        try {
            uploadFile.getFile().transferTo(new File(path + resultFilename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String mainImaginePath = regex + resultFilename;
        UserEntity userEntity=userDetails.getUserEntity();
        userEntity.setPath(mainImaginePath);
        userEntityService.save(userEntity);
        return "redirect:/personal_account";
    }

    @PostMapping
    public String updateUserPublic(@ModelAttribute("userEntity") @Valid UserDTO userDTO, BindingResult bindingResult,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @RequestParam("passwordRepeat") Optional<String> passwordRepeat,
                                   @RequestParam("password")  Optional<String> password,
                                   Errors errors,
                                   Model model,
                                   @ModelAttribute("uploadFile") UploadImg uploadFile) {
        userDTO.setPass(userDetails.getUserEntity().getPass());
        if (!passwordRepeat.get().isBlank() || !password.get().isBlank()) {
            userDTO.setPass(password.get());
            passwordValidator.validate(userDTO, errors);
            log.info("passwordRepeat.isPresent()||password.isPresent()");
            log.info(passwordRepeat.get());
            log.info(password.get());
            if (!password.get().equals(passwordRepeat.get())) {
                model.addAttribute("passwordRepeat", false);
                bindingResult.rejectValue("pass", "", "Пароли не совпадают");
            }

        }

        log.info("updateUserPublic" + userDTO);
        if (!userDetails.getUserEntity().getEmail().equals(userDTO.getEmail())) {
            emailUserValidator.validate(userDTO, bindingResult);
        }
        if (!userDetails.getUserEntity().getTelephone().equals(userDTO.getTelephone())) {
            telephoneUserValidator.validate(userDTO, bindingResult);
        }
        if (!userDetails.getUserEntity().getLogin().equals(userDTO.getLogin())) {
            loginUserValidator.validate(userDTO, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            password= Optional.of("");
            passwordRepeat= Optional.of("");
            log.error("updateUserPublic-PersonalAccountController error");
            model.addAttribute("passwordRepeat", true);
//            model.addAttribute("userEntity",dtoToEntity.convertToDto(userDetails.getUserEntity()));
            return "/info/personalAccount";
        }
        UserEntity userEntity = userDtoToEntity.convert(userDTO, userDetails.getUserEntity());
        userEntityService.save(userEntity);
        if (!password.get().isBlank()) {
            model.addAttribute("changePass", true);
        }
        return "/info/personalAccount";
    }
}
