package ttmm.controllers.auth;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.Oauth2Credentials;
import io.vertx.ext.auth.oauth2.providers.GoogleAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import lombok.extern.slf4j.Slf4j;
import ttmm.controllers.CommonController;
import ttmm.database.enums.Role;
import ttmm.database.repos.UserRepo;
import ttmm.middlewares.Jwt;
import ttmm.utils.ConfigManager;
import ttmm.utils.SubRouterProtocol;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/*
    Need to keep this all-in-one file for now because of the way the vertx router works
 */

@Slf4j
public enum AuthRoutes implements SubRouterProtocol, CommonController {

    INSTANCE;

    private OAuth2AuthHandler authHandler;
    private OAuth2Auth authProvider;

    @Override
    public Router router(Vertx vertx) {
        Router router = Router.router(vertx);
        initAuth(vertx);

        authHandler.setupCallback(router.get("/callback"));

        router.route("/google").handler(authHandler).failureHandler(ctx -> {
            ctx.response().end("error");
            fail(ctx.failure().getMessage());
        });
        router.get("/google/callback").handler(this::handle);

        return router;
    }

    @Override
    public void handle(RoutingContext ctx) {
        try {
            String code = ctx.request().getParam("code");
            String redirectUri = ConfigManager.INSTANCE.getAuthConfig().getString("redirect_uri");
            String frontEndUriTokenRedirect = ConfigManager.INSTANCE.getFrontEndUri() + ConfigManager.INSTANCE.getAuthConfig().getString("token_redirect");
            String frontEndUriErrorRedirect = ConfigManager.INSTANCE.getFrontEndUri() + ConfigManager.INSTANCE.getAuthConfig().getString("error_redirect");

            JsonObject put = new JsonObject().put("code", code).put("redirectUri", redirectUri);

            Credentials credentials = new Oauth2Credentials(put);

            //Authenticate user with google
            // 1) If user exists in DB then create a token with email and id and then redirect to front end with token
            // 2) If user does not exist in DB then create a new user and then create a token with email and id and then redirect to front end with token

            // TODO: Come back and learn how it actually works
            authProvider.authenticate(credentials)
                .onSuccess(user -> {
                    authProvider.userInfo(user).onSuccess(userInfo -> {
                        System.out.println(userInfo);
                        String email = userInfo.getString("email");
                        String firstName = userInfo.getString("given_name");
                        String lastName = userInfo.getString("family_name");
                        String picture = userInfo.getString("picture");
                        UserRepo.INSTANCE.getUserByEmailFuture(email)
                            .thenComposeAsync(newUser -> {
                                if (newUser == null) {
                                    return UserRepo.INSTANCE.createUserFuture(email, firstName, lastName, picture, Role.USER);
                                } else {
                                    return CompletableFuture.completedFuture(newUser);
                                }
                            }).thenApplyAsync(userExisted -> {
                                log.info("User created/exists: {}", userExisted);
                                String token = Jwt.INSTANCE.generateToken(email, userExisted.getId());
                                ctx.response().putHeader("Location", frontEndUriTokenRedirect + token).setStatusCode(302).end();
                                return userExisted;
                            }).exceptionally(throwable -> {
                                log.error("Error creating user", throwable);
                                throw new RuntimeException(throwable);
                            });
                    }).onFailure(error -> {
                        log.error("Error authenticating user", error);
                        ctx.response().putHeader("Location", frontEndUriErrorRedirect).setStatusCode(302).end();
                    });
                });
        } catch (Exception e) {
            log.error("Error authenticating user", e);
            ctx.response().putHeader("Location", ConfigManager.INSTANCE.getFrontEndUri() + ConfigManager.INSTANCE.getAuthConfig().getString("error_redirect")).setStatusCode(302).end();
        }
    }

    void initAuth(Vertx vertx) {
        String clientId = ConfigManager.INSTANCE.getAuthConfig().getString("client_id");
        String clientSecret = ConfigManager.INSTANCE.getAuthConfig().getString("client_secret");
        String redirectUri = ConfigManager.INSTANCE.getAuthConfig().getString("redirect_uri");

        authProvider = GoogleAuth.create(vertx, clientId, clientSecret);
        // add calendar auth scope
        authHandler = OAuth2AuthHandler.create(vertx, authProvider, redirectUri).withScopes(List.of("email", "profile", "https://www.googleapis.com/auth/calendar"));
    }
}
