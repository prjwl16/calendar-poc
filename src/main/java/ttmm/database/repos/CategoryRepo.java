package ttmm.database.repos;

import lombok.extern.slf4j.Slf4j;
import ttmm.database.SqlFinder;
import ttmm.database.models.Category;
import ttmm.database.models.User;

@Slf4j
public enum CategoryRepo {
    INSTANCE;
    public final SqlFinder<Long, Category> categoryFinder = new SqlFinder<>(Category.class);

    public Category addCategory(String name, String description, User user) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setUser(user);
        try {
            category.save();
            return category;
        } catch (Exception e) {
            log.error("Error while adding category:  ", e);
            return null;
        }
    }
}
