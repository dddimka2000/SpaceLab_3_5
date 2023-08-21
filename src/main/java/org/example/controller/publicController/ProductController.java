package org.example.controller.publicController;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.security.UserDetailsImpl;
import org.example.service.FavoriteProductService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Controller
public class ProductController {
    final
    ProductService productService;

    public ProductController(ProductService productService, FavoriteProductService favoriteProductService) {
        this.productService = productService;
        this.favoriteProductService = favoriteProductService;
    }
    final
    FavoriteProductService favoriteProductService;
    @GetMapping("/productInfo/{idProduct}")
    public String showInfoProduct(Model model, @PathVariable Integer idProduct, @ModelAttribute("productDTO")ProductDto productDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("favoriteProduct",favoriteProductService.findAllFavoriteProductsByUserEntity(userDetails.getUserEntity())
                    .stream().map(s->s.getProductEntity().getId()).toList());
        }
        if(productService.findById(idProduct).get().getStatus()==false){
            return "redirect:/";
        }
        ProductEntity productEntity=productService.findById(idProduct).get();
        productDto.setId(productEntity.getId());
        productDto.setName(productEntity.getName());
        productDto.setDescription(productEntity.getDescription());
        productDto.setPath(productEntity.getPath());
        productDto.setPrice(productEntity.getPrice());
        List<ProductEntity> products = productService.findAllProductsByCategoryEntity(productEntity.getCategoryEntity());
        Collections.shuffle(products);
        model.addAttribute("otherProducts", products.stream().limit(4));
        return "/public/productInfo";
    }
}
