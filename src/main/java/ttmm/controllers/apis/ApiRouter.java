package ttmm.controllers.apis;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import ttmm.controllers.apis.category.CategoryRouter;
import ttmm.controllers.apis.expense.ExpenseRouter;
import ttmm.controllers.apis.friend.FriendRouter;
import ttmm.controllers.apis.user.UserRouter;
import ttmm.middlewares.Jwt;
import ttmm.utils.SubRouterProtocol;

public enum ApiRouter implements SubRouterProtocol {
    INSTANCE;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route().handler(Jwt.INSTANCE::validateToken);
        router.route().handler(BodyHandler.create());

        router.route("/expense/*").subRouter(ExpenseRouter.INSTANCE.router(vertx));
        router.route("/friend/*").subRouter(FriendRouter.INSTANCE.router(vertx));
        router.route("/user/*").subRouter(UserRouter.INSTANCE.router(vertx));
        router.route("/category/*").subRouter(CategoryRouter.INSTANCE.router(vertx));

        return router;
    }
}
