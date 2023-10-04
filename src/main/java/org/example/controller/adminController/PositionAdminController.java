package org.example.controller.adminController;

import lombok.extern.log4j.Log4j2;
import org.example.entity.UserEntity;
import org.example.entity.UserRole;
import org.example.security.UserDetailsImpl;
import org.example.service.UserEntityService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@Log4j2
//@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasAuthority('ADMIN')")
public class PositionAdminController {
    final
    UserEntityService userEntityService;

    public PositionAdminController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

int size=10;
    @GetMapping("/admin/positions")
    public String adminPositionShow(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<UserEntity> userPage = userEntityService.findAllUsersPage(page, size);
        List<UserEntity> users = userPage.getContent();
        List<String> roles = new ArrayList<>();
        getPage = page;
        users.forEach(userEntity -> roles.add(userEntity.getRoles().iterator().next().getAuthority()));
        if (hasError) {
            model.addAttribute("userError", "Вы не можете изменить свою роль");
        }
        hasError = false;
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        long count = userEntityService.countBy();
        String panelCount = "Показано " + (size * page + 1) + "-" + (users.size() + (size * page)) + " из " + count;
        log.info(panelCount);
        model.addAttribute("panelCount", panelCount);
        model.addAttribute("sizePageItems", size);
        return "/admin/positions";
    }

    int getPage = 0;

    @PostMapping
    public String nextPageUsers(@RequestParam("page") int page) {
        return "redirect:/admin/positions?page=" + page;
    }

    boolean hasError = false;

    @PostMapping("/admin/positions/update")
    public String changeRolesAdminPanel(@RequestParam Map<String, String> roles,
                                        @AuthenticationPrincipal UserDetailsImpl userEntity) {
        log.info(roles);
        UserEntity user = null;
        for (Map.Entry<String, String> entry : roles.entrySet()) {
            log.info(entry.getKey());
            if (!entry.getKey().equals("_csrf")) {
                if (!userEntity.getUserEntity().getLogin().equals(entry.getKey())) {
                    user = userEntityService.findByLogin(entry.getKey()).get();
                    Set<UserRole> userRoles = new HashSet<>();
                    switch (entry.getValue()) {
                        case "ADMIN":
                            userRoles.add(UserRole.ADMIN);
                            break;
                        case "MODERATOR":
                            userRoles.add(UserRole.MODERATOR);
                            break;
                        case "USER":
                            userRoles.add(UserRole.USER);
                            break;
                    }
                    if (user != null) {
                        user.setRoles(userRoles);
                        userEntityService.save(user);
                    }
                } else {
                    if (!entry.getValue().equals(userEntity.getUserEntity().getRoles().stream().iterator().next().getAuthority())) {
                        log.info(userEntity.getUserEntity().getRoles().stream().iterator().next().getAuthority());
                        hasError = true;
                    }
                }
            }
        }
        return "redirect:/admin/positions?page="+getPage;
    }
}