package ttmm.controllers.apis.expense;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import ttmm.controllers.apis.expense.friend.FriendExpenseRouter;
import ttmm.controllers.apis.expense.groups.GroupExpenseRouter;
import ttmm.utils.SubRouterProtocol;

public enum ExpenseRouter implements SubRouterProtocol {
    INSTANCE;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);

        router.route("/friend/*").subRouter(FriendExpenseRouter.INSTANCE.router(vertx));
        router.route("/group/*").subRouter(GroupExpenseRouter.INSTANCE.router(vertx));

        return router;
    }
}
