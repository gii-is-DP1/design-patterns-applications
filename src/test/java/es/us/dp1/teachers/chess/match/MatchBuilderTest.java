package es.us.dp1.teachers.chess.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.ArgumentsSource;
//import org.junit.jupiter.params.provider.EnumSource;

import es.us.dp1.teachers.chess.user.User;

@Disabled
public class MatchBuilderTest {
    
    private ChessMatchBuilder builder;
    private User creator;
    private ChessMatch match;
    
    @BeforeEach
    public void setUp() {
        builder = new ChessMatchBuilder();
        creator = new User(); // Assuming User has a default constructor
        ChessMatchType matchType = ChessMatchType.Standard; // Example match type
        match = builder.build(creator, matchType);
    }

    @Test
    public void testTotalNumberOfPieces() {
        // Get the total number of pieces
        int totalPieces = match.getBoard().getPieces().size();
        
        // Assert that the total number of pieces is 32
        assertEquals(32, totalPieces, "The total number of pieces should be 32.");
    }
    
    @Test
    public void testNumberOfPiecesPerType() {
        // Variables to count different types of pieces
        int pawns = 0, rooks = 0, knights = 0, bishops = 0, queens = 0, kings = 0;

        for (Piece piece : match.getBoard().getPieces()) {
            switch (piece.getType()) {
                case PAWN: pawns++; break;
                case ROOK: rooks++; break;
                case KNIGHT: knights++; break;
                case BISHOP: bishops++; break;
                case QUEEN: queens++; break;
                case KING: kings++; break;
            }
        }

        // Check the counts of each piece type
        assertEquals(16, pawns, "There should be 16 pawns (8 for each side).");
        assertEquals(4, rooks, "There should be 4 rooks (2 for each side).");
        assertEquals(4, knights, "There should be 4 knights (2 for each side).");
        assertEquals(4, bishops, "There should be 4 bishops (2 for each side).");
        assertEquals(2, queens, "There should be 2 queens (1 for each side).");
        assertEquals(2, kings, "There should be 2 kings (1 for each side).");
    }
    
    @Test
    public void testInitialPiecePositions() {
        // Verify that rooks are in their correct starting positions
        assertPosition(PieceType.ROOK, PieceColor.WHITE, 1, 1);
        assertPosition(PieceType.ROOK, PieceColor.WHITE, 8, 1);
        assertPosition(PieceType.ROOK, PieceColor.BLACK, 1, 8);
        assertPosition(PieceType.ROOK, PieceColor.BLACK, 8, 8);
        
        // Verify that knights are in their correct starting positions
        assertPosition(PieceType.KNIGHT, PieceColor.WHITE, 2, 1);
        assertPosition(PieceType.KNIGHT, PieceColor.WHITE, 7, 1);
        assertPosition(PieceType.KNIGHT, PieceColor.BLACK, 2, 8);
        assertPosition(PieceType.KNIGHT, PieceColor.BLACK, 7, 8);
        
        // Verify that bishops are in their correct starting positions
        assertPosition(PieceType.BISHOP, PieceColor.WHITE, 3, 1);
        assertPosition(PieceType.BISHOP, PieceColor.WHITE, 6, 1);
        assertPosition(PieceType.BISHOP, PieceColor.BLACK, 3, 8);
        assertPosition(PieceType.BISHOP, PieceColor.BLACK, 6, 8);
        
        // Verify the queen and king positions
        assertPosition(PieceType.QUEEN, PieceColor.WHITE, 4, 1);
        assertPosition(PieceType.KING, PieceColor.WHITE, 5, 1);
        assertPosition(PieceType.QUEEN, PieceColor.BLACK, 4, 8);
        assertPosition(PieceType.KING, PieceColor.BLACK, 5, 8);
    }
    
    @Test
    public void testInitialPawnPositions() {
        // Verify that all pawns are in their correct positions for white
        for (int i = 1; i <= 8; i++) {
            assertPosition(PieceType.PAWN, PieceColor.WHITE, i, 2);
            assertPosition(PieceType.PAWN, PieceColor.BLACK, i, 7);
        }
    }
    
    // Helper method to assert the position of a specific piece type and color
    private void assertPosition(PieceType type, PieceColor color, int x, int y) {
        Piece foundPiece = match.getBoard().getPieces().stream()
            .filter(piece -> piece.getType() == type && piece.getColor() == color && piece.getXPosition() == x && piece.getYPosition() == y)
            .findFirst()
            .orElse(null);
        
        assertNotNull(foundPiece, type + " of color " + color + " should be at position (" + x + ", " + y + ").");
    }
}
