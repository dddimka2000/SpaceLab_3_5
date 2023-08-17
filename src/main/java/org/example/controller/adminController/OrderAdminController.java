package org.example.controller.adminController;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.OrderTableDTO;
import org.example.entity.CategoryEntity;
import org.example.entity.ClassificationEntity;
import org.example.entity.OrderTableEntity;
import org.example.entity.ProductByOrderEntity;
import org.example.service.*;
import org.example.util.OrderValidator;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class OrderAdminController {
    final
    OrderTableService orderTableService;

    final
    CategoryService categoryService;
    final
    OrderValidator orderValidator;
    final
    UserEntityService userEntityService;
    final
    ProductByOrderService productByOrderService;
    final
    ClassificationService classificationService;
    final
    ProductService productService;


    public OrderAdminController(OrderTableService orderTableService, ClassificationService classificationService, CategoryService categoryService, ProductService productService, OrderValidator orderValidator, ProductByOrderService productByOrderService, UserEntityService userEntityService) {
        this.orderTableService = orderTableService;
        this.classificationService = classificationService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderValidator = orderValidator;
        this.productByOrderService = productByOrderService;
        this.userEntityService = userEntityService;
    }
    int pageSize = 3;



    @GetMapping("/admin/orders")
    public String showOrdersAdmin(@RequestParam(defaultValue = "0", name = "page") Integer page,
                                  @RequestParam(defaultValue = "", name = "orderId") String orderId,
                                  Model model) {
        log.info("orderId " + orderId);
        Page<OrderTableEntity> orderTablePage = orderTableService.findByIdContaining(orderId, page, pageSize);
        List<OrderTableEntity> orderTableEntityList = new ArrayList<>();
        int totalPage = 0;
        if (orderTablePage != null) {
            totalPage = orderTablePage.getTotalPages();
            orderTableEntityList = orderTablePage.getContent();
        }
        model.addAttribute("orderTableEntityList", orderTableEntityList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPage);

        long count = orderTableService.countBy();
        String panelCount = "Показано " + (pageSize * page + 1) + "-" + (orderTableEntityList.size() + (pageSize * page)) + " из " + count;
        log.info(panelCount);
        model.addAttribute("panelCount", panelCount);
        model.addAttribute("orderId", orderId);

        return "/admin/orders/showOrders";
    }

    @PostMapping("/admin/orders")
    public String nextPageUsers(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(name = "orderId") List<String> orderId) {
        String orderIdDecoding = "";
        try {
            orderIdDecoding = UriUtils.encodeQueryParam((orderId.get(orderId.size() - 1)), StandardCharsets.UTF_8);
        } catch (IndexOutOfBoundsException e) {
        }
        log.warn(orderIdDecoding);
        log.warn(orderId);
        return "redirect:/admin/orders?page=" + page + "&orderId=" + orderIdDecoding;
    }

    @GetMapping("/admin/orders/create")
    public String createOrderAdmin(@ModelAttribute("orderTableDTO") OrderTableDTO orderTableDTO, Model model) {
        model.addAttribute("classifications", classificationService.findAllClassificationEntities().stream().map(classificationEntity -> classificationEntity.getName()).collect(Collectors.toList()));
        return "/admin/orders/createOrder";
    }

    @GetMapping("/admin/orders/{id}")
    public String editOrderAdmin(@ModelAttribute("orderTableDTO") OrderTableDTO orderTableDTO, Model model, @PathVariable Integer id) {
        OrderTableEntity orderTableEntity=orderTableService.findById(id).get();


        orderTableDTO.setProducts(productByOrderService.findAllByOrderTableEntity(orderTableEntity).stream()
                .map(s->s.getProductEntity().getName()).toList());
        log.info(orderTableDTO.getProducts());
        orderTableDTO.setQuantity(productByOrderService.findAllByOrderTableEntity(orderTableEntity).stream()
                .map(s->String.valueOf(s.getCountProducts())).toList());
        log.info(orderTableDTO.getQuantity());


        orderTableDTO.setLogin(orderTableEntity.getUserEntity().getLogin());
//        model.addAttribute("idOrder"+id);
        orderTableDTO.setComment(orderTableEntity.getComment());
        orderTableDTO.setStatus(orderTableEntity.getStatus());
        model.addAttribute("classifications", classificationService.
        findAllClassificationEntities().stream().map(classificationEntity -> classificationEntity.getName()).collect(Collectors.toList()));
        return "/admin/orders/editOrder";
    }

    @ResponseBody
    @PutMapping("/admin/orders/{id}")
    public ResponseEntity<Map<String, Object>> editOrderAdminPost(@ModelAttribute @Valid OrderTableDTO orderTableDTO,
                                                                  BindingResult bindingResult, @PathVariable Integer id) {
        log.info(orderTableDTO);
        orderTableDTO.setProducts(orderTableDTO.getProducts().stream()
                .map(product -> product.replaceAll("\\[", "")
                        .replaceAll("\"", "")
                        .replaceAll("]", ""))
                .collect(Collectors.toList()));

        orderTableDTO.setQuantity(orderTableDTO.getQuantity().stream()
                .map(s -> s.replaceAll("\\[", "")
                        .replaceAll("\"", "")
                        .replaceAll("]", ""))
                .collect(Collectors.toList()));

        orderValidator.validate(orderTableDTO, bindingResult);
        Map response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            }
            fieldErrors.forEach((s, s2) -> log.info(s + " = " + s2));
            response.put("fields", fieldErrors);
            return ResponseEntity.badRequest().body(response);
        }
        OrderTableEntity orderTableEntity = orderTableService.findById(id).get();
        orderTableEntity.setStatus(orderTableDTO.getStatus());
        orderTableEntity.setComment(orderTableDTO.getComment());
        orderTableEntity.setUserEntity(userEntityService.findByLogin(orderTableDTO.getLogin()).get());
        orderTableService.save(orderTableEntity);
        productByOrderService.findAllByOrderTableEntity(orderTableEntity).forEach(s->productByOrderService.delete(s));
        AtomicInteger num=new AtomicInteger(0);
        orderTableDTO.getQuantity().stream().forEach(s -> {
            ProductByOrderEntity productByOrderEntity= new ProductByOrderEntity();
            productByOrderEntity.setOrderTableEntity(orderTableEntity);
            productByOrderEntity.setCountProducts(Integer.parseInt(s));
            productByOrderEntity.setProductEntity(productService.findByName(orderTableDTO.getProducts().get(num.get())).get());
            num.incrementAndGet();
            productByOrderService.save(productByOrderEntity);
        });

        return ResponseEntity.ok().body(Map.of("success", true, "message", "Product created successfully"));
    }


    @ResponseBody
    @PostMapping("/admin/orders/create")
    public ResponseEntity<Map<String, Object>> createOrderAdminPost(@ModelAttribute @Valid OrderTableDTO orderTableDTO,
                                                                    BindingResult bindingResult) {
        log.info(orderTableDTO);
        orderTableDTO.setProducts(orderTableDTO.getProducts().stream()
                .map(product -> product.replaceAll("\\[", "")
                        .replaceAll("\"", "")
                        .replaceAll("]", ""))
                .collect(Collectors.toList()));

        orderTableDTO.setQuantity(orderTableDTO.getQuantity().stream()
                .map(s -> s.replaceAll("\\[", "")
                        .replaceAll("\"", "")
                        .replaceAll("]", ""))
                .collect(Collectors.toList()));

        orderValidator.validate(orderTableDTO, bindingResult);
        Map response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            }
            fieldErrors.forEach((s, s2) -> log.info(s + " = " + s2));
            response.put("fields", fieldErrors);
            return ResponseEntity.badRequest().body(response);
        }
        OrderTableEntity orderTableEntity = new OrderTableEntity();
        orderTableEntity.setStatus(orderTableDTO.getStatus());
        orderTableEntity.setComment(orderTableDTO.getComment());
        orderTableEntity.setUserEntity(userEntityService.findByLogin(orderTableDTO.getLogin()).get());
        orderTableEntity.setDateTime(LocalDateTime.now());
        orderTableService.save(orderTableEntity);
        AtomicInteger num=new AtomicInteger(0);
        orderTableDTO.getQuantity().stream().forEach(s -> {
            ProductByOrderEntity productByOrderEntity= new ProductByOrderEntity();
            productByOrderEntity.setOrderTableEntity(orderTableEntity);
            productByOrderEntity.setCountProducts(Integer.parseInt(s));
            productByOrderEntity.setProductEntity(productService.findByName(orderTableDTO.getProducts().get(num.get())).get());
            num.incrementAndGet();
            productByOrderService.save(productByOrderEntity);
        });

        return ResponseEntity.ok().body(Map.of("success", true, "message", "Product created successfully"));
    }


    @GetMapping("/admin/orders/create/getCategories")
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

    @GetMapping("/admin/orders/create/getProducts")
    @ResponseBody
    public List<String> adminProductsShowByCategory(@RequestParam(name = "selectedValue") String selectedValue) {
        Optional<CategoryEntity> categoryEntity = categoryService.findByName(selectedValue);
        List<String> productEntityList = null;
        if (categoryEntity.isPresent()) {
            productEntityList = productService.findAllProductsByCategoryEntity(categoryEntity.get()).stream().map(productEntity -> productEntity.getName()).collect(Collectors.toList());
            log.info(productEntityList);
        }
        return productEntityList;
    }
}
