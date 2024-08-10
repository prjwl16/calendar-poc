package ttmm.controllers.apis.category;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import ttmm.utils.SubRouterProtocol;

public enum CategoryRouter implements SubRouterProtocol {
    INSTANCE;

    @Override
    public Router router(Vertx vertx) {

        Router router = Router.router(vertx);
        router.post().handler(AddCategoryController.INSTANCE::handle);

        return router;
    }
}
