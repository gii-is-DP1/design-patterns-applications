package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;

import es.us.dp1.teachers.chess.model.NamedEntity;
import es.us.dp1.teachers.chess.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChessMatch extends NamedEntity {
    LocalDateTime start;
    LocalDateTime finish;
    Long turnDuration;
    
    ChessMatchType type;

    @ManyToOne
    User creator;
    
    @ManyToOne
    User opponent;

    @OneToOne(cascade = CascadeType.ALL)
    ChessBoard board;
    
}
