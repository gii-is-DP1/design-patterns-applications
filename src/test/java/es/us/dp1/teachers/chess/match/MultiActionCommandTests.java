package es.us.dp1.teachers.chess.match;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import es.us.dp1.teachers.chess.user.User;

class MultiActionCommandTests {

    private static final AtomicInteger idSource = new AtomicInteger(1);

    @Test
    void castlingDecoratorMovesKingAndRook() {
        Piece king = createPiece(PieceType.KING, PieceColor.WHITE, 5, 1);
        Piece rook = createPiece(PieceType.ROOK, PieceColor.WHITE, 8, 1);
        ChessMatch match = createMatch(new WhiteMoveState(), List.of(king, rook));

        match.movePiece(match.getCreator(), 5, 1, 7, 1);

        assertThat(king.getXPosition()).isEqualTo(7);
        assertThat(rook.getXPosition()).isEqualTo(6);
        assertThat(match.getCommandsHistory()).hasSize(2);
        assertThat(match.getCommandsHistory().get(0)).isInstanceOf(MovePieceCommand.class);
        assertThat(match.getCommandsHistory().get(1)).isInstanceOf(MovePieceCommand.class);

        match.undoLastCommand();

        assertThat(king.getXPosition()).isEqualTo(5);
        assertThat(rook.getXPosition()).isEqualTo(8);
    }

    @Test
    void promotionDecoratorUpgradesPawnAndSupportsUndo() {
        Piece pawn = createPiece(PieceType.PAWN, PieceColor.WHITE, 1, 7);
        ChessMatch match = createMatch(new WhiteMoveState(), List.of(pawn));

        match.movePiece(match.getCreator(), 1, 7, 1, 8);

        assertThat(pawn.getYPosition()).isEqualTo(8);
        assertThat(pawn.getType()).isEqualTo(PieceType.QUEEN);
        assertThat(match.getCommandsHistory()).hasSize(1);
        assertThat(match.getCommandsHistory().get(0)).isInstanceOf(MovePieceCommand.class);

        match.undoLastCommand();

        assertThat(pawn.getYPosition()).isEqualTo(7);
        assertThat(pawn.getType()).isEqualTo(PieceType.PAWN);
    }

    private ChessMatch createMatch(ChessMatchState state, List<Piece> pieces) {
        ChessBoard board = new ChessBoard();
        board.setPieces(new ArrayList<>());
        pieces.forEach(board::addPiece);

        ChessMatch match = new ChessMatch();
        match.setBoard(board);
        match.setCommandsHistory(new ArrayList<>());
        match.setCreator(createUser(1));
        match.setOpponent(createUser(2));

        state.setMatch(match);
        match.setState(state);
        return match;
    }

    private Piece createPiece(PieceType type, PieceColor color, int x, int y) {
        Piece piece = new Piece();
        piece.setType(type);
        piece.setColor(color);
        piece.setXPosition(x);
        piece.setYPosition(y);
        piece.setId(idSource.getAndIncrement());
        return piece;
    }

    private User createUser(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
