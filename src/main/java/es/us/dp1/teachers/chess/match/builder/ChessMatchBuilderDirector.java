package es.us.dp1.teachers.chess.match.builder;

import java.util.Map;
import java.util.Set;

import org.jpatterns.gof.BuilderPattern;
import org.springframework.stereotype.Component;

import es.us.dp1.teachers.chess.match.ChessMatch;
import es.us.dp1.teachers.chess.match.ChessMatchType;

@Component
@BuilderPattern.Director(participants = {ChessMatch.class, StandardChessMatchBuilder.class,SpeedyChessMatchBuilder.class})
public class ChessMatchBuilderDirector {
    Map<ChessMatchType, ChessMatchBuilder> builders=Map.of(ChessMatchType.Standard, new StandardChessMatchBuilder(),
                                                            ChessMatchType.Speedy, new SpeedyChessMatchBuilder());

    public ChessMatchBuilder ofType(ChessMatchType type) {        
        if(!builders.containsKey(type)) 
            throw new IllegalArgumentException("Unsupported match type: " + type);        
        return builders.get(type);
    }

    public Set<ChessMatchType> getSupportedMatchTypes() {
        return builders.keySet();
    }
}

