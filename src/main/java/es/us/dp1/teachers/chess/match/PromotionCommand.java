package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.DecoratorPattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import es.us.dp1.teachers.chess.match.ChessBoard;

@DecoratorPattern.ConcreteDecorator
@Entity
public class PromotionCommand extends CommandDecorator {

    @Enumerated(EnumType.STRING)
    private PieceType promotionType = PieceType.QUEEN;

    @Transient
    @JsonIgnore
    private PieceType originalType;

    @ManyToOne
    @JsonIgnore
    private Piece capturedPiece;


    public PromotionCommand() {
        super();
    }

    public PromotionCommand(Piece piece, int fromX, int fromY, int toX, int toY, PieceType promotionType) {
        super(piece, fromX, fromY,toX,toY,new PieceExchange(piece, new Piece(piece.getColor(), promotionType, toX, toY)));
        this.promotionType = promotionType != null ? promotionType : PieceType.QUEEN;
    }

    public PromotionCommand(Piece piece, int fromX, int fromY, int toX, int toY, PieceType promotionType, Piece capturedPiece) {
        this(piece, fromX, fromY, toX, toY, promotionType);
        this.capturedPiece = capturedPiece;
    }

    public PieceType getPromotionType() {
        return promotionType;
    }

    @Override
    public MoveType getMoveType() {
        return MoveType.PROMOTION;
    }

    @Override
    public void execute() {
        if (capturedPiece != null && capturedPiece.getBoard() != null) {
            capturedPiece.getBoard().getPieces().remove(capturedPiece);
            capturedPiece.setBoard(null);
        }
        super.execute();        
        wrappedCommand.execute();
        
    }

    @Override
    public void undo() {
        wrappedCommand.undo();
        super.undo();
        ChessBoard board = getPiece() != null ? getPiece().getBoard() : null;
        if (capturedPiece != null && board != null) {
            capturedPiece.setBoard(board);
            if (!board.getPieces().contains(capturedPiece)) {
                board.getPieces().add(capturedPiece);
            }
        }
    }

    @Override
    public String toString() {
        Piece p = getPiece();
        String color = p != null ? p.getColor().toString() : "Pawn";
        return color + " pawn promotion to " + promotionType + " at (" + getToX() + ", " + getToY() + ")";
    }
    
}
