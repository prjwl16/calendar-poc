package ttmm.database.models;

import io.ebean.annotation.DbDefault;
import io.ebean.annotation.Index;
import io.ebean.annotation.JsonIgnore;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ttmm.database.enums.Role;

import javax.persistence.*;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseModel {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    @Index(unique = true)
    @NotNull
    private String email;
    private String avatar;
    @Column(name = "google_auth_id")
    private String googleAuthId;

    @Enumerated(EnumType.STRING)
    @DbDefault("USER")
    @NotNull
    private Role role=Role.USER;

    @OneToMany(mappedBy = "creator")
    @JsonIgnore
    private List<UserTransaction> userTransactions;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Category> categories;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserGroup> userGroups;


    public User(String email, String firstName, String lastName, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.googleAuthId = googleAuthId;
        this.role = role;
    }

}
