package es.us.dp1.teachers.chess.match;

import java.util.List;

import org.jpatterns.gof.DecoratorPattern;

import jakarta.persistence.Entity;

@DecoratorPattern.ConcreteDecorator
@Entity
public class CastlingCommand extends MultiActionMoveCommand {

    public CastlingCommand() {
        super();
    }

    public CastlingCommand(MovePieceCommand kingMove, MovePieceCommand rookMove) {
        super(kingMove, List.of(rookMove));
    }

    @Override
    public String toString() {
        return "Castling (" + getInnerCommands().size() + " moves)";
    }
}
