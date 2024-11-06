package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.teachers.chess.user.User;

@Service
public class ChessMatchService {

    MatchRepository repo;    

    @Autowired
    public ChessMatchService(MatchRepository repo) {
        this.repo = repo;        
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
        ChessMatch result = new ChessMatch();
        ChessBoard board =new ChessBoard();        
        result.setCreator(user);
        result.setType(ChessMatchType.Standard);
        result.setBoard(board);
        result.setStart(LocalDateTime.now());
        board.setPieces(new ArrayList<Piece>());
        result=save(result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<ChessMatch> getMatches() {
        return repo.findAll();
    }

    @Transactional
    public ChessMatch useMatchAsExercise(ChessMatch match, User user) {
        // TODO modify this dummy implementation that just creates a new match
        return initializeMatch(user);
    }

    @Transactional(readOnly = true)
    public List<ChessMatch> getMatchesByCreatorId(User ownerId) {
        return repo.findByCreator(ownerId);
    }

}
