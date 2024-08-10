package ttmm.controllers.apis.expense.groups;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import ttmm.utils.SubRouterProtocol;

public enum GroupExpenseRouter implements SubRouterProtocol {
    INSTANCE;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post().handler(AddGroupExpenseController.INSTANCE::handle);
        router.get().handler(FetchGroupExpenseController.INSTANCE::handle);

        return router;
    }
}
