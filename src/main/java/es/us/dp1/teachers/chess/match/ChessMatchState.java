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
public abstract class ChessMatchState extends BaseEntity {

    @OneToOne(mappedBy = "state")
    @JsonIgnore
    protected ChessMatch match;

    public void movePiece(User user, int fromX, int fromY, int toX, int toY) {
        if(!user.equals(expectedMovingPlayer()))
            throw new IllegalMoveException("It is not your turn!");
        else {
            Piece target = match.getBoard().getPieceAt(fromX, fromY);
            if (target==null || !target.getColor().equals(expectedTargetColor()))
                throw new IllegalMoveException("You cannot move other player's pieces");
            else {
                boolean castlingMove = isCastlingMove(target, fromX, fromY, toX, toY);
                Piece destinationPiece = match.getBoard().validateDestination(target, toX, toY);
                if (castlingMove && destinationPiece != null) {
                    throw new IllegalMoveException("Castling destination must be empty");
                }
                target.getType().validateMove(target, destinationPiece, fromX, fromY, toX, toY);
                match.getBoard().validatePathClear(target, fromX, fromY, toX, toY);
                Command command = buildCommandForMove(target, destinationPiece, fromX, fromY, toX, toY);
                match.executeCommand(command);
                ChessMatchState nexState = nextState();
                nexState.setMatch(match);
                match.setState(nexState);
            }
        }
    }

    protected Command buildCommandForMove(Piece target, Piece destinationPiece, int fromX, int fromY, int toX, int toY) {
        if (isCastlingMove(target, fromX, fromY, toX, toY)) {
            return createCastlingCommand(target, fromX, fromY, toX, toY);
        }
        if (isPromotionMove(target, toY)) {
            return new PromotionCommand(target,  fromX, fromY,  toX, toY, PieceType.QUEEN, destinationPiece);
        }
        if (destinationPiece != null) {
            return new CaptureCommand(target, destinationPiece, fromX, fromY, toX, toY);
        }
        return new MovePieceCommand(target, fromX, fromY, toX, toY);
    }

    private boolean isPromotionMove(Piece target, int toY) {
        if (target.getType() != PieceType.PAWN) {
            return false;
        }
        return (target.getColor() == PieceColor.WHITE && toY == 8) || (target.getColor() == PieceColor.BLACK && toY == 1);
    }

    private boolean isCastlingMove(Piece target, int fromX, int fromY, int toX, int toY) {
        return target.getType() == PieceType.KING && fromY == toY && Math.abs(fromX - toX) == 2;
    }

    private Command createCastlingCommand(Piece king, int fromX, int fromY, int toX, int toY) {
        int rookFromX = toX > fromX ? 8 : 1;
        Piece rook = match.getBoard().getPieceAt(rookFromX, fromY);
        if (rook == null || rook.getType() != PieceType.ROOK || rook.getColor() != king.getColor()) {
            throw new IllegalMoveException("Castling requires an unmoved rook on the same rank");
        }
        int rookToX = toX > fromX ? toX - 1 : toX + 1;
        Piece rookDestination = match.getBoard().validateDestination(rook, rookToX, toY);
        if (rookDestination != null) {
            throw new IllegalMoveException("Castling squares must be empty");
        }
        match.getBoard().validatePathClear(rook, rookFromX, fromY, rookToX, toY);
        MovePieceCommand rookMove = new MovePieceCommand(rook, rookFromX, fromY, rookToX, toY);
        return new CastlingCommand(king, fromX, fromY, toX, toY, rookMove);
    }

    public abstract PieceColor expectedTargetColor();
    public abstract User expectedMovingPlayer();
    public abstract ChessMatchState nextState();
}
