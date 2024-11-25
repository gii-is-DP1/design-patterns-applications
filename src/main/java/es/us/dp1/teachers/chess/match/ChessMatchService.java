package es.us.dp1.teachers.chess.match;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jpatterns.gof.BuilderPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.teachers.chess.match.builder.ChessMatchBuilderDirector;
import es.us.dp1.teachers.chess.user.User;

@Service
public class ChessMatchService {

    ChessMatchBuilderDirector builder;
    MatchRepository repo;    

    @Autowired
    public ChessMatchService(MatchRepository repo, ChessMatchBuilderDirector builder) {
        this.repo = repo;        
        this.builder = builder;
    }

    @Transactional(readOnly = true)
    public List<ChessMatch> getMatchesByCreator(User creator) {
        return repo.findByCreator(creator);
    }

    @Transactional(readOnly = true)
    public List<ChessMatch> getMatchesByOpponent(User opponent) {
        return repo.findByOpponent(opponent);
    }

    @Transactional(readOnly = true)
    public Set<ChessMatch> getMatchesPlayedBy(User user) {
        Set<ChessMatch> matches = new HashSet<>(getMatchesByCreator(user));
        matches.addAll(getMatchesByOpponent(user));
        return matches;
    }

    @Transactional(readOnly = true)
    public Optional<ChessMatch> getMatchById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public ChessMatch save(ChessMatch m) {
        return repo.save(m);
    }
    
    @Transactional
    public ChessMatch initializeMatch(User user) {
        
        ChessMatch result = builder.ofType(ChessMatchType.Standard)
                                    .withCreator(user)                                    
                                    .build();
        result=save(result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<ChessMatch> getMatches() {
        return repo.findAll();
    }

    @Transactional
    public ChessMatch useMatchAsExercise(ChessMatch match, User user) {
        ChessMatch exercise=(ChessMatch) match.clone();
        exercise.setCreator(user);
        exercise=save(exercise);
        return exercise;
    }

    @Transactional(readOnly = true)
    public List<ChessMatch> getMatchesByCreatorId(User ownerId) {
        return repo.findByCreator(ownerId);
    }

}
