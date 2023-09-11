package org.example.controller.publicController;

import lombok.extern.log4j.Log4j2;
import org.example.dto.CategoryDTO;
import org.example.entity.CategoryEntity;
import org.example.security.UserDetailsImpl;
import org.example.service.CategoryService;
import org.example.service.ClassificationService;
import org.example.service.FavoriteProductService;
import org.example.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class CatalogController {
    final
    ClassificationService classificationService;
    final
    CategoryService categoryService;
    final
    ProductService productService;
    private final
    FavoriteProductService favoriteProductService;


    public CatalogController(ClassificationService classificationService, CategoryService categoryService, ProductService productService, FavoriteProductService favoriteProductService) {
        this.classificationService = classificationService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.favoriteProductService = favoriteProductService;
    }

    @GetMapping("/catalog")
    public String showCatalog(Model model) {
        Map<String, List<CategoryEntity>> map = classificationService
                .findAllClassificationEntities().stream().filter(s -> s.getStatus() == true).collect(Collectors.toMap(classificationEntity -> classificationEntity.getName(),
                        classificationEntity -> categoryService.findAllCategoriesByClassificationEntity(classificationEntity).stream().filter(s->s.getStatus()==true).collect(Collectors.toList())));
        model.addAttribute("carouselItems", classificationService
                .findAllClassificationEntities().stream().filter(s -> s.getStatus() == true).map(s -> s.getPath()).toList());
        model.addAttribute("categoryMap", map);
        log.info(map.size());
        return "/public/catalog";
    }

    @GetMapping("/catalog/{idCategory}")
    public String showProductsByCatalog(Model model, @PathVariable Integer idCategory, @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
        model.addAttribute("favoriteProduct",favoriteProductService.findAllFavoriteProductsByUserEntity(userDetails.getUserEntity())
                .stream().map(s->s.getProductEntity().getId()).toList());
        }
        CategoryEntity categoryEntity = categoryService.findById(idCategory).get();
        categoryDTO.setDescription(categoryEntity.getDescription());
        categoryDTO.setName(categoryEntity.getName());
        categoryDTO.setPath(categoryEntity.getPath());
        model.addAttribute("products", productService.findAllProductsByCategoryEntity(categoryEntity).stream().filter(s->s.getStatus()==true));
        return "/public/productsByCatalog";
    }


}