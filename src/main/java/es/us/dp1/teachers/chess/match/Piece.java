package es.us.dp1.teachers.chess.match;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.teachers.chess.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
public class Piece extends BaseEntity{
    
    @Min(1)
    @Max(8)
    Integer xPosition;
    
    @Min(1)
    @Max(8)
    Integer yPosition;

    @ManyToOne
    @JsonIgnore
    ChessBoard board;

    @Enumerated(EnumType.STRING)
    PieceType type;
    
    @Enumerated(EnumType.STRING)
    PieceColor color;
}
