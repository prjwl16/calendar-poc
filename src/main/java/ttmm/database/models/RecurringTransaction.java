package ttmm.database.models;

import io.ebean.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recurring_transactions")
public class RecurringTransaction extends BaseModel{
    private Date startDate;
    private Date endDate;
    private int frequency;
    private Date nextExecutionDate;
    private String interval;
    private boolean isActive;
    private String recurringPeriod;
}
