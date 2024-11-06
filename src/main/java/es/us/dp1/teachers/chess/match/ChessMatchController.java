package es.us.dp1.teachers.chess.match;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.teachers.chess.exceptions.ResourceNotFoundException;
import es.us.dp1.teachers.chess.user.User;
import es.us.dp1.teachers.chess.user.UserService;


@RestController
@RequestMapping("/api/v1/matches")
public class ChessMatchController {

    UserService userService;
    ChessMatchService matchService;

    @Autowired
    public ChessMatchController(ChessMatchService service,UserService userService){
        this.matchService=service;
        this.userService=userService;
    }

    @GetMapping()
    public List<ChessMatch> getMatches(@RequestParam(name="ownerId", required=false) Integer ownerId) {        
        User user=null;
        if(ownerId==null)
            user=userService.findCurrentUser();
        else if(ownerId>0)
            user=userService.getUserById(ownerId);
        return matchService.getMatchesByCreatorId(user);
    }

    @PostMapping
    public ResponseEntity<ChessMatch> createMatch(@RequestParam(name="matchToUseAsExercise", required=false) Integer matchToUseAsExerciseId) {
        
        User user=userService.findCurrentUser();
        if(user==null)
            throw new BadCredentialsException("We could not find the current user");
        ChessMatch match=null;
        if(matchToUseAsExerciseId!=null)   // We create a match for the user as a copy or the match with the given ID   
            match=matchService.useMatchAsExercise(getMatch(matchToUseAsExerciseId),user); 
        else                               // We create a new match for the user
            match= matchService.initializeMatch(user);                
        return ResponseEntity.ok(match);
    }
    
    @GetMapping("/{id}")
    public ChessMatch getMatch(@PathVariable("id") Integer matchdId ) {
        Optional<ChessMatch> match=matchService.getMatchById(matchdId);
        if(!match.isPresent())
            throw new ResourceNotFoundException("Unable to find match with ID:"+matchdId);
        return match.get();
    }
    
}