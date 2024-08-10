package ttmm.controllers.apis.friend;

import io.reactivex.rxjava3.core.Single;
import io.vertx.ext.web.RoutingContext;
import ttmm.controllers.CommonController;
import ttmm.database.enums.Role;
import ttmm.database.models.User;
import ttmm.database.repos.UserRepo;
import ttmm.middlewares.ResponseHelper;
import ttmm.utils.Response;

public enum InviteFriendController implements CommonController {
    INSTANCE;

    @Override
    public void handle(RoutingContext event) {
        Single.just(event)
            .map(this::inviteFriend)
            .subscribe(
                res -> ResponseHelper.INSTANCE.writeJsonResponse(event, new Response("Invite sent", 200, true)),
                err -> ResponseHelper.INSTANCE.handleError(event, err)
            ).dispose();
    }

    private boolean inviteFriend(RoutingContext event) {
        String email = event.request().getParam("email");

        if (email == null || email.isEmpty()) {
            fail("Email is required");
        }
        User basicDetails = UserRepo.INSTANCE.getBasicDetails(null, email);
        if (basicDetails == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(email.split("@")[0]);
            newUser.setLastName("");
            newUser.setAvatar("");
            newUser.setRole(Role.USER);
            try {
                newUser.save();
                return true;
            } catch (Exception e) {
                fail(e.getMessage(), 500);
            }
        }
        //send invite email
        return true;
    }
}
