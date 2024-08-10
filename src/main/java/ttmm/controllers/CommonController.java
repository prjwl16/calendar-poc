package ttmm.controllers;

import io.vertx.ext.web.RoutingContext;
import ttmm.exceptions.RoutingError;

@FunctionalInterface
public interface CommonController {


    void handle(RoutingContext event);

    default void fail(String e) throws RoutingError {
        throw new RoutingError(e);
    }

    default void fail(String e, int status) throws RoutingError {
        throw new RoutingError(e, status);
    }
}
