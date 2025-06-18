/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

import com.bookstore.exception.*;
import com.bookstore.model.*;
import com.bookstore.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 *
 * @author chani
 */
@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    @GET
    public Response getOrders(@PathParam("customerId") int customerId) {
        List<Order> orderList = DataStore.orders.getOrDefault(customerId, new ArrayList<>());
        return Response.ok(orderList).build();
    }

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null || cart.getItems().isEmpty()) throw new InvalidInputException("Cart is empty");

        double total = 0;
        for (CartItem item : cart.getItems()) {
            Book book = DataStore.books.get(item.getBookId());
            if (book == null) throw new BookNotFoundException("Book not found");
            if (book.getStock() < item.getQuantity()) throw new OutOfStockException("Not enough stock");
            total += book.getPrice() * item.getQuantity();
            book.setStock(book.getStock() - item.getQuantity());
        }

        Order order = new Order(DataStore.orderIdCounter++, customerId, new ArrayList<>(cart.getItems()), total);
        DataStore.orders.computeIfAbsent(customerId, k -> new ArrayList<>()).add(order);
        cart.getItems().clear();

        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        List<Order> customerOrders = DataStore.orders.get(customerId);
        if (customerOrders != null) {
            for (Order order : customerOrders) {
                if (order.getId() == orderId) return Response.ok(order).build();
            }
        }
        throw new InvalidInputException("Order not found");
    }
}
