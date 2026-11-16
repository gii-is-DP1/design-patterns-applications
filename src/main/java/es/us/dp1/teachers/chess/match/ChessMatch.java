package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;

import org.jpatterns.gof.BuilderPattern;
import org.jpatterns.gof.StatePattern;

import es.us.dp1.teachers.chess.model.NamedEntity;
import es.us.dp1.teachers.chess.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@BuilderPattern.Product
@StatePattern.Context
public class ChessMatch extends NamedEntity implements Cloneable{
    LocalDateTime start;
    LocalDateTime finish;
    Long turnDuration;

    ChessMatchType type;

    @ManyToOne
    User creator;

    @ManyToOne
    User opponent;

    @OneToOne(cascade = CascadeType.ALL)
    ChessBoard board;

    // orphanRemoval: the previous state is deleted from the DB when the match changes its state
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    ChessMatchState state;

    public void movePiece(User user, int fromX, int fromY, int toX, int toY) {
        state.movePiece(user, fromX, fromY, toX, toY);
    }

    public void changeState(ChessMatchState newState) {
        newState.setMatch(this);
        this.state = newState;
    }

    public ChessMatch clone() {
        ChessMatch match = new ChessMatch();
        match.setName(this.getName());
        match.setStart(this.getStart());
        match.setFinish(this.getFinish());
        match.setTurnDuration(this.getTurnDuration());
        match.setType(this.getType());
        match.setCreator(this.getCreator());
        match.setOpponent(this.getOpponent());
        match.setBoard(getBoard().clone());
        match.changeState(this.getState().clone()); // A new state for the new match
        return match;
    }

}
