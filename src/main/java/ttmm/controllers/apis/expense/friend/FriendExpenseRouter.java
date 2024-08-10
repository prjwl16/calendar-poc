package ttmm.controllers.apis.expense.friend;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import ttmm.utils.SubRouterProtocol;

public enum FriendExpenseRouter implements SubRouterProtocol {
    INSTANCE;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post().handler(AddExpense.INSTANCE::handle);

        return router;
    }
}
