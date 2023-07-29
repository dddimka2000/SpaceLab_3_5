package org.example.controller.adminController;

import lombok.extern.log4j.Log4j2;
import org.example.entity.UserEntity;
import org.example.entity.UserRole;
import org.example.service.UserEntityService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class PositionController {
    final
    UserEntityService userEntityService;

    public PositionController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/admin/positions")
    public String adminPositionShow(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<UserEntity> userPage = userEntityService.findAllUsersPage(page, 3);
        List<UserEntity> users = userPage.getContent();
        List<String> roles=new ArrayList<>();
        users.forEach(userEntity -> roles.add(userEntity.getRoles().iterator().next().getAuthority()));
        model.addAttribute("roles", roles);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        return "/admin/positions";
    }

    @PostMapping
    public String nextPageUsers(@RequestParam("page") int page) {
        return "redirect:/admin/positions?page=" + page;
    }

    int num = 1;

    @PostMapping("/admin/positions/upload")
    public String changeRolesAdminPanel(@RequestParam("choice") List<Optional<Integer>> roles, @RequestParam("loginHidden") List<String> login) {
        for (Optional<Integer> role : roles) {
            num++;
            if (role.isPresent()) {
                Optional<UserEntity> userEntity = userEntityService.findByLogin(login.get(num));
                switch (role.get()) {
                    case (1):
                        userEntity.get().setRoles(Collections.singleton(UserRole.USER));
                        break;
                    case (9):
                        Set<UserRole> userRoleSetMODERATOR = new HashSet<>();
                        userRoleSetMODERATOR.add(UserRole.MODERATOR);
                        userRoleSetMODERATOR.add(UserRole.USER);
                        userEntity.get().setRoles(userRoleSetMODERATOR);
                        break;
                    case (10):
                        Set<UserRole> userRoleSetAdmin = new HashSet<>();
                        userRoleSetAdmin.add(UserRole.ADMIN);
                        userRoleSetAdmin.add(UserRole.MODERATOR);
                        userRoleSetAdmin.add(UserRole.USER);
                        userEntity.get().setRoles(userRoleSetAdmin);
                        break;
                }
                userEntityService.save(userEntity.get());
            }

        }
        return "/admin/positions";
    }
}