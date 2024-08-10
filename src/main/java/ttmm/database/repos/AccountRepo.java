package ttmm.database.repos;

import ttmm.database.SqlFinder;
import ttmm.database.models.Account;

public enum AccountRepo {
    INSTANCE;
    public final SqlFinder<Long, Account> accountFinder = new SqlFinder<>(Account.class);
}
