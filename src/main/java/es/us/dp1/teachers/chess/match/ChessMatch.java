package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

import org.jpatterns.gof.BuilderPattern;
import org.jpatterns.gof.CommandPattern;
import org.jpatterns.gof.StatePattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.us.dp1.teachers.chess.model.NamedEntity;
import es.us.dp1.teachers.chess.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@NoArgsConstructor
@AllArgsConstructor
@BuilderPattern.Product
@StatePattern.Context
@CommandPattern.Client
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

    @OneToOne(cascade = CascadeType.ALL)
    State state;

    @OneToMany(cascade = CascadeType.ALL)
    List<Command> commandsHistory;

    public void executeCommand(Command command) {
        command.execute();
        commandsHistory.add(command);
    }

    public void movePiece(User user, int fromX, int fromY, int toX, int toY) {
        state.movePiece(user, fromX, fromY, toX, toY);
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
        match.setState(this.getState());
        return match;
    }

}
