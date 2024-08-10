package ttmm.database.models;


import io.ebean.annotation.JsonIgnore;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group extends BaseModel {
    @NotNull
    private String name;
    private String description;
    private String avatar;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<UserGroup> userGroups;
}
