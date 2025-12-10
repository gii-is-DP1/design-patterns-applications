package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.us.dp1.teachers.chess.exceptions.IllegalMoveException;
import es.us.dp1.teachers.chess.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChessBoard extends BaseEntity implements Cloneable{
    boolean creatorTurn; // Not necessary anymore because we have the state pattern
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

    /**
     * Validates that the target square is inside the board and not occupied by a
     * piece of the same color.
     */
    public Piece validateDestination(Piece movingPiece, int toX, int toY) {
        if (toX < 1 || toX > 8 || toY < 1 || toY > 8) {
            throw new IllegalMoveException("The destination square is outside the board");
        }
        Piece target = getPieceAt(toX, toY);
        if (target != null && target.getColor() == movingPiece.getColor()) {
            throw new IllegalMoveException("You already have a piece on that square");
        }
        return target;
    }

    /**
     * Validates that the movement path is clear (no jumps) for all pieces except
     * knights. The destination square is not checked here; callers are expected to
     * validate it separately.
     */
    public void validatePathClear(Piece movingPiece, int fromX, int fromY, int toX, int toY) {
        if (movingPiece.getType() == PieceType.KNIGHT) {
            return;
        }

        int dx = toX - fromX;
        int dy = toY - fromY;
        if (dx == 0 && dy == 0) {
            return;
        }

        int stepX = Integer.compare(dx, 0);
        int stepY = Integer.compare(dy, 0);

        // Only relevant for straight or diagonal moves
        if (!(stepX == 0 || stepY == 0 || Math.abs(dx) == Math.abs(dy))) {
            return;
        }

        int currentX = fromX + stepX;
        int currentY = fromY + stepY;
        while (currentX != toX || currentY != toY) {
            Piece blockingPiece = getPieceAt(currentX, currentY);
            if (blockingPiece != null) {
                throw new IllegalMoveException("Pieces cannot jump over other pieces");
            }
            currentX += stepX;
            currentY += stepY;
        }
    }

    /**
     * Builds the appropriate Command for a given move, applying board-level move validation and
     * special move determination (castling, promotion, capture).
     */
    public Command createCommandForMove(Piece movingPiece, int fromX, int fromY, int toX, int toY) {
        Piece destinationPiece = validateDestination(movingPiece, toX, toY);
        boolean castlingMove = isCastlingMove(movingPiece, fromX, fromY, toX, toY);
        if (castlingMove && destinationPiece != null) {
            throw new IllegalMoveException("Castling destination must be empty");
        }
        movingPiece.getType().validateMove(movingPiece, destinationPiece, fromX, fromY, toX, toY);
        validatePathClear(movingPiece, fromX, fromY, toX, toY);

        if (castlingMove) {
            return createCastlingCommand(movingPiece, fromX, fromY, toX, toY);
        }
        if (isPromotionMove(movingPiece, toY)) {
            return new PromotionCommand(movingPiece,  fromX, fromY,  toX, toY, PieceType.QUEEN, destinationPiece);
        }
        if (destinationPiece != null) {
            return new CaptureCommand(movingPiece, destinationPiece, fromX, fromY, toX, toY);
        }
        return new MovePieceCommand(movingPiece, fromX, fromY, toX, toY);
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
        Piece rook = getPieceAt(rookFromX, fromY);
        if (rook == null || rook.getType() != PieceType.ROOK || rook.getColor() != king.getColor()) {
            throw new IllegalMoveException("Castling requires an unmoved rook on the same rank");
        }
        int rookToX = toX > fromX ? toX - 1 : toX + 1;
        Piece rookDestination = validateDestination(rook, rookToX, toY);
        if (rookDestination != null) {
            throw new IllegalMoveException("Castling squares must be empty");
        }
        validatePathClear(rook, rookFromX, fromY, rookToX, toY);
        MovePieceCommand rookMove = new MovePieceCommand(rook, rookFromX, fromY, rookToX, toY);
        return new CastlingCommand(king, fromX, fromY, toX, toY, rookMove);
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
