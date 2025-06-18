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

// --- BookResource.java ---
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    @GET
    public Response getAllBooks() {
        return Response.ok(DataStore.books.values()).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Book book = DataStore.books.get(id);
        if (book == null) throw new BookNotFoundException("Book with ID " + id + " not found.");
        return Response.ok(book).build();
    }

    @POST
    public Response createBook(Book book) {
        if (!DataStore.authors.containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException("Author with ID " + book.getAuthorId() + " not found.");
        }
        book.setId(DataStore.bookIdCounter++);
        DataStore.books.put(book.getId(), book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existing = DataStore.books.get(id);
        if (existing == null) throw new BookNotFoundException("Book not found");
        updatedBook.setId(id);
        DataStore.books.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = DataStore.books.remove(id);
        if (removed == null) throw new BookNotFoundException("Book not found");
        return Response.noContent().build();
    }
}