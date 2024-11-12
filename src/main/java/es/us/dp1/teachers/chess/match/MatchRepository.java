package es.us.dp1.teachers.chess.match;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.us.dp1.teachers.chess.user.User;

public interface MatchRepository extends CrudRepository<ChessMatch,Integer>{

    List<ChessMatch> findAll();

    List<ChessMatch> findByCreator(User creator);

    List<ChessMatch> findByOpponent(User opponent);    
    
}
