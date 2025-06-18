/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.bookstore.exception.mappers;

import com.bookstore.exception.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class AuthorNotFoundMapper implements ExceptionMapper<AuthorNotFoundException> {
    @Override
    public Response toResponse(AuthorNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Author Not Found");
        error.put("message", ex.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}