package ttmm.database.models;

import io.ebean.Model;
import io.ebean.annotation.NotNull;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;


@EqualsAndHashCode(callSuper = false)
@Data
@MappedSuperclass
public abstract class BaseModel extends Model {

    @Id
    @NotNull
    private Long id;

    @WhenCreated
    @Column(name = "created_at")
    private Timestamp createdAt;

    @WhenModified
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @SoftDelete
    private boolean isDeleted = false;


}
