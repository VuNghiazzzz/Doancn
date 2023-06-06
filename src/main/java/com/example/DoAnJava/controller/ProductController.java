package com.example.DoAnJava.controller;

import com.example.DoAnJava.DTO.CreateProductDto;
import com.example.DoAnJava.entity.Category;
import com.example.DoAnJava.entity.Product;
import com.example.DoAnJava.services.FirebaseService;
import com.example.DoAnJava.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FirebaseService firebaseService;
    static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/list")
    public String listProduct()
    {
        return  "product/list";
    }

    @GetMapping("/detail")
    public String detailProduct(Model model){
        Long id = 1l;
        String name = "Sản phẩm test";
        Double gia = 200.0;
        String mota = "test mo ta san pham";

        String hinh = "/img/4jpg.jpg";

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(gia);
        product.setDescription(mota);
        product.setUrlImageThumbnail(hinh);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping()
    @ResponseBody
    public List<Product> getProductList(){
        List<Product> product = this.productService.getAllProduct();
        return product;
    }


    // /product/category?category=abc
    @GetMapping("/category")
    @ResponseBody
    public List<Product> getProductsByCate(@RequestParam("category") String category){
        List<Product> product = this.productService.getProductsByCategory(category);
        return product;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Product> search(@RequestParam("search") String search){
        return  this.productService.searchProducts(search);
    }

    @PostMapping
    @ResponseBody
    public Product create(@ModelAttribute() CreateProductDto product, @RequestParam("file") List<MultipartFile> file, @RequestParam("files") List<MultipartFile> files){
        String url = this.firebaseService.uploadImages(file).get(0);
        System.out.println("url 0  " + url);
        List<String> url_list = this.firebaseService.uploadImages(files);
        product.setUrlImageThumbnail(url);
        String result = String.join(",", url_list);
        product.setImageList(result);
        System.out.println("url list  " + result);

        return this.productService.saveProduct(product);
    }



}
