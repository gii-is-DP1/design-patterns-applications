package es.us.dp1.teachers.chess.match;

import org.jpatterns.gof.DecoratorPattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.OneToOne;

@DecoratorPattern.Decorator(participants = {Command.class})
@Entity
@Inheritance
public abstract class CommandDecorator extends MovePieceCommand {

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    protected Command wrappedCommand;
    
    public CommandDecorator() {
        super();
    }
    
    protected CommandDecorator(Piece piece, int fromX, int fromY, int toX, int toY, Command wrappedCommand) {
        super(piece, fromX, fromY, toX, toY);
        this.wrappedCommand = wrappedCommand;
    }

    public void execute(){
        super.execute();
        if (wrappedCommand != null) 
            wrappedCommand.execute();        
    }

    public void undo(){
        super.undo();
        if (wrappedCommand != null) 
            wrappedCommand.undo();        
    }
    
}
