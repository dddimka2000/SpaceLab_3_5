package org.example.controller.adminController;

import lombok.extern.log4j.Log4j2;
import org.example.dto.ProductDto;
import org.example.dto.ProductWithChoiceDto;
import org.example.entity.ClassificationEntity;
import org.example.entity.ProductEntity;
import org.example.service.CategoryService;
import org.example.service.ClassificationService;
import org.example.service.ProductService;
import org.example.util.ProductValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class ProductAdminController {
    @Value("${spring.regex}")
    String regex;
    @Value("${spring.pathImg}")
    String path;

    private final
    ClassificationService classificationService;

    private final
    ProductService productService;

    private final
    CategoryService categoryService;
    private final
    ProductValidator productValidator;

    public ProductAdminController(ClassificationService classificationService, ProductService productService, CategoryService categoryService, ProductValidator productValidator) {
        this.classificationService = classificationService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.productValidator = productValidator;
    }

    int size=2;
    @GetMapping("/admin/products")
    public String adminProducts(@RequestParam(defaultValue = "0", name = "page") Integer page,
                                @RequestParam(defaultValue = "", name = "productName") String productName,
                                Model model) {
        Page<ProductEntity> productsPage = productService.findByNameContainingIgnoreCase(productName, page, size);
        List<ProductEntity> products = productsPage.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());

        long count = productService.countBy();
        String panelCount = "Показано " + (size * page + 1) + "-" + (products.size() + (size * page)) + " из " + count;
        log.info(panelCount);
        model.addAttribute("panelCount", panelCount);

        model.addAttribute("productName", productName);
        return "/admin/products/productsAdmin";
    }

    @PostMapping("/admin/products")
    public String nextPageUsers(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(name = "productName") List<String> productName) {
        String productNameDecoding = "";
        productNameDecoding = UriUtils.encodeQueryParam(productName.get(productName.size() - 1), StandardCharsets.UTF_8);
        log.warn(productNameDecoding);
        log.warn(productName);
        return "redirect:/admin/products?page=" + page + "&productName=" + productNameDecoding;
    }


    @GetMapping("/admin/products/create")
    public String adminProductsCreate(@ModelAttribute("productWithChoiceDto") ProductDto productDto, Model model) {
        model.addAttribute("classifications", classificationService.findAllClassificationEntities().stream().map(classificationEntity -> classificationEntity.getName()).collect(Collectors.toList()));
        model.addAttribute("contextPath", contextPath);
        return "/admin/products/createProductAdmin";
    }

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @ResponseBody
    @PostMapping("/admin/products/create/validate")
    public ResponseEntity<Map<String, Object>> adminProductsValidatePost(
            @ModelAttribute("productWithChoiceDto")
            @Valid ProductDto productDto,
            BindingResult bindingResult, Model model) {
        model.addAttribute("classifications", classificationService.findAllClassificationEntities().stream().map(classificationEntity -> classificationEntity.getName()).collect(Collectors.toList()));
        model.addAttribute("contextPath", contextPath);
        Map<String, Object> response = new HashMap<>();
        log.info(bindingResult.getFieldErrors());
        log.info("adminProductsValidatePost+start");
        log.info(productDto);
        productValidator.validate(productDto, bindingResult, productDto.getSelectedClassification(), productDto.getSelectedCategory());
        if (productDto.getFile() == null) {
            bindingResult.rejectValue("file", "", "Пустой файл");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            }
            fieldErrors.forEach((s, s2) -> log.info(s + " = " + s2));
            response.put("fields", fieldErrors);
            return ResponseEntity.badRequest().body(response);
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setStatus(productDto.getStatus());
        productEntity.setCategoryEntity(categoryService.findByName(productDto.getSelectedCategory()).get());
        if (productDto.getFile() != null && !productDto.getFile().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + productDto.getFile().getOriginalFilename();
            try {
                productDto.getFile().transferTo(new File(path + resultFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String mainImaginePath = regex + resultFilename;
            productEntity.setPath(mainImaginePath);
        }
        productService.save(productEntity);
        return ResponseEntity.ok().body(Map.of("success", true, "message", "Product created successfully"));
    }

    @GetMapping("/admin/products/create/getCategories")
    @ResponseBody
    public List<String> adminProductsEditShow(@RequestParam(name = "selectedValue") String selectedValue) {
        Optional<ClassificationEntity> classificationEntityOptional = classificationService.findByName(selectedValue);
        List<String> categoryEntityList = null;
        if (classificationEntityOptional.isPresent()) {
            categoryEntityList = categoryService.findAllCategoriesByClassificationEntity(classificationEntityOptional.get()).stream().map(categoryEntity -> categoryEntity.getName()).collect(Collectors.toList());
            log.info(categoryEntityList);
        }
        return categoryEntityList;
    }


    @GetMapping("/admin/products/{id}")
    public String adminProductsEditShow(@ModelAttribute("productWithChoiceDto") ProductDto productWithChoiceDto, @PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("contextPath", contextPath);
        ProductEntity productEntity = productService.findById(id).get();
        productWithChoiceDto.setName(productEntity.getName());
        productWithChoiceDto.setDescription(productEntity.getDescription());
        productWithChoiceDto.setPrice(productEntity.getPrice());
        productWithChoiceDto.setStatus(productEntity.getStatus());
        productWithChoiceDto.setSelectedCategory(productEntity.getCategoryEntity().getName());
        productWithChoiceDto.setSelectedClassification(productEntity.getCategoryEntity().getClassificationEntity().getName());
        productWithChoiceDto.setPath(productEntity.getPath());
        model.addAttribute("classifications", classificationService.findAllClassificationEntities().stream().map(classificationEntity -> classificationEntity.getName()).collect(Collectors.toList()));
        model.addAttribute("categories", categoryService.findAllCategoriesByClassificationEntity(productEntity.getCategoryEntity().getClassificationEntity()).stream().map(categoryEntity -> categoryEntity.getName()).collect(Collectors.toList()));
        log.info(categoryService.findAllCategoriesByClassificationEntity(productEntity.getCategoryEntity().getClassificationEntity()));
        log.info(productWithChoiceDto);
        return "/admin/products/editProductAdmin";
    }

    @DeleteMapping("/admin/products/delete/{id}")
    public ResponseEntity<String> deleteProductAdmin(@PathVariable Integer id) {
        Optional<ProductEntity> productEntity = productService.findById(id);
        if (productEntity.isPresent()) {
            productService.delete(productEntity.get());
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<Map<String, Object>> adminProductsEditPut(@ModelAttribute("productWithChoiceDto")
                                                                     @Valid ProductDto productDto,
                                                                     BindingResult bindingResult, @PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        ProductEntity productEntity = productService.findById(id).get();
        if (productDto.getSelectedCategory() == null) {
            productDto.setSelectedCategory(productEntity.getCategoryEntity().getName());
        }
        if (productDto.getSelectedClassification() == null) {
            productDto.setSelectedClassification(productEntity.getCategoryEntity().getClassificationEntity().getName());
        }
        log.info(productDto);

        productValidator.validate(productDto, bindingResult, productDto.getSelectedClassification(), productDto.getSelectedCategory(), productEntity.getName());
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            }
            fieldErrors.forEach((s, s2) -> log.info(s + " = " + s2));
            response.put("fields", fieldErrors);
            return ResponseEntity.badRequest().body(response);
        }
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setStatus(productDto.getStatus());
        productEntity.setCategoryEntity(categoryService.findByName(productDto.getSelectedCategory()).get());
        if (productDto.getFile() != null && !productDto.getFile().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + productDto.getFile().getOriginalFilename();
            try {
                productDto.getFile().transferTo(new File(path + resultFilename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String mainImaginePath = regex + resultFilename;
            productEntity.setPath(mainImaginePath);
        }
        productService.save(productEntity);
        return ResponseEntity.ok().body(Map.of("success", true, "message", "Product created successfully"));
    }
}
