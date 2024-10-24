package es.us.dp1.teachers.chess.match;

import java.time.LocalDateTime;
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
    public void save(ChessMatch m) {
        repo.save(m);
    }

    public ChessMatch initializeMatch(User user) {
        ChessMatch result = new ChessMatch();
        result.setCreator(user);
        result.setType(ChessMatchType.Standard); // Standard match type
        result.setTurnDuration(60000L); // Ten minutes turn time!
        initializeMatch(result);
        return result;
    }

    public void initializeMatch(ChessMatch match) {
        // Create a new ChessBoard for the match
        ChessBoard chessBoard = new ChessBoard();

        // Initialize the pieces on the chessboard
        initializePieces(chessBoard);

        // Associate the ChessBoard with the match
        match.setBoard(chessBoard);

        // Set the start time for the match
        match.setStart(LocalDateTime.now());

        // Optionally set turn duration and other match properties
        match.setTurnDuration(600L); // 10 minutes per turn, example
    }

    private void initializePieces(ChessBoard chessBoard) {
        // White pieces (Player 1)
        setupBackRow(chessBoard, 1, PieceColor.WHITE); // 1st rank (back row for white)
        setupPawns(chessBoard, 2, PieceColor.WHITE); // 2nd rank (pawns row for white)

        // Black pieces (Player 2)
        setupBackRow(chessBoard, 8, PieceColor.BLACK); // 8th rank (back row for black)
        setupPawns(chessBoard, 7, PieceColor.BLACK); // 7th rank (pawns row for black)
    }

    private void setupBackRow(ChessBoard chessBoard, int row, PieceColor color) {
        PieceType[] backRowOrder = { PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP,
                PieceType.QUEEN, PieceType.KING,
                PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK };

        for (int i = 0; i < 8; i++) {
            Piece piece = new Piece();
            piece.setXPosition(i + 1); // Files (columns) are 1 to 8
            piece.setYPosition(row); // The rank (row) is provided as a parameter
            piece.setType(backRowOrder[i]);
            piece.setColor(color); // Set the color of the piece (black or white)
            chessBoard.addPiece(piece);
        }
    }

    private void setupPawns(ChessBoard chessBoard, int row, PieceColor color) {
        for (int i = 1; i <= 8; i++) {
            Piece pawn = new Piece();
            pawn.setXPosition(i); // Files (columns) are 1 to 8
            pawn.setYPosition(row); // The rank (row) is provided as a parameter
            pawn.setType(PieceType.PAWN);
            pawn.setColor(color); // Set the color of the pawn (black or white)
            chessBoard.addPiece(pawn);
        }
    }

}
