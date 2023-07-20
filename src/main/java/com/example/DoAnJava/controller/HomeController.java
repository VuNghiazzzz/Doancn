package com.example.DoAnJava.controller;

import com.example.DoAnJava.entity.Product;
import org.antlr.v4.runtime.ParserRuleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping
    public String home(Model model)
    {
        String url = "http://localhost:8080/product/list";
        List products = this.restTemplate.getForObject(url, List.class);
        model.addAttribute("products",products);
        return  "home/index";
    }

    @GetMapping("/products")
    public String products(Model model) {
        String url = "http://localhost:8080/api/product/list";
        RestTemplate restTemplate = new RestTemplate();
        List result = restTemplate.getForObject(url, List.class);
        model.addAttribute("products", result);
        return "product/list";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id , Model model) {
        String url = "http://localhost:8080/api/product/detail/"+id;
        RestTemplate restTemplate = new RestTemplate();
        Product result = restTemplate.getForObject(url, Product.class);
        model.addAttribute("product", result);
        return "product/detail";
    }
}
