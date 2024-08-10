package ttmm.controllers.apis.friend;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import ttmm.controllers.CommonController;
import ttmm.database.enums.FriendshipStatus;
import ttmm.database.enums.Role;
import ttmm.database.models.Friendship;
import ttmm.database.models.User;
import ttmm.database.repos.UserRepo;
import ttmm.middlewares.ResponseHelper;
import ttmm.utils.Response;

@Slf4j
public enum AddFriendController implements CommonController {
    INSTANCE;

    @Override
    public void handle(RoutingContext event) {

        Single.just(event)
            .map(this::map)
            .subscribe(
                response -> ResponseHelper.INSTANCE.writeJsonResponse(event, response),
                error -> ResponseHelper.INSTANCE.handleError(event, error)
            ).dispose();

    }

    private Response map(RoutingContext event) {
        Response response;
        JsonObject jsonObject = event.body().asJsonObject();
        String email = jsonObject.getString("emailId");
        User friend = UserRepo.INSTANCE.getUserByEmail(email);
        if (friend != null) {
            User user = event.get("user");
            Friendship friendship = new Friendship(user.getId(), friend.getId(), FriendshipStatus.PENDING);
            try {
                friendship.save();
                response = new Response("Friend request sent", 201, true);
            } catch (Exception e) {
                log.error("Error while saving friendship", e);
                response = new Response("Error while saving friendship", 400, false);
            }
        } else {
            //send email to user to join the app
            User newFriend = new User(email, "john", "doe", Role.USER);
            try {
                newFriend.save();
                response = new Response("Friend request sent", 201, true);
            } catch (Exception e) {
                log.error("Error while saving friendship", e);
                response = new Response("Error while saving friendship", 400, false);
            }

        }
        return response;
    }
}
