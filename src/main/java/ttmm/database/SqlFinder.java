package ttmm.database;

import io.ebean.Query;

public class SqlFinder<I, T> {
    private final Class<T> type;

    public SqlFinder(Class<T> type) {
        this.type = type;
    }

    public Query<T> query(){
        return DataBaseFactory.INSTANCE.db.find(type);
    }

}
