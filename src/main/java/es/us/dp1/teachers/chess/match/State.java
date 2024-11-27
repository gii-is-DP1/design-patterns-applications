package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.StatePattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.teachers.chess.model.BaseEntity;
import es.us.dp1.teachers.chess.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@StatePattern.State
public abstract class State extends BaseEntity {

    @OneToOne(mappedBy = "state")
    @JsonIgnore
    protected ChessMatch match;

    public void movePiece(User user, int fromX, int fromY, int toX, int toY) {
        throw new UnsupportedOperationException();
    }
}
