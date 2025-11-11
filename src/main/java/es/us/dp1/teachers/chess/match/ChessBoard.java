package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
import java.util.List;

import es.us.dp1.teachers.chess.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChessBoard extends BaseEntity{
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

    
}
