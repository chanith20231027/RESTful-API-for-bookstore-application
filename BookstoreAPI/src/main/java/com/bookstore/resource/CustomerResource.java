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
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    @GET
    public Response getAllCustomers() {
        return Response.ok(DataStore.customers.values()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = DataStore.customers.get(id);
        if (customer == null) throw new CustomerNotFoundException("Customer not found");
        return Response.ok(customer).build();
    }

    @POST
    public Response createCustomer(Customer customer) {
        customer.setId(DataStore.customerIdCounter++);
        DataStore.customers.put(customer.getId(), customer);
        DataStore.carts.put(customer.getId(), new Cart(customer.getId()));
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updated) {
        if (!DataStore.customers.containsKey(id)) throw new CustomerNotFoundException("Customer not found");
        updated.setId(id);
        DataStore.customers.put(id, updated);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer removed = DataStore.customers.remove(id);
        if (removed == null) throw new CustomerNotFoundException("Customer not found");
        DataStore.carts.remove(id);
        DataStore.orders.remove(id);
        return Response.noContent().build();
    }
}