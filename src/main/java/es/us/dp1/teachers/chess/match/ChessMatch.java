package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.BuilderPattern;
import org.jpatterns.gof.CommandPattern;
import org.jpatterns.gof.StatePattern;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    ChessMatchState state;

    @OneToMany(cascade = CascadeType.ALL)
    List<Command> commandsHistory;

    public void executeCommand(Command command) {
        ensureCommandsHistory();
        command.execute();
        commandsHistory.add(command);
    }

    public void undoLastCommand() {
        if(commandsHistory != null && commandsHistory.size()>0) {
            Command command = commandsHistory.remove(commandsHistory.size()-1);
            command.undo();
            state = state.nextState(); // Revert to previous state (only works because we have 2 states!)
        }
    }

    public void movePiece(User user, int fromX, int fromY, int toX, int toY) {
        state.movePiece(user, fromX, fromY, toX, toY);
    }

    @JsonProperty("commandsHistory")
    public List<Command> getCommandsHistory() {
        if (commandsHistory == null) {
            return List.of();
        }
        return commandsHistory.stream()
                .flatMap(command -> command.getInnerCommands().stream())
                .toList();
    }

    private void ensureCommandsHistory() {
        if (commandsHistory == null) {
            commandsHistory = new ArrayList<>();
        }
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
        match.setCommandsHistory(new ArrayList<>());
        return match;
    }

}
