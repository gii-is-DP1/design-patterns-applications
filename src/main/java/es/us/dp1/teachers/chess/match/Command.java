package es.us.dp1.teachers.chess.match;

import java.util.List;

import org.jpatterns.gof.CommandPattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.us.dp1.teachers.chess.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;

@CommandPattern.Command
@Entity
@Inheritance
public abstract class Command extends BaseEntity{

    public abstract void execute();

    public abstract void undo();

    public MoveType getMoveType() {
        return MoveType.STANDARD;
    }

    @JsonProperty("moveType")
    public String getMoveTypeName() {
        return getMoveType().name();
    }

    @JsonProperty("description")
    public String getDescription() {
        return toString();
    }
    
}
