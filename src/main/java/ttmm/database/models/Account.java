package ttmm.database.models;

import io.ebean.annotation.DbDefault;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ttmm.database.enums.AccountType;
import ttmm.database.enums.Currency;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account extends BaseModel{

    private String name;
    @Enumerated(EnumType.STRING)
    @DbDefault("INR")
    private Currency currency;
    private String balance;

    @Enumerated(EnumType.STRING)
    @DbDefault("CASH")
    private AccountType type;

    @Column(name = "is_default")
    private boolean isdDefault;

    //Relationships
    @ManyToOne
    @Column(name = "user_id")
    private User user;
}
