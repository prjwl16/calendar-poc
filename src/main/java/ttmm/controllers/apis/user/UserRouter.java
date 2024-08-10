package ttmm.controllers.apis.user;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import ttmm.utils.SubRouterProtocol;


public enum UserRouter implements SubRouterProtocol {

    INSTANCE;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/:email").handler(SearchUserByEmail.INSTANCE::handle);
        router.get().handler(FetchUserController.INSTANCE::handle);
        router.post().handler(CreateUserController.INSTANCE::handle);

        return router;
    }

}
