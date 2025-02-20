package prography.pingpong_game.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoomCapacity is a Querydsl query type for RoomCapacity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomCapacity extends EntityPathBase<RoomCapacity> {

    private static final long serialVersionUID = -1919431766L;

    public static final QRoomCapacity roomCapacity = new QRoomCapacity("roomCapacity");

    public final NumberPath<Integer> blueTeamCapacity = createNumber("blueTeamCapacity", Integer.class);

    public final NumberPath<Integer> currentCapacity = createNumber("currentCapacity", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxCapacity = createNumber("maxCapacity", Integer.class);

    public final NumberPath<Integer> redTeamCapacity = createNumber("redTeamCapacity", Integer.class);

    public QRoomCapacity(String variable) {
        super(RoomCapacity.class, forVariable(variable));
    }

    public QRoomCapacity(Path<? extends RoomCapacity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoomCapacity(PathMetadata metadata) {
        super(RoomCapacity.class, metadata);
    }

}

