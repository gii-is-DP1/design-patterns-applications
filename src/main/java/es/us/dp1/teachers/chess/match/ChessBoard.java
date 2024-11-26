package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.us.dp1.teachers.chess.exceptions.IllegalMoveException;
import es.us.dp1.teachers.chess.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
public class ChessBoard extends BaseEntity implements Cloneable{
    boolean creatorTurn;
    LocalDateTime currentTurnStart;
    boolean jaque;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    List<Piece> pieces;

    public void addPiece(Piece piece) {
        piece.setBoard(this);
        if(!pieces.contains(piece))
            pieces.add(piece);
    }

    public Piece getPieceAt(int x, int y) {
        for(Piece piece : pieces) {
            if(piece.getXPosition()==x && piece.getYPosition()==y)
                return piece;
        }
        return null;
    }

    public Piece movePiece(int fromX, int fromY, int toX, int toY) {
        Piece target = getPieceAt(fromX, fromY);
        if(target!=null) {
            if((target.getColor().equals(PieceColor.WHITE) && !isCreatorTurn()) ||
                    (target.getColor().equals(PieceColor.BLACK) && isCreatorTurn()))
                throw new IllegalMoveException("You cannot move other player's pieces");
            target.setXPosition(toX);
            target.setYPosition(toY);
            setCreatorTurn(!isCreatorTurn()); // Change turn
        }

        return target;
    }

    public ChessBoard clone() {
        ChessBoard board = new ChessBoard();
        board.setCreatorTurn(this.isCreatorTurn());
        board.setCurrentTurnStart(this.getCurrentTurnStart());
        board.setJaque(this.isJaque());
        board.pieces = new ArrayList<>(pieces.size());
        for(Piece piece : this.getPieces()) {
            Piece newPiece=piece.clone();
            newPiece.setBoard(board);
            board.pieces.add(newPiece);
        }
        return board;
    }


}
