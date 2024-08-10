import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import ttmm.utils.SubRouterProtocol;


public enum Playground implements SubRouterProtocol {

    INSTANCE;


    @Override
    public Router router(Vertx vertx) {
        try {
            Router router = Router.router(vertx);
            router.get().handler(context -> {
                context.response().end("Hello from playground");
            });
            return router;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
