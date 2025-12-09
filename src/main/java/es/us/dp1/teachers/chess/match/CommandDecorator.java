package es.us.dp1.teachers.chess.match;

import java.util.List;

import org.jpatterns.gof.DecoratorPattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.OneToOne;

@DecoratorPattern.Decorator(participants = {Command.class})
@Entity
@Inheritance
public abstract class CommandDecorator extends Command {

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    protected Command wrappedCommand;

    protected CommandDecorator() {
        super();
    }

    protected CommandDecorator(Command wrappedCommand) {
        this.wrappedCommand = wrappedCommand;
    }

    @Override
    public List<Command> getInnerCommands() {
        return wrappedCommand != null ? wrappedCommand.getInnerCommands() : List.of();
    }
}
