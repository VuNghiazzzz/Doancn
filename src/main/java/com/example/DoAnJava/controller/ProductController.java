package com.example.DoAnJava.controller;

import com.example.DoAnJava.DTO.CreateProductDto;
import com.example.DoAnJava.DTO.ProductDto;
import com.example.DoAnJava.entity.Category;
import com.example.DoAnJava.entity.Product;
import com.example.DoAnJava.services.FirebaseService;
import com.example.DoAnJava.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private RestTemplate restTemplate;

    /*create api list products*/

    @GetMapping("/view")
    public String getView() {
        String url = "http://localhost:8080/product/detail/1";
        ProductDto product = this.restTemplate.getForObject(url, ProductDto.class);
        System.out.println("A " + product.getId());
        return "layout/layoutClient";
    }
    @GetMapping("/list")
    @ResponseBody
    public List<Product> getProductList() {
        List<Product> product = this.productService.getAllProduct();
        return product;
    }

    @GetMapping("/detail/{id}")
    @ResponseBody

    public Product product(@PathVariable(value = "id") Long id) {
        Product product = productService.getProductById(id);
        return product;
    }

    // /product/category?category=abc
    @GetMapping("/category")
    @ResponseBody
    public List<Product> getProductsByCate(@RequestParam("category") String category) {
        List<Product> product = this.productService.getProductsByCategory(category);
        return product;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Product> search(@RequestParam("search") String search) {
        return this.productService.searchProducts(search);
    }

    @PostMapping
    @ResponseBody
    public Product create(@ModelAttribute() CreateProductDto product, @RequestParam("file") List<MultipartFile> file, @RequestParam("files") List<MultipartFile> files) {
        String url = this.firebaseService.uploadImages(file).get(0);
        System.out.println("url 0  " + url);
        List<String> url_list = this.firebaseService.uploadImages(files);
        product.setUrlImageThumbnail(url);
        String result = String.join(",", url_list);
        product.setImageList(result);
        System.out.println("url list  " + result);
        return this.productService.saveProduct(product);
    }

//    @PostMapping("/{id}/edit")
//    @ResponseBody
//    public String updateProduct(@PathVariable Long id, @RequestBody CreateProductDto createProductDto) {
//        productService.updateProduct(id, createProductDto);
//        return "redirect:/products/";
//    }


}
