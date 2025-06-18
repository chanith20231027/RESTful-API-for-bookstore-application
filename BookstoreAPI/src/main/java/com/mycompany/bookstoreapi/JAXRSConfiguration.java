
package com.mycompany.bookstoreapi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class JAXRSConfiguration extends Application {
    // Another alternative config class, usually one of these is sufficient
}
