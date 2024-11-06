package es.us.dp1.teachers.chess.match.builder;

import java.time.LocalDateTime;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.teachers.chess.match.ChessMatch;
import es.us.dp1.teachers.chess.match.ChessMatchType;
import es.us.dp1.teachers.chess.user.User;
@BuilderPattern.Builder
public interface ChessMatchBuilder {    
    ChessMatchBuilder withCreator(User creator);
    ChessMatchBuilder withOpponent(User opponent);
    ChessMatchBuilder withStart(LocalDateTime start);        
    ChessMatchBuilder withName(String ame);        
    ChessMatch build();
}
