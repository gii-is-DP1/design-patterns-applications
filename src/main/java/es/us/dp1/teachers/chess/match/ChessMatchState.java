package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.StatePattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.teachers.chess.exceptions.IllegalMoveException;
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
public abstract class ChessMatchState extends BaseEntity implements Cloneable {

    @OneToOne(mappedBy = "state")
    @JsonIgnore
    protected ChessMatch match;

    // Each match must have its own state (the relationship is one to one), so when a
    // match is cloned we create a new state of the same concrete class (not persisted yet
    // and not associated to any match).
    @Override
    public ChessMatchState clone() {
        try {
            ChessMatchState copy = (ChessMatchState) super.clone();
            copy.setId(null);
            copy.setMatch(null);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    public void movePiece(User user, int fromX, int fromY, int toX, int toY) {
        if(!user.equals(expectedMovingPlayer()))
            throw new IllegalMoveException("It is not your turn!");
        else {
            Piece target = match.getBoard().getPieceAt(fromX, fromY);
            if (target==null || !target.getColor().equals(expectedTargetColor()))
                throw new IllegalMoveException("You cannot move other player's pieces");
            else {
                target.setXPosition(toX);
                target.setYPosition(toY);
                match.changeState(nextState());
            }
        }
    }

    public abstract PieceColor expectedTargetColor();
    public abstract User expectedMovingPlayer();
    public abstract ChessMatchState nextState();
}
