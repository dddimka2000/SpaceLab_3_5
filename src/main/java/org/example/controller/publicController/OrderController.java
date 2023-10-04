package org.example.controller.publicController;


import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.OrderTableDTO;
import org.example.entity.OrderTableEntity;
import org.example.entity.ProductByOrderEntity;
import org.example.security.UserDetailsImpl;
import org.example.service.*;
import org.example.util.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
public class OrderController {
    final
    OrderValidator orderValidator;
    final
    UserEntityService userEntityService;
    final
    OrderTableService orderTableService;
    final
    ProductByOrderService productByOrderService;
    final
    ProductService productService;

    public OrderController(OrderValidator orderValidator, UserEntityService userEntityService, OrderTableService orderTableService, ProductService productService, ProductByOrderService productByOrderService, BasketItemService basketItemService) {
        this.orderValidator = orderValidator;
        this.userEntityService = userEntityService;
        this.orderTableService = orderTableService;
        this.productService = productService;
        this.productByOrderService = productByOrderService;
        this.basketItemService = basketItemService;
    }

    int pageSize = 10;

    private final
    BasketItemService basketItemService;

    @GetMapping("/orders/{orderId}")
    public String showOrdersByUser(@ModelAttribute("orderTableDTO") OrderTableDTO orderTableDTO, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Integer orderId) {

        if (orderTableService.findById(orderId).isPresent() && orderTableService.findById(orderId).get().getUserEntity().getId().equals(userDetails.getUserEntity().getId())) {
            OrderTableEntity orderTableEntity = orderTableService.findById(orderId).get();
            log.info(orderTableEntity.getId());
            log.info(orderTableEntity.getProductByOrderEntity().stream().map(productByOrderEntity -> productByOrderEntity.getProductEntity().getName()).toList());
            orderTableDTO.setId(orderTableEntity.getId());
            orderTableDTO.setComment(orderTableEntity.getComment());
            orderTableDTO.setStatus(orderTableEntity.getStatus());
            orderTableDTO.setProducts(productByOrderService.findAllByOrderTableEntity(orderTableEntity).stream().map(productByOrderEntity -> productByOrderEntity.getProductEntity().getName()).toList());
            orderTableDTO.setQuantity(productByOrderService.findAllByOrderTableEntity(orderTableEntity).stream().map(productByOrderEntity -> String.valueOf(productByOrderEntity.getCountProducts())).toList());
            orderTableDTO.setId(orderTableEntity.getId());
            log.info(orderTableDTO);
            return "/public/showOrder";
        }

        return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String showOrdersByUser(@RequestParam(defaultValue = "0", name = "page") Integer page,
                                   @RequestParam(defaultValue = "", name = "orderId") String orderId,
                                   Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("orderId " + orderId);
        Page<OrderTableEntity> orderTablePage = orderTableService.findAllOrderTableEntitiesByUserEntity(orderId, page, pageSize, userDetails.getUserEntity());
        List<OrderTableEntity> orderTableEntityList = new ArrayList<>();
        int totalPage = 0;
        if (orderTablePage != null) {
            totalPage = orderTablePage.getTotalPages();
            orderTableEntityList = orderTablePage.getContent();
        }
        model.addAttribute("orderTableEntityList", orderTableEntityList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPage);

        long count = orderTableService.countByUserEntity(userDetails.getUserEntity());
        String panelCount = "Показано " + (pageSize * page + 1) + "-" + (orderTableEntityList.size() + (pageSize * page)) + " из " + count;
        log.info(panelCount);
        Integer lastPageSize=page*pageSize;
        model.addAttribute("lastPageSize", lastPageSize);
        log.info(lastPageSize);
        model.addAttribute("panelCount", panelCount);
        model.addAttribute("orderId", orderId);
        return "/public/orders";
    }

    @PostMapping("/orders")
    public String nextPageUsers(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(name = "orderId") List<String> orderId) {
        String orderIdDecoding = "";
        try {
            orderIdDecoding = UriUtils.encodeQueryParam((orderId.get(orderId.size() - 1)), StandardCharsets.UTF_8);
        } catch (IndexOutOfBoundsException e) {
        }
        log.warn(orderIdDecoding);
        log.warn(orderId);
        return "redirect:/orders?page=" + page + "&orderId=" + orderIdDecoding;
    }
    @PutMapping("/order/{id}/return")
    public ResponseEntity<Map<String, Object>> createOrderAdminPost(@PathVariable Integer id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        Optional<OrderTableEntity> orderTableEntity=orderTableService.findById(id);
        if(orderTableEntity.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error", true, "message", "Заказа не существует"));
        }
        if(!orderTableEntity.get().getUserEntity().getId().equals(userDetails.getUserEntity().getId())){
            return ResponseEntity.badRequest().body(Map.of("error", true, "message", "Вы не являетесь владельцем данного заказа"));
        }
        if(orderTableEntity.get().getStatus()!=null){
            return ResponseEntity.badRequest().body(Map.of("error", true, "message", "Вы не можете отменить этот заказ"));
        }
        orderTableEntity.get().setStatus(false);
        orderTableService.save(orderTableEntity.get());
        return ResponseEntity.ok().body(Map.of("success", true, "message", "Заказ успешно отменен"));
    }



    @ResponseBody
    @PostMapping("/order/create")
    public ResponseEntity<Map<String, Object>> createOrderAdminPost(@ModelAttribute @Valid OrderTableDTO orderTableDTO,
                                                                    BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.badRequest().body(Map.of("error", true, "message", "Пользователь не залогинен"));
        }
        orderTableDTO.setLogin(userDetails.getUserEntity().getLogin());
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
        AtomicInteger num = new AtomicInteger(0);
        orderTableDTO.getQuantity().stream().forEach(s -> {
            ProductByOrderEntity productByOrderEntity = new ProductByOrderEntity();
            productByOrderEntity.setOrderTableEntity(orderTableEntity);
            productByOrderEntity.setCountProducts(Integer.parseInt(s));
            productByOrderEntity.setProductEntity(productService.findByName(orderTableDTO.getProducts().get(num.get())).get());
            num.incrementAndGet();
            productByOrderService.save(productByOrderEntity);
        });
        basketItemService.findAllBasketProductsByUserEntity(userDetails.getUserEntity()).stream().forEach(s ->
                basketItemService.delete(s));

        return ResponseEntity.ok().body(Map.of("success", true, "message", "Product created successfully"));
    }
}
