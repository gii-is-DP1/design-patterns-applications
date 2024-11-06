package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.teachers.chess.model.NamedEntity;
import es.us.dp1.teachers.chess.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@NoArgsConstructor
@AllArgsConstructor
@BuilderPattern.Product
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
