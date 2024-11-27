package es.us.dp1.teachers.chess.match;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jpatterns.gof.BuilderPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.teachers.chess.exceptions.IllegalMoveException;
import es.us.dp1.teachers.chess.match.builder.ChessMatchBuilderDirector;
import es.us.dp1.teachers.chess.user.User;
import es.us.dp1.teachers.chess.user.UserService;

@Service
public class ChessMatchService {

    ChessMatchBuilderDirector builder;
    MatchRepository repo;
    UserService userService;

    @Autowired
    public ChessMatchService(MatchRepository repo, ChessMatchBuilderDirector builder, UserService userService) {
        this.repo = repo;
        this.builder = builder;
        this.userService = userService;
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
                                    .withOpponent(userService.findUser(5))
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

    @Transactional
    public void movePiece(ChessMatch match, int x1, int y1, int x2, int y2) {
        User user = userService.findCurrentUser();
        match.movePiece(user, x1, y1, x2, y2);
        save(match);
    }

}
