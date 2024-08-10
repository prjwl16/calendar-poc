package ttmm.database.models;

import io.ebean.annotation.DbDefault;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ttmm.database.enums.Currency;

import javax.persistence.*;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "settlements")
public class Settlement extends BaseModel {

    private double amount;

    @Enumerated(EnumType.STRING)
    @DbDefault("INR")
    @NotNull
    private Currency currency;
    private Date date;

    //Relationships


    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;



}
