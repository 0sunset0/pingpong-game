package prography.pingpong_game.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRoom is a Querydsl query type for UserRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRoom extends EntityPathBase<UserRoom> {

    private static final long serialVersionUID = 479961787L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRoom userRoom = new QUserRoom("userRoom");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QRoom room;

    public final EnumPath<Team> team = createEnum("team", Team.class);

    public final prography.pingpong_game.user.entity.QUser user;

    public QUserRoom(String variable) {
        this(UserRoom.class, forVariable(variable), INITS);
    }

    public QUserRoom(Path<? extends UserRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRoom(PathMetadata metadata, PathInits inits) {
        this(UserRoom.class, metadata, inits);
    }

    public QUserRoom(Class<? extends UserRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new prography.pingpong_game.user.entity.QUser(forProperty("user")) : null;
    }

}

