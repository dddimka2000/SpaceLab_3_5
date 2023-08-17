package org.example.controller.adminController;

import lombok.extern.log4j.Log4j2;
import org.example.dto.CategoryDTO;
import org.example.dto.ClassificationDTO;
import org.example.entity.CategoryEntity;
import org.example.entity.ProductEntity;
import org.example.service.CategoryService;
import org.example.service.ClassificationService;
import org.example.service.ProductService;
import org.example.util.CategoryValidatorAndConvert.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.web.ServerProperties;


import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Controller
public class CategoryAdminController {
    @Value("${spring.regex}")
    String regex;
    @Value("${spring.pathImg}")
    String path;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final
    CategoryService categoryService;
    private final
    ClassificationService classificationService;
    private final
    CategoryValidator categoryValidator;

    private final
    ProductService productService;

    public CategoryAdminController(CategoryService categoryService, ClassificationService classificationService, CategoryValidator categoryValidator, ProductService productService) {
        this.categoryService = categoryService;
        this.classificationService = classificationService;
        this.categoryValidator = categoryValidator;
        this.productService = productService;
    }

    @GetMapping("/admin/classifications/{nameClassification}/create")
    public String createCategoryAdminPageShow(@PathVariable String nameClassification, @ModelAttribute("category") CategoryDTO categoryDTO, Model model) {
        model.addAttribute("nameClassification", nameClassification);
        return "/admin/classifications/categories/categoryCreate";
    }

    @PostMapping("/admin/classifications/{nameClassification}/create")
    public String createCategoryAdminPagePost(@PathVariable String nameClassification,
                                              @ModelAttribute("category") @Valid CategoryDTO categoryDTO,
                                              BindingResult bindingResult, Model model) {

        categoryValidator.validate(categoryDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("nameClassification", nameClassification);
            log.info(categoryDTO);
            if (categoryDTO.getFile().isEmpty() || categoryDTO.getFile() == null) {
                bindingResult.rejectValue("file", "", "Добавьте файл.");
            }
            return "/admin/classifications/categories/categoryCreate";
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setClassificationEntity(classificationService.findByName(nameClassification).get());
        categoryEntity.setDescription(categoryDTO.getDescription());
        categoryEntity.setStatus(categoryDTO.getStatus());
        categoryEntity.setName(categoryDTO.getName());
        if (categoryDTO.getFile() != null && !categoryDTO.getFile().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + categoryDTO.getFile().getOriginalFilename();
            try {
                categoryDTO.getFile().transferTo(new File(path + resultFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String mainImaginePath = regex + resultFilename;
            categoryEntity.setPath(mainImaginePath);
        }
        categoryService.save(categoryEntity);
        return "redirect:/admin/classifications/{nameClassification}";
    }
    String nameCategoryOld=null;
    @GetMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}")
    public String editCategoryAdminPageGet(@PathVariable String nameCategory, @PathVariable String nameClassification,
                                           Model model, @ModelAttribute("categoryDTO") CategoryDTO categoryDTO) {
        log.info("wwwwwwwwwwwwwwwwwww"+contextPath);
        nameCategoryOld=nameCategory;
        model.addAttribute("contextPath",contextPath);
        model.addAttribute("nameClassification", nameClassification);
        model.addAttribute("nameCategory", nameCategoryOld);
        Optional<CategoryEntity> categoryEntity = categoryService.findByName(nameCategoryOld);
        model.addAttribute("listProducts", productService.findAllProductsByCategoryEntity(categoryEntity.get()));
        categoryDTO.setName(categoryEntity.get().getName());
        categoryDTO.setDescription(categoryEntity.get().getDescription());
        categoryDTO.setStatus(categoryEntity.get().getStatus());
        categoryDTO.setPath(categoryEntity.get().getPath());
        model.addAttribute("path", categoryEntity.get().getPath());
        return "/admin/classifications/categories/categoryEdit";
    }

    @PostMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}")
    public String editCategoryAdminPagePOST(@PathVariable String nameCategory, @PathVariable String nameClassification,
                                            Model model, @ModelAttribute("categoryDTO") @Valid CategoryDTO categoryDTO, BindingResult bindingResult) {
        model.addAttribute("contextPath",contextPath);
        model.addAttribute("nameClassification", nameClassification);
        model.addAttribute("nameCategory", nameCategoryOld);
        Optional<CategoryEntity> categoryEntityOld = categoryService.findByName(nameCategoryOld);
        categoryValidator.validate(categoryDTO, bindingResult, nameCategoryOld);
        if (bindingResult.hasErrors()) {
            model.addAttribute("listProducts", productService.findAllProductsByCategoryEntity(categoryEntityOld.get()));
            model.addAttribute("path", categoryEntityOld.get().getPath());
            return "/admin/classifications/categories/categoryEdit";
        }
        CategoryEntity categoryEntity = categoryService.findByName(nameCategoryOld).get();
        categoryEntity.setClassificationEntity(classificationService.findByName(nameClassification).get());
        categoryEntity.setDescription(categoryDTO.getDescription());
        categoryEntity.setStatus(categoryDTO.getStatus());
        categoryEntity.setName(categoryDTO.getName());
        if (categoryDTO.getFile() != null && !categoryDTO.getFile().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + categoryDTO.getFile().getOriginalFilename();
            try {
                categoryDTO.getFile().transferTo(new File(path + resultFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String mainImaginePath = regex + resultFilename;
            categoryEntity.setPath(mainImaginePath);
        }
        categoryService.save(categoryEntity);
        return "redirect:/admin/classifications/{nameClassification}";
    }

    @DeleteMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}/delete/{nameProduct}")
    public ResponseEntity<String> deleteCategory(@PathVariable String nameCategory, @PathVariable String nameProduct, @PathVariable String nameClassification,
                                                 Model model) {
        model.addAttribute("contextPath",contextPath);
        model.addAttribute("nameClassification", nameClassification);
        model.addAttribute("nameCategory", nameCategoryOld);
        log.info("DeleteMapping"+ nameProduct+ "start");
        Optional<ProductEntity> productEntity = productService.findByName(nameProduct);
        if (productEntity.isPresent()) {
            productService.delete(productEntity.get());
            log.info("Category deleted successfully");
            return ResponseEntity.ok().body("Category deleted successfully");
        } else {
            log.error("Category deleted unsuccessfully");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}/getTableProducts")
    @ResponseBody
    public List<ProductEntity> getElementsTable(@PathVariable String nameClassification, @PathVariable String nameCategory) {
        List<ProductEntity> productEntityList =productService.findAllProductsByCategoryEntity(categoryService.findByName(nameCategory).get());
        return productEntityList;
    }

}
