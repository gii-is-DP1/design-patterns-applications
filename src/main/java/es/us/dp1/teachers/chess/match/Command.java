package es.us.dp1.teachers.chess.match;

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
}
