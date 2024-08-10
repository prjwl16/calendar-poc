package ttmm.controllers.apis.user;

import io.reactivex.rxjava3.core.Single;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import ttmm.controllers.CommonController;
import ttmm.database.models.User;
import ttmm.database.repos.UserRepo;
import ttmm.middlewares.ResponseHelper;
import ttmm.utils.Response;


@Slf4j
public enum SearchUserByEmail implements CommonController {
    INSTANCE;

    @Override
    public void handle(RoutingContext event) {
        Single.just(event)
            .map(this::map)
            .subscribe(
                user -> ResponseHelper.INSTANCE.writeJsonResponse(event, new Response("User found", user, 200, true)),
                error -> ResponseHelper.INSTANCE.handleError(event, error)
            ).dispose();
    }

    private User map(RoutingContext event) {
        String email = event.request().getParam("email");
        User user = UserRepo.INSTANCE.getBasicDetails(null, email);
        if(user == null)
            fail("User not found", 404);
        User tokenUser = event.get("user");
        if(tokenUser.getEmail().equals(email)){
            fail("You can't search yourself", 403);
        }
        return user;
    }
}
