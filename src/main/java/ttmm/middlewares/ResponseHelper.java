package ttmm.middlewares;

import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import ttmm.exceptions.RoutingError;
import ttmm.utils.Mapper;
import ttmm.utils.Response;

@Slf4j
public enum ResponseHelper {
    INSTANCE;

    public void writeJsonResponse(RoutingContext context, Response response) {
        if(response == null) response = new Response("OK", 200, true);
        context.response()
            .putHeader("content-type", "application/json")
            .end(Mapper.INSTANCE.getGson().toJson(response));
        log.info("Response sent");
    }

    public void handleError(RoutingContext context, Throwable error) {
        if(!context.response().closed()){
            if(isReportableError(error)){
                writeError(context, ((RoutingError)error).getStatusCode(), error.getMessage());
            }else {
                writeError(context, 500, "Oops, it's on us..!");
            }
//            log.error("Error while processing request ------- ", error);
        }else{
            log.error("Response already sent");
        }
    }

    void writeError(RoutingContext context, int statusCode, String message) {
        context.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(statusCode)
            .end(Mapper.INSTANCE.getGson().toJson(new Response(message, statusCode,false)));
    }


    boolean isReportableError(Throwable error) {
        if(error instanceof NullPointerException)
            return false;
        if (error instanceof RoutingError)
            return true;
        if(error.getClass().isAssignableFrom(RoutingError.class))
            return true;
        return false;
    }

}
