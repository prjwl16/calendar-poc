package ttmm.database.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_transactions")
public class UserTransaction extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
