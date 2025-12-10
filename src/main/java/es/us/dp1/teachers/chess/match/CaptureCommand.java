package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.CommandPattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@CommandPattern.ConcreteCommand
@Entity
@Getter
@Setter
public class CaptureCommand extends MovePieceCommand {

    @ManyToOne
    @JsonSerialize
    private Piece capturedPiece;

    public CaptureCommand() {
        super();
    }

    public CaptureCommand(Piece piece, Piece capturedPiece, int fromX, int fromY, int toX, int toY) {
        super(piece, fromX, fromY, toX, toY);
        this.capturedPiece = capturedPiece;
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.CAPTURE;
    }

    @Override
    public void execute() {
        ChessBoard board = getPiece() != null ? getPiece().getBoard() : null;
        super.execute();
        if (board != null && capturedPiece != null) {
            board.getPieces().remove(capturedPiece);
            capturedPiece.setBoard(null);
        }
        
    }

    @Override
    public void undo() {
        super.undo();
        ChessBoard board = getPiece() != null ? getPiece().getBoard() : null;
        if (board != null && capturedPiece != null) {
            capturedPiece.setBoard(board);
            if (!board.getPieces().contains(capturedPiece)) {
                board.getPieces().add(capturedPiece);
            }
        }
    }

    @Override
    public String toString() {
        String capturedDesc = capturedPiece != null ? capturedPiece.getColor() + " " + capturedPiece.getType() : "opponent piece";
        Piece attacker = getPiece();
        String attackerDesc = attacker != null ? attacker.getColor() + " " + attacker.getType() : "Piece";
        return attackerDesc + " captures " + capturedDesc + " from (" + getFromX() + ", " + getFromY() + ") to (" + getToX() + ", " + getToY() + ")";
    }
}
