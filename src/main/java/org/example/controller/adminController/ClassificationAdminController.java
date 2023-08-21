package org.example.controller.adminController;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.ClassificationDTO;
import org.example.entity.CategoryEntity;
import org.example.entity.ClassificationEntity;
import org.example.service.CategoryService;
import org.example.service.ClassificationService;
import org.example.util.ClassificationValidatorAndConvert.ClassificationDTOConvert;
import org.example.util.ClassificationValidatorAndConvert.ClassificationValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/admin/classifications")
public class ClassificationAdminController {
    String OldName = null;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final
    CategoryService categoryService;

    @Value("${spring.regex}")
    String regex;
    @Value("${spring.pathImg}")
    String path;
    private final
    ClassificationValidator classificationValidator;

    private final
    ClassificationService classificationService;

    public ClassificationAdminController(ClassificationValidator classificationValidator, ClassificationService classificationService, CategoryService categoryService) {
        this.classificationValidator = classificationValidator;
        this.classificationService = classificationService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showClassificationsAdminPage(Model model) {
        model.addAttribute("objects", classificationService.findAllClassificationEntities());
        return "/admin/classifications/classificationsAdmin";
    }

    @GetMapping("/create")
    public String createClassificationAdminPageShow(@ModelAttribute("classificationDTO") ClassificationDTO classificationDTO) {
        return "/admin/classifications/classificationCreate";
    }

    @PostMapping("/create")
    public String createClassificationAdminPagePost(@ModelAttribute("classificationDTO") @Valid ClassificationDTO classificationDTO, BindingResult bindingResult) {
        log.info("ClassificationController-createClassificationAdminPagePost start");
        if (classificationDTO.getFile() == null && classificationDTO.getFile().isEmpty()) {
            bindingResult.rejectValue("file", "", "Пожалуйста, выберите файл для загрузки.");
        }
        classificationValidator.validate(classificationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/classifications/classificationCreate";
        }
        log.warn(classificationDTO);
        ClassificationDTOConvert classificationDTOConvert = new ClassificationDTOConvert();
        ClassificationEntity classificationEntity = classificationDTOConvert.convertToEntity(classificationDTO, regex, path, new ClassificationEntity());
        classificationService.save(classificationEntity);
        return "redirect:/admin/classifications";
    }



    @GetMapping("/{name}")
    public String editClassificationAdminPageShow(@PathVariable String name, @ModelAttribute("classificationDTO") ClassificationDTO classificationDTO,
                                                  Model model) {
        log.info("wwwwwwwwwwwwwwwwwwwwwww"+contextPath);
        model.addAttribute("contextPath", contextPath);
        OldName = name;
        model.addAttribute("oldName", OldName);
        Optional<ClassificationEntity> classificationEntity = classificationService.findByName(OldName);
        if (classificationEntity.isPresent()) {
            classificationDTO.setName(classificationEntity.get().getName());
            classificationDTO.setDescription(classificationEntity.get().getDescription());
            classificationDTO.setStatus(classificationEntity.get().getStatus());
            model.addAttribute("path", classificationEntity.get().getPath());
            List<CategoryEntity> categoryEntityList = categoryService.findAllCategoriesByClassificationEntity(classificationEntity.get());
            model.addAttribute("categories", categoryEntityList);
        }
        return "/admin/classifications/classificationEdit";
    }

    @PostMapping("/{name}")
    public String editClassificationAdminPagePost(@PathVariable String name, @ModelAttribute("classificationDTO") @Valid ClassificationDTO classificationDTO, BindingResult bindingResult
            , Model model) {
        model.addAttribute("oldName", OldName);
        model.addAttribute("contextPath", contextPath);
        model.addAttribute("path", classificationService.findByName(OldName).get().getPath());
        List<CategoryEntity> categoryEntityList = categoryService.findAllCategoriesByClassificationEntity(classificationService.findByName(OldName).get());
        model.addAttribute("categories", categoryEntityList);

        log.info("ClassificationController-editClassificationAdminPagePost start");
        classificationValidator.validate(classificationDTO, bindingResult, OldName);

        if (bindingResult.hasErrors()) {
            return "/admin/classifications/classificationEdit";
        }
        log.warn(classificationDTO);
        ClassificationDTOConvert classificationDTOConvert = new ClassificationDTOConvert();

        ClassificationEntity classificationEntity = classificationService.findByName(OldName).get();
        classificationEntity = classificationDTOConvert.convertToEntity(classificationDTO, regex, path, classificationEntity);
        classificationService.save(classificationEntity);
        return "redirect:/admin/classifications";
    }

    @DeleteMapping("/{nameClassification}/delete/{nameCategory}")
    public ResponseEntity<String> deleteCategory(@PathVariable String nameClassification, @PathVariable String nameCategory,Model model) {
        model.addAttribute("oldName", OldName);
        model.addAttribute("contextPath", contextPath);
        log.info("DeleteMapping" + nameClassification + "start");
        Optional<CategoryEntity> categoryEntity = categoryService.findByName(nameCategory);
        if (categoryEntity.isPresent()) {
            categoryService.delete(categoryEntity.get());
            log.info("Category deleted successfully");
            return ResponseEntity.ok().body("Category deleted successfully");
        } else {
            log.error("Category deleted unsuccessfully");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{nameClassification}/getElementsTable")
    @ResponseBody
    public List<CategoryEntity> getElementsTable(@PathVariable String nameClassification) {
        List<CategoryEntity> categoryEntityList = categoryService.findAllCategoriesByClassificationEntity(classificationService.findByName(nameClassification).get());
        return categoryEntityList;
    }
}
