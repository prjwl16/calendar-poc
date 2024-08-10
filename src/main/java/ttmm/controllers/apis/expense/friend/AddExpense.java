package ttmm.controllers.apis.expense.friend;

import io.ebean.DB;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ttmm.controllers.CommonController;
import ttmm.database.enums.TransactionType;
import ttmm.database.models.Transaction;
import ttmm.database.models.User;
import ttmm.database.models.UserTransaction;
import ttmm.database.repos.AccountRepo;
import ttmm.database.repos.CategoryRepo;
import ttmm.database.repos.UserRepo;
import ttmm.middlewares.ResponseHelper;
import ttmm.utils.Response;

import java.sql.Date;

@Slf4j
public enum AddExpense implements CommonController {

    INSTANCE;

    @Override
    public void handle(RoutingContext event) {

        Single.just(event)
            .map(this::map)
            .subscribe(
                (response) -> ResponseHelper.INSTANCE.writeJsonResponse(event, new Response("Expense added", response, 200, true)),
                err -> ResponseHelper.INSTANCE.handleError(event, err)
            ).dispose();

    }

    private boolean map(RoutingContext context) {
        io.ebean.Transaction txn = DB.beginTransaction();
        try {
            Transaction transaction = new Transaction();
            JsonObject jsonObject = context.body().asJsonObject();

            double amount = jsonObject.getDouble("amount");
            String description = jsonObject.getString("description");
            Long payer = jsonObject.getLong("payer");
            Long receiver = jsonObject.getLong("receiver");
            Long accountId = jsonObject.getLong("accountId");
            Long categoryId = jsonObject.getLong("categoryId");
            String date = jsonObject.getString("date");
            TransactionType type = TransactionType.valueOf(jsonObject.getString("type"));
            User tokenUser = context.get("userId");

            transaction.setAmount(amount);
            transaction.setDescription(description);
            transaction.setAccount(AccountRepo.INSTANCE.accountFinder.query().where().idEq(accountId).findOne());
            transaction.setCategory(CategoryRepo.INSTANCE.categoryFinder.query().where().idEq(categoryId).findOne());
            transaction.setDate(Date.valueOf(date));
            transaction.setType(type);

            transaction.save();

            UserTransaction userTransaction = new UserTransaction();

            userTransaction.setTransaction(transaction);
            userTransaction.setPayer(UserRepo.INSTANCE.getUserById(payer));
            userTransaction.setCreator(tokenUser);
            userTransaction.setReceiver(UserRepo.INSTANCE.getUserById(receiver));

            userTransaction.save();

            //TODO: handle recurring transaction

            txn.commit();
            return true;
        } catch (Exception e) {
            txn.rollback();
            log.error("Error while adding expense", e);
            return false;
        } finally {
            txn.end();
        }
    }

    @Data
    class Res {
        private String message;
        private int code;
        private boolean success;
    }
}
