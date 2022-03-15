package com.jobgate.demo.controllers;


import com.jobgate.demo.Models.Order;
import com.jobgate.demo.Models.Response;
import com.jobgate.demo.Models.User;
import com.jobgate.demo.dao.IOrder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "jobgate")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private IOrder iOrder;

    @PostMapping("/addOrder")
    public Response<Order> addOrder(@RequestBody Order o) {
        try {
            if (o != null) {
                return new Response<Order>("200", "Create Order", iOrder.save(o));
            } else {
                return new Response<Order>("500", "Order not found", null);
            }
        } catch (Exception e) {
            return new Response<Order>("406", e.getMessage(), null);
        }
    }


    @DeleteMapping("/deleteOrder")
    public Response<Order> deleteOrder(long id) {
        iOrder.deleteById(id);
        try {
            return new Response<Order>("200", "Delete Order", null);
        } catch (Exception e) {
            return new Response<Order>("406", e.getMessage(), null);
        }
    }

    @PutMapping("/updateOrder")
    public Response<Order> updateCategory(Order o) {
        Optional<Order> order = iOrder.findById(o.getId());
        if (order.isPresent()) {
            Order newOrder = order.get();
            newOrder.setName(o.getName());
            newOrder.setDescription(o.getDescription());
            newOrder.setTotal_price(o.getTotal_price());

            iOrder.save(newOrder);
            try {
                return new Response<Order>("200", "Order Updated", iOrder.save(o));
            } catch (Exception e) {
                return new Response<Order>("406", e.getMessage(), iOrder.save(o));
            }
        }
        try {
            return new Response<Order>("200", "Order Updated", iOrder.save(o));
        } catch (Exception e) {
            return new Response<Order>("406", e.getMessage(), iOrder.save(o));
        }
    }


    @GetMapping("/")
    public Response<List<Order>> findAllProduct() {
        try {
            return new Response<List<Order>>("200", "Get all Orders", iOrder.findAll());
        } catch (Exception e) {
            return new Response<List<Order>>("406", e.getMessage(), iOrder.findAll());

        }
    }


}
