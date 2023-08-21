package org.example.controller.adminController;

import lombok.extern.log4j.Log4j2;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.example.util.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
@Log4j2
@Controller
public class ProductSecondAdminController {
    @Value("${spring.regex}")
    String regex;
    @Value("${spring.pathImg}")
    String path;
    private final
    CategoryService categoryService;

    private final
    ProductService productService;
    final
    ProductValidator productValidator;

    public ProductSecondAdminController(ProductService productService, CategoryService categoryService, ProductValidator productValidator) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productValidator = productValidator;
    }

    @GetMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}/create")
    public String createProductSecondAdminPageGet(@PathVariable String nameCategory, @PathVariable String nameClassification,
                                                  @ModelAttribute("productDTO") ProductDto productDTO, Model model) {
        model.addAttribute("nameCategory",nameCategory);
        model.addAttribute("nameClassification",nameClassification);

        return "/admin/classifications/categories/products/createProduct";
    }
    @PostMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}/create")
    public String createProductSecondAdminPagePost(@PathVariable String nameCategory, @PathVariable String nameClassification,
                                                   @ModelAttribute("productDTO") @Valid ProductDto productDTO, BindingResult bindingResult,
                                                   Model model) {
        model.addAttribute("nameCategory",nameCategory);
        model.addAttribute("nameClassification",nameClassification);
        productValidator.validate(productDTO,bindingResult);
        if (productDTO.getFile().isEmpty()||productDTO.getFile()==null){
            bindingResult.rejectValue("file","","Добавьте картинку.");
        }

        if (bindingResult.hasErrors()){
            return "/admin/classifications/categories/products/createProduct";
        }
        ProductEntity productEntity=new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setStatus(productDTO.getStatus());
        productEntity.setCategoryEntity(categoryService.findByName(nameCategory).get());
        if (productDTO.getFile() != null && !productDTO.getFile().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + productDTO.getFile().getOriginalFilename();
            try {
                productDTO.getFile().transferTo(new File(path + resultFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String mainImaginePath = regex + resultFilename;
            productEntity.setPath(mainImaginePath);
        }
        productService.save(productEntity);
        return "redirect:/admin/classifications/{nameClassification}/edit/{nameCategory}";
    }

    String nameProductLink=null;

    @GetMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}/edit/{nameProduct}")
    public String showProductSecondAdminPageGet(@PathVariable String nameCategory, @PathVariable String nameClassification,
                                                @ModelAttribute("productDTO") ProductDto productDTO, @PathVariable String nameProduct) {
        nameProductLink=nameProduct;
        ProductEntity productEntity=productService.findByName(nameProduct).get();
        productDTO.setName(productEntity.getName());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setStatus(productEntity.getStatus());
        productDTO.setPath(productEntity.getPath());

        return "/admin/classifications/categories/products/productEdit";
    }

    @PostMapping("/admin/classifications/{nameClassification}/edit/{nameCategory}/edit/{nameProduct}")
    public String editProductSecondAdminPagePost(@PathVariable String nameCategory, @PathVariable String nameClassification,
                                                 @ModelAttribute("productDTO") @Valid ProductDto productDTO, BindingResult bindingResult, @PathVariable String nameProduct,
                                                 Model model) {
        log.info("nameProduct: "+nameProductLink);
        ProductEntity productEntityOld=productService.findByName(nameProductLink).get();
        model.addAttribute("nameCategory",nameCategory);
        model.addAttribute("nameClassification",nameClassification);
        productValidator.validate(productDTO,bindingResult,nameProductLink);
        if (bindingResult.hasErrors()){
            productDTO.setPath(productEntityOld.getPath());
            return "/admin/classifications/categories/products/productEdit";
        }
        ProductEntity productEntity=productService.findByName(nameProductLink).get();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setStatus(productDTO.getStatus());
        if (productDTO.getFile() != null && !productDTO.getFile().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + productDTO.getFile().getOriginalFilename();
            try {
                productDTO.getFile().transferTo(new File(path + resultFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String mainImaginePath = regex + resultFilename;
            productEntity.setPath(mainImaginePath);
        }
        productService.save(productEntity);

        return "redirect:/admin/classifications/{nameClassification}/edit/{nameCategory}";
    }
}
