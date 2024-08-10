package ttmm.controllers.apis.user;

import io.vertx.ext.web.RoutingContext;
import ttmm.controllers.CommonController;

public enum CreateUserController implements CommonController {
    INSTANCE;


    @Override
    public void handle(RoutingContext event) {
        try{
            event.response().end("OK");
        }catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void map(RoutingContext context) {

    }
}
