package es.us.dp1.teachers.chess.match;

import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.DecoratorPattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@DecoratorPattern.ConcreteDecorator
@Entity
public class MultiActionMoveCommand extends CommandDecorator {

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Command> additionalCommands = new ArrayList<>();

    public MultiActionMoveCommand() {
        super();
    }

    public MultiActionMoveCommand(Command wrappedCommand, List<Command> additionalCommands) {
        super(wrappedCommand);
        if (additionalCommands != null) {
            this.additionalCommands.addAll(additionalCommands);
        }
    }

    @Override
    public void execute() {
        if (wrappedCommand != null) {
            wrappedCommand.execute();
        }
        for (Command command : additionalCommands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (int i = additionalCommands.size() - 1; i >= 0; i--) {
            additionalCommands.get(i).undo();
        }
        if (wrappedCommand != null) {
            wrappedCommand.undo();
        }
    }

    @Override
    public List<Command> getInnerCommands() {
        List<Command> commands = new ArrayList<>(super.getInnerCommands());
        for (Command additional : additionalCommands) {
            commands.addAll(additional.getInnerCommands());
        }
        return commands;
    }
}
