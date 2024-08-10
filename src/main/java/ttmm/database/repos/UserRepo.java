package ttmm.database.repos;

import io.reactivex.rxjava3.core.Single;
import ttmm.database.SqlFinder;
import ttmm.database.enums.Role;
import ttmm.database.models.User;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public enum UserRepo {
    INSTANCE;

    public final SqlFinder<Long, User> userFinder = new SqlFinder<>(User.class);

    public User getUserById(Long id) {
        return userFinder.query().where().eq("id", id).findOne();
    }

    public User getUserByEmail(String email) {
        return userFinder.query().where().eq("email", email).findOne();
    }

    public CompletableFuture<User> getUserByEmailFuture(String email) {
        return CompletableFuture.supplyAsync(() -> getUserByEmail(email));
    }

    public User getBasicDetails(Long id, String email) {
        if (id == null && email == null) return null;
        User user;
        if (id == null)
            user = userFinder.query().select("id, email, first_name, last_name, avatar, role").where().eq("email", email).findOne();
        else
            user = userFinder.query().select("id, email, first_name, last_name, avatar, role").where().eq("id", id).findOne();
        if(user != null){
            user.setUserTransactions(null);
            user.setUserGroups(null);
            user.setCategories(null);
        }
        return user;
    }

    public CompletionStage<User> createUserFuture(String email, String firstName, String lastName, String avatar, Role role) {
        return CompletableFuture.supplyAsync(() -> {
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAvatar(avatar);
            user.setRole(role);
            user.save();
            return user;
        });
    }
}
