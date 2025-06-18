/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.ValidationException;
import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.storage.DataStore;

/**
 *
 * @author chani
 */
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    @GET
    public Response getAllAuthors() {
        return Response.ok(DataStore.authors.values()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = DataStore.authors.get(id);
        if (author == null) throw new AuthorNotFoundException("Author not found");
        return Response.ok(author).build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int id) {
        if (!DataStore.authors.containsKey(id)) throw new AuthorNotFoundException("Author not found");
        List<Book> books = new ArrayList<>();
        for (Book b : DataStore.books.values()) {
            if (b.getAuthorId() == id) books.add(b);
        }
        return Response.ok(books).build();
    }

    @POST
    public Response createAuthor(Author author) {
        validateAuthor(author);
        author.setId(DataStore.authorIdCounter++);
        DataStore.authors.put(author.getId(), author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updated) {
        if (!DataStore.authors.containsKey(id)) throw new AuthorNotFoundException("Author not found");
        validateAuthor(updated);
        updated.setId(id);
        DataStore.authors.put(id, updated);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = DataStore.authors.remove(id);
        if (removed == null) throw new AuthorNotFoundException("Author not found");
        return Response.noContent().build();
    }

    private void validateAuthor(Author author) {
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new ValidationException("Author name cannot be empty");
        }
        if (author.getBiography() == null || author.getBiography().trim().isEmpty()) {
            throw new ValidationException("Author biography cannot be empty");
        }
    }
}
