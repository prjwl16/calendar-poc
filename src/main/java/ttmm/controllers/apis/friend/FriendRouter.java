package ttmm.controllers.apis.friend;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import ttmm.utils.SubRouterProtocol;

public enum FriendRouter implements SubRouterProtocol {
    INSTANCE;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/invite/:email").handler(InviteFriendController.INSTANCE::handle);
        router.post().handler(AddFriendController.INSTANCE::handle);
        router.get().handler(FetchFriendController.INSTANCE::handle);

        return router;
    }
}
