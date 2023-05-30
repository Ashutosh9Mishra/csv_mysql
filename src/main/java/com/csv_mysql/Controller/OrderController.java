package com.csv_mysql.Controller;


import com.csv_mysql.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importOrders() {
        orderService.processOrders();
        return ResponseEntity.ok("Orders imported successfully.");
    }
}
