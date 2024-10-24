package es.us.dp1.teachers.chess.match;

import java.util.HashSet;
import java.util.List;
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
    public ChessMatchService(MatchRepository repo){
        this.repo=repo;
    }
    
    @Transactional(readOnly=true)
    public List<ChessMatch> getMatchesByCreator(User creator){
        return repo.findByCreator(creator);
    }
    @Transactional(readOnly=true)
    public List<ChessMatch> getMatchesByOpponent(User opponent){
        return repo.findByOpponent(opponent);
    }
    @Transactional(readOnly=true)
    public Set<ChessMatch> getMatchesPlayedBy(User user){
        Set<ChessMatch> matches=new HashSet<>(getMatchesByCreator(user));
        matches.addAll(getMatchesByOpponent(user));
        return matches;
    }
    
    @Transactional(readOnly=true)
    public Optional<ChessMatch> getMatchById(Integer id){
        return repo.findById(id);
    }

    @Transactional    
    public void save(ChessMatch m){
        repo.save(m);
    }
    
}
