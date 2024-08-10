package ttmm.database.models;

import io.ebean.annotation.DbDefault;
import io.ebean.annotation.Index;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ttmm.database.enums.FriendshipStatus;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friendships")
public class Friendship extends BaseModel {

    @Column(name = "friendship_id")
    @Index(unique = true)
    private String friendshipId;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "friend_id")
    private Long friendId;

    @Enumerated(EnumType.STRING)
    @DbDefault("PENDING")
    private FriendshipStatus status;


    public Friendship(Long userId, Long friendId, FriendshipStatus status) {
        this.userId = userId;
        this.friendId = friendId;
        this.status = status;
    }

}
