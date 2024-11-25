package es.us.dp1.teachers.chess.match.builder;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.teachers.chess.match.ChessMatch;
import es.us.dp1.teachers.chess.match.ChessMatchType;

@BuilderPattern.ConcreteBuilder(participants = {ChessMatch.class})
public class SpeedyChessMatchBuilder extends StandardChessMatchBuilder{
    @Override
    public ChessMatch build() {
        ChessMatch result=super.build();
        result.setType(ChessMatchType.Speedy);
        result.setTurnDuration(120L);
        return result;
    }
}
