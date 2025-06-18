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
@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null) throw new CartNotFoundException("Cart not found");
        return Response.ok(cart).build();
    }

    @POST
    @Path("/items")
    public Response addItem(@PathParam("customerId") int customerId, CartItem item) {
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null) throw new CartNotFoundException("Cart not found");
        cart.getItems().add(item);
        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId, CartItem updatedItem) {
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null) throw new CartNotFoundException("Cart not found");
        for (CartItem item : cart.getItems()) {
            if (item.getBookId() == bookId) {
                item.setQuantity(updatedItem.getQuantity());
                return Response.ok(cart).build();
            }
        }
        throw new BookNotFoundException("Book in cart not found");
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        Cart cart = DataStore.carts.get(customerId);
        if (cart == null) throw new CartNotFoundException("Cart not found");
        cart.getItems().removeIf(item -> item.getBookId() == bookId);
        return Response.noContent().build();
    }
}