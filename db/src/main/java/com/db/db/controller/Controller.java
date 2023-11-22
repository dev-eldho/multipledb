package com.db.db.controller;

import com.db.db.order.entity.Orders;
import com.db.db.order.repositories.OrderRepository;
import com.db.db.product.entity.Product;
import com.db.db.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Controller {
    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public OrderRepository orderRepository;

    @GetMapping("/product")
    public List<Product> productList() {
        System.out.println("SEx");
        return productRepository.findAll();
    }

    @GetMapping("/order")
    public List<Orders> orderList() {
        return orderRepository.findAll();
    }
}
