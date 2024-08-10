package ttmm.database.models;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends BaseModel {
    @NotNull
    private String name;
    private String description;
    private String avatar;

    //Relationships

    @ManyToOne
    @Column(name = "user_id")
    private User user;
}
