package org.example.controller.adminController;

import lombok.extern.log4j.Log4j2;
import org.example.entity.FavoriteProductEntity;
import org.example.entity.UserEntity;
import org.example.entity.UserRole;
import org.example.service.FavoriteProductService;
import org.example.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Log4j2
public class MainAdminPage {
    private final
    UserEntityService userEntityService;

    final
    FavoriteProductService favoriteProductService;

    public MainAdminPage(UserEntityService userEntityService, FavoriteProductService favoriteProductService) {
        this.userEntityService = userEntityService;
        this.favoriteProductService = favoriteProductService;
    }

    @GetMapping
    public String showMainAdminPage(Model model) {
        List<UserEntity> list = userEntityService.findAllUsers();
        long userSize = list.stream().filter(userEntity -> userEntity.getRoles().contains(UserRole.USER)).count();
        long moderatorSize = list.stream().filter(userEntity -> userEntity.getRoles().contains(UserRole.MODERATOR)).count();
        long adminSize = list.stream().filter(userEntity -> userEntity.getRoles().contains(UserRole.ADMIN)).count();
        model.addAttribute("userSize", userSize);
        model.addAttribute("moderatorSize", moderatorSize);
        model.addAttribute("adminSize", adminSize);
        model.addAttribute("findBestSeven", favoriteProductService.findBestSeven());
        log.info(favoriteProductService.findBestSeven());

        return "admin/mainPageAdmin";
    }
}
