package org.example.controller.publicController;

import lombok.extern.log4j.Log4j2;
import org.example.entity.ProductEntity;
import org.example.security.UserDetailsImpl;
import org.example.service.FavoriteProductService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@Log4j2
public class MainPageController {
    final
    ProductService productService;


    private final
    FavoriteProductService favoriteProductService;

    public MainPageController(ProductService productService, FavoriteProductService favoriteProductService) {
        this.productService = productService;
        this.favoriteProductService = favoriteProductService;
    }

    @GetMapping("/")
    public String showMainPage(@RequestParam(defaultValue = "0", name = "page") Integer page,
                                @RequestParam(defaultValue = "", name = "productName") String productName,
                                Model model ,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("favoriteProduct",favoriteProductService.findAllFavoriteProductsByUserEntity(userDetails.getUserEntity())
                    .stream().map(s->s.getProductEntity().getId()).toList());
        }
        Page<ProductEntity> productsPage = productService.findByNameContainingIgnoreCaseWithStatus(productName, page, size, true);
        List<ProductEntity> products = productsPage.getContent();
        log.info(products);
        if(products.size()==0&&page!=0){
            return "redirect:/?page=" + 0 + "&productName=" + productName;
        }
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());

        long count = productService.countByStatusAndNameContainingIgnoreCase(true,productName);
        int firstNum=(size * page + 1);
        int secondNum=(products.size() + (size * page));
        if(firstNum>secondNum){
            firstNum=secondNum;
        }
        String panelCount = "Показано " + firstNum  + "-" +secondNum  + " из " + count;
        log.info(panelCount);
        model.addAttribute("panelCount", panelCount);

        model.addAttribute("productName", productName);
        return "/public/mainPage";
    }

    int size=6;

    @PostMapping("/")
    public String nextPageUsers(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam(name = "productName") List<String> productName) {
        String productNameDecoding = "";
        productNameDecoding = UriUtils.encodeQueryParam(productName.get(productName.size() - 1), StandardCharsets.UTF_8);
        log.warn(productNameDecoding);
        log.warn(productName);

        return "redirect:/?page=" + page + "&productName=" + productNameDecoding;
    }
}
