package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.StatePattern;

import es.us.dp1.teachers.chess.exceptions.IllegalMoveException;
import es.us.dp1.teachers.chess.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("BlackMoveState")
@StatePattern.ConcreteState
public class BlackMoveState extends ChessMatchState {

    @Override
    public PieceColor expectedTargetColor() {
        return PieceColor.BLACK;
    }

    @Override
    public User expectedMovingPlayer() {
        return match.getOpponent();
    }

    @Override
    public ChessMatchState nextState() {
        return new WhiteMoveState();
    }

    
}
