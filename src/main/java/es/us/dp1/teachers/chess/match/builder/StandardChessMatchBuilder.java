package es.us.dp1.teachers.chess.match.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.teachers.chess.match.ChessBoard;
import es.us.dp1.teachers.chess.match.ChessMatch;
import es.us.dp1.teachers.chess.match.ChessMatchType;
import es.us.dp1.teachers.chess.match.Piece;
import es.us.dp1.teachers.chess.match.PieceColor;
import es.us.dp1.teachers.chess.match.PieceType;
import es.us.dp1.teachers.chess.user.User;

@BuilderPattern.ConcreteBuilder(participants = {ChessMatch.class})
public class StandardChessMatchBuilder extends AbstractChessMatchBuilder {
    @Override
    public ChessMatch build() {
        ChessMatch match = new ChessMatch();
        match.setName(name);
        match.setCreator(creator);
        match.setOpponent(opponent);
        match.setType(ChessMatchType.Standard);        
        initializeMatch(match);
        return match;
    }

    

    public void initializeMatch(ChessMatch match) {
        // Create a new ChessBoard for the match
        ChessBoard chessBoard = new ChessBoard();
        // Initialize the pieces on the chessboard
        initializePieces(chessBoard);
        // Associate the ChessBoard with the match
        match.setBoard(chessBoard);
        // Set the start time for the match; this could be the current time or the provided time
        if(start!=null)
            match.setStart(start);
        else
            match.setStart(LocalDateTime.now());
        // Optionally set turn duration and other match properties
        match.setTurnDuration(600L); // 10 minutes per turn, example
    }

    private void initializePieces(ChessBoard chessBoard) {
        if(chessBoard.getPieces()==null)
            chessBoard.setPieces(new ArrayList<Piece>());
        else    chessBoard.getPieces().clear();
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
            piece.setBoard(chessBoard);
            chessBoard.getPieces().add(piece);
        }
    }

    private void setupPawns(ChessBoard chessBoard, int row, PieceColor color) {
        for (int i = 1; i <= 8; i++) {
            Piece pawn = new Piece();
            pawn.setXPosition(i); // Files (columns) are 1 to 8
            pawn.setYPosition(row); // The rank (row) is provided as a parameter
            pawn.setType(PieceType.PAWN);
            pawn.setColor(color); // Set the color of the pawn (black or white)
            pawn.setBoard(chessBoard);
            chessBoard.getPieces().add(pawn);
        }
    }                   

}
