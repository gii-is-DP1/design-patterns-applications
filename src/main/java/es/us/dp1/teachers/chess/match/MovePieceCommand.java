package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.CommandPattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.us.dp1.teachers.chess.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@CommandPattern.ConcreteCommand
@Entity
@Getter
@Setter
public class MovePieceCommand extends Command {

    @ManyToOne
    @JsonSerialize
    private Piece piece;
    @JsonSerialize
    private int fromX;
    @JsonSerialize
    private int fromY;
    @JsonSerialize
    private int toX;
    @JsonSerialize
    private int toY;

    public MovePieceCommand() {
        super();
    }

    public MovePieceCommand(Piece piece, int fromX, int fromY, int toX, int toY) {
        this.piece = piece;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public void execute() {
        piece.setXPosition(toX);
        piece.setYPosition(toY);
    }

    @Override
    public String toString() {
        return piece.color + " " + piece.type.getName() + " from (" + fromX + ", " + fromY + ") to (" + toX + ", " + toY + ")";
    }

}
