package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.DecoratorPattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;

@DecoratorPattern.ConcreteDecorator
@Entity
public class PromotionCommand extends CommandDecorator {

    @Enumerated(EnumType.STRING)
    private PieceType promotionType = PieceType.QUEEN;

    @Transient
    @JsonIgnore
    private PieceType originalType;

    public PromotionCommand() {
        super();
    }

    public PromotionCommand(Command wrappedCommand, PieceType promotionType) {
        super(wrappedCommand);
        this.promotionType = promotionType != null ? promotionType : PieceType.QUEEN;
    }

    @Override
    public void execute() {
        ensureWrappedCommand();
        MovePieceCommand moveCommand = (MovePieceCommand) wrappedCommand;
        originalType = moveCommand.getPiece().getType();
        wrappedCommand.execute();
        moveCommand.getPiece().setType(promotionType);
    }

    @Override
    public void undo() {
        ensureWrappedCommand();
        MovePieceCommand moveCommand = (MovePieceCommand) wrappedCommand;
        moveCommand.getPiece().setType(originalType);
        wrappedCommand.undo();
    }

    @Override
    public String toString() {
        return "Promotion to " + promotionType;
    }

    private void ensureWrappedCommand() {
        if (!(wrappedCommand instanceof MovePieceCommand)) {
            throw new IllegalStateException("Promotion commands must decorate a MovePieceCommand");
        }
    }
}
