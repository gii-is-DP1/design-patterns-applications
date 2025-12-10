package es.us.dp1.teachers.chess.match;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PieceExchange extends Command {

    @ManyToOne
    private Piece oldPiece;

    @ManyToOne
    private Piece newPiece;

    @ManyToOne
    @JsonIgnore
    private ChessBoard board;

    public PieceExchange() {
        super();
    }

    public PieceExchange(Piece oldPiece, Piece newPiece) {
        this.oldPiece = oldPiece;
        this.newPiece = newPiece;
        this.board = oldPiece != null ? oldPiece.getBoard() : null;
        if (this.board != null && this.newPiece != null) {
            this.newPiece.setBoard(this.board);
        }
    }

    @Override
    public MoveType getMoveType() {        
        return MoveType.PROMOTION;
    }

    @Override
    public void execute() {
        if (board == null) {
            return;
        }
        if (oldPiece != null) {
            board.getPieces().remove(oldPiece);
            oldPiece.setBoard(null);
        }
        if (newPiece != null) {
            newPiece.setBoard(board);
            if (!board.getPieces().contains(newPiece)) {
                board.getPieces().add(newPiece);
            }
        }
    }

    @Override
    public void undo() {
        if (board == null) {
            return;
        }
        if (newPiece != null) {
            board.getPieces().remove(newPiece);
            newPiece.setBoard(null);
        }
        if (oldPiece != null) {
            oldPiece.setBoard(board);
            if (!board.getPieces().contains(oldPiece)) {
                board.getPieces().add(oldPiece);
            }
        }
    }

    @Override
    public String toString() {
        return "Exchange of a  " + (oldPiece != null ? oldPiece.type.getName() : "piece") + " by a " + (newPiece != null ? newPiece.type.getName() : "piece");
    }
    
}
