package ttmm.controllers.apis.user;


import io.reactivex.rxjava3.core.Single;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import ttmm.controllers.CommonController;
import ttmm.database.models.User;
import ttmm.middlewares.ResponseHelper;
import ttmm.utils.Response;

@Slf4j
public enum FetchUserController implements CommonController {

    INSTANCE;

    public void handle(RoutingContext event) {
        log.info("Fetching user");
        try {
            Single.just(event).map(this::map).subscribe(
                user -> ResponseHelper.INSTANCE.writeJsonResponse(event, new Response("User fetched successfully", user, 200, true)),
                error -> {
                    ResponseHelper.INSTANCE.handleError(event, error);
                }
            ).dispose();
        } catch (Exception e) {
            log.error("Error fetching user " + e.getMessage());
            fail(e.getMessage());
        }
    }


    private User map(RoutingContext context) {
        return context.get("user");
    }
}
