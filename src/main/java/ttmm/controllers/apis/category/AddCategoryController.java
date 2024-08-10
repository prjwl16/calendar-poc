package ttmm.controllers.apis.category;

import io.reactivex.rxjava3.core.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import ttmm.controllers.CommonController;
import ttmm.database.models.Category;
import ttmm.database.models.User;
import ttmm.database.repos.CategoryRepo;
import ttmm.middlewares.ResponseHelper;
import ttmm.utils.Response;

@Slf4j
public enum AddCategoryController implements CommonController {
    INSTANCE;

    @Override
    public void handle(RoutingContext event) {
        Single.just(event)
            .map(this::map)
            .subscribe(
                response -> ResponseHelper.INSTANCE.writeJsonResponse(event, response),
                err -> ResponseHelper.INSTANCE.handleError(event, err)
            );
    }


    private Response map(RoutingContext context) {
        Response response = new Response();
        try {
            JsonObject body = context.body().asJsonObject();
            if (body == null) {
                throw new Exception("Invalid body");
            }
            String name = body.getString("name");
            String description = body.getString("description");
            User tokenUser = context.get("user");

            Category category = CategoryRepo.INSTANCE.addCategory(name, description, tokenUser);
            if (category == null) {
                throw new Exception("Error while adding category");
            }
            response.setMessage("Category added");
            response.setSuccess(true);
            response.setStatus(200);
            response.setData(category);
        } catch (Exception e) {
            log.error("Error while adding category:  ", e);
            fail(e.getMessage());
        }
        return response;
    }
}
