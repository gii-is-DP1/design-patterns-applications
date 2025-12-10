package es.us.dp1.teachers.chess.match;

import es.us.dp1.teachers.chess.exceptions.IllegalMoveException;

public enum PieceType {
    KING("King", 1000), // Usually the King is considered invaluable, often represented with a very high number.
    QUEEN("Queen", 9), 
    ROOK("Rook", 5), 
    BISHOP("Bishop", 3), 
    KNIGHT("Knight", 3), 
    PAWN("Pawn", 1);

    private final String name;
    private final int value; // Typically, pieces are given a value to indicate their relative strength in the game.

    PieceType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    /**
     * Validates that the movement from (fromX, fromY) to (toX, toY) follows the
     * pattern for this piece type. It does not check for collisions; callers are
     * expected to handle board occupancy concerns.
     * If the move is invalid, throws an IllegalMoveException.
     */
    public void validateMove(Piece piece, Piece destinationPiece, int fromX, int fromY, int toX, int toY) {
        int dx = toX - fromX;
        int dy = toY - fromY;

        if (dx == 0 && dy == 0) {
            throw new IllegalMoveException("The piece must move to a different square");
        }

        switch (this) {
            case KING:
                validateKingMove(dx, dy);
                break;
            case QUEEN:
                validateQueenMove(dx, dy);
                break;
            case ROOK:
                validateRookMove(dx, dy);
                break;
            case BISHOP:
                validateBishopMove(dx, dy);
                break;
            case KNIGHT:
                validateKnightMove(dx, dy);
                break;
            case PAWN:
                validatePawnMove(piece, destinationPiece, dx, dy, fromY);
                break;
            default:
                throw new IllegalMoveException("Unsupported piece type");
        }
    }

    private void validateKingMove(int dx, int dy) {
        if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
            return;
        }
        // Allow horizontal 2-square move for castling
        if (dy == 0 && Math.abs(dx) == 2) {
            return;
        }
        throw new IllegalMoveException("Invalid king move");
    }

    private void validateQueenMove(int dx, int dy) {
        if (dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy)) {
            return;
        }
        throw new IllegalMoveException("Invalid queen move");
    }

    private void validateRookMove(int dx, int dy) {
        if (dx == 0 || dy == 0) {
            return;
        }
        throw new IllegalMoveException("Invalid rook move");
    }

    private void validateBishopMove(int dx, int dy) {
        if (Math.abs(dx) == Math.abs(dy)) {
            return;
        }
        throw new IllegalMoveException("Invalid bishop move");
    }

    private void validateKnightMove(int dx, int dy) {
        int adx = Math.abs(dx);
        int ady = Math.abs(dy);
        if ((adx == 1 && ady == 2) || (adx == 2 && ady == 1)) {
            return;
        }
        throw new IllegalMoveException("Invalid knight move");
    }

    private void validatePawnMove(Piece piece, Piece destinationPiece, int dx, int dy, int fromY) {
        int direction = piece.getColor() == PieceColor.WHITE ? 1 : -1;
        // Straight forward one square must be empty
        if (dx == 0 && dy == direction && destinationPiece == null) {
            return;
        }
        // Initial two-square advance
        if (dx == 0 && dy == 2 * direction && (fromY == 2 || fromY == 7) && destinationPiece == null) {
            return;
        }
        // Diagonal captures require an opponent piece
        if (Math.abs(dx) == 1 && dy == direction && destinationPiece != null && destinationPiece.getColor() != piece.getColor()) {
            return;
        }
        throw new IllegalMoveException("Invalid pawn move");
    }

    @Override
    public String toString() {
        return name + " (value: " + value + ")";
    }
}
