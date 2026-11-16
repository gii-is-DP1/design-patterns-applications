package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.BuilderPattern;
import org.jpatterns.gof.CommandPattern;
import org.jpatterns.gof.StatePattern;

import es.us.dp1.teachers.chess.exceptions.IllegalMoveException;
import es.us.dp1.teachers.chess.model.NamedEntity;
import es.us.dp1.teachers.chess.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@CommandPattern.Invoker // It executes the commands and keeps the history (to undo them)
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

    // orphanRemoval: an undone command is deleted from the DB
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Command> commandsHistory = new ArrayList<>();

    public void executeCommand(Command command) {
        command.execute();
        commandsHistory.add(command);
    }

    public void undoLastCommand() {
        if(commandsHistory.isEmpty())
            throw new IllegalMoveException("There are no movements to undo");
        Command command = commandsHistory.remove(commandsHistory.size()-1);
        command.undo();
        changeState(state.nextState()); // Revert to previous state (only works because we have 2 states!)
    }

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
