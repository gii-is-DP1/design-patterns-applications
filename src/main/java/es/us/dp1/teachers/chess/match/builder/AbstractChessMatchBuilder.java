package es.us.dp1.teachers.chess.match.builder;

import java.time.LocalDateTime;

import es.us.dp1.teachers.chess.match.ChessMatch;
import es.us.dp1.teachers.chess.match.ChessMatchType;
import es.us.dp1.teachers.chess.user.User;

public abstract class AbstractChessMatchBuilder implements ChessMatchBuilder {    
    protected User creator;
    protected User opponent;
    protected String name;
    protected LocalDateTime start;

    

    @Override
    public ChessMatchBuilder withCreator(User creator) {
        this.creator = creator;
        return this;
    }

    @Override
    public ChessMatchBuilder withOpponent(User opponent) {
        this.opponent = opponent;
        return this;
    }

    @Override
    public ChessMatchBuilder withStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    @Override
    public ChessMatchBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    
}
