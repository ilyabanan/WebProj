package com.example.playGame.controller;

import com.example.playGame.models.Product;
import com.example.playGame.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class productController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product")
    public String product(Model model){
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "productPage";
    }
    @GetMapping("/addProduct")
    public String addProduct(){
        return "addProduct";
    }
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name, @RequestParam String description, @RequestParam BigDecimal price, @RequestParam String image){
        Product product = new Product(name,description,price,image);
        productRepository.save(product);
        return "redirect:/adminPage";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        Product product = productRepository.findById(id);
        productRepository.delete(product);
        return "redirect:/adminPage";
    }
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id);
        model.addAttribute("editProd", product);
        return "editProduct";
    }
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long id, @RequestParam String name, @RequestParam String description, @RequestParam BigDecimal price, @RequestParam String image, Model model){
        Product product = productRepository.findById(id);

        product.setName(name);
        product.setImage(image);
        product.setDescription(description);
        product.setPrice(price);

        productRepository.save(product);
        return "redirect:/adminPage";
    }
}
