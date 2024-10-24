package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.us.dp1.teachers.chess.user.User;
import jakarta.transaction.Transactional;

@Service
public class ChessMatchService {
    
    MatchRepository repo;

    @Autowired
    public ChessMatchService(MatchRepository repo){
        this.repo=repo;
    }

    @


    @Transactional    
    public void save(ChessMatch m){
        repo.save(m);
    }
    
}
