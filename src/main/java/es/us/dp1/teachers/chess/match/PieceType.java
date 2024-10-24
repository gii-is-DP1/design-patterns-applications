package es.us.dp1.teachers.chess.match;

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

    @Override
    public String toString() {
        return name + " (value: " + value + ")";
    }
}
