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
@DiscriminatorValue("WhiteMoveState")
@StatePattern.ConcreteState
public class WhiteMoveState extends State {

    @Override
    public void movePiece(User user, int fromX, int fromY, int toX, int toY) {
        if(!user.equals(match.getCreator()))
            throw new IllegalMoveException("It is not your turn!");
        else {
            Piece target = match.getBoard().getPieceAt(fromX, fromY);
            if (target==null || target.getColor().equals(PieceColor.BLACK))
                throw new IllegalMoveException("You cannot move other player's pieces");
            else {
                target.setXPosition(toX);
                target.setYPosition(toY);
                State nexState = new BlackMoveState();
                nexState.setMatch(match);
                match.setState(nexState);
            }
        }

    }

}
