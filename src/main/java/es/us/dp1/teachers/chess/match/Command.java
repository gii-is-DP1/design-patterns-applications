package es.us.dp1.teachers.chess.match;

import java.util.List;

import org.jpatterns.gof.CommandPattern;

import es.us.dp1.teachers.chess.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;

@CommandPattern.Command
@Entity
@Inheritance
public abstract class Command extends BaseEntity{

    public abstract void execute();

    public abstract void undo();

    /**
     * Returns the list of atomic commands that were executed.
     * Decorators can override this to expose the internal sequence.
     */
    public List<Command> getInnerCommands() {
        return List.of(this);
    }
}
