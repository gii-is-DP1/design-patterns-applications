package es.us.dp1.teachers.chess.match;

import java.util.List;

import org.jpatterns.gof.DecoratorPattern;

import jakarta.persistence.Entity;

@DecoratorPattern.ConcreteDecorator
@Entity
public class CastlingCommand extends CommandDecorator {

    public CastlingCommand() {
        super();
    }

    public CastlingCommand(Piece piece, int fromX, int fromY, int toX, int toY, MovePieceCommand rookMove) {
        super(piece, fromX, fromY, toX, toY, rookMove);    
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.CASTLING;
    }

    @Override
    public String toString() {
        return "Castling (" + super.toString()+" + " + wrappedCommand.toString() + " moves)";
    }
}
