import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from "react-router-dom";
import * as matchesApi from '../services/matches';
import './Game.css';
import tablero from '../static/images/tablero.png';
import knightBlack from '../static/images/HORSE-BLACK.png';
import knightWhite from '../static/images/HORSE-WHITE.png';
import kingBlack from '../static/images/KING-BLACK.png';
import kingWhite from '../static/images/KING-WHITE.png';
import bishopBlack from '../static/images/BISHOP-BLACK.png';
import bishopWhite from '../static/images/BISHOP-WHITE.png';
import pawnBlack from '../static/images/PAWN-BLACK.png';
import pawnWhite from '../static/images/PAWN-WHITE.png';
import rookWhite from '../static/images/TOWER-WHITE.png';
import rookBlack from '../static/images/TOWER-BLACK.png';
import queenWhite from '../static/images/QUEEN-WHITE.png';
import queenBlack from '../static/images/QUEEN-BLACK.png';

function Match() {

    const [matchName , setMatchName] = useState("");

    const [pieces, setPieces] = useState([]);

    const [color, setColor] = useState();

    const [turn, setTurn] = useState();

    const [jaque, setJaque] = useState();

    const [time, setTime] = useState();

    const [timeOpponent, setTimeOpponent] = useState();

    const [myTurn, setMyTurn] = useState(false);

    const [finPartida, setFinPartida] = useState(false);

    const [inicializado, setInicializado] = useState("false");
    

    // :match is either a match id or "new" (see the routes in App.jsx)
    const { match: matchId } = useParams();
    const navigate = useNavigate();
    


    const DrawBoard = () => {

        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        var image;
        // Pintamos el fondo del tablero:
        image = document.getElementById("source");
        ctx.drawImage(image, 0, 0, 800, 800);

        // Pintamos las piezas:
        pieces.map(piece => {
            var pieza = document.getElementById(piece.type + "-" + piece.color);            
            if (pieza) {
                pieza.style.display = 'block';
                if (color === "BLACK") {
                    ctx.drawImage(pieza, 700 - ((piece.xposition - 1) * 100), 700 - ((piece.yposition - 1) * 100), 100, 100);
                } else {
                    ctx.drawImage(pieza, (piece.xposition - 1) * 100, (piece.yposition - 1) * 100, 100, 100);
                }
                pieza.style.display = 'none';
            } else {
                console.log("No se ha encontrado la pieza");
            }
        })
    }

    const partida = async () => {
        try {
            let match;
            if (matchId === "new") {
                // POST /api/v1/matches creates (and initializes) a new match for the current user
                match = await matchesApi.createMatch();
                // Show the real id in the URL, so reloading the page does not create another match
                // (only if the backend has saved the match and returned its id)
                if (match.id != null)
                    navigate(`/matches/${match.id}`, { replace: true });
            } else {
                match = await matchesApi.getMatch(matchId);
            }
            setMatchName(match.name);
            setPieces(match.board?.pieces ?? []);
            setInicializado("true");
        } catch (error) {
            console.error("Error loading match:", error);
        }
    }

    useEffect(() => {
        partida();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [matchId])

    const handleSubmit = (e) => { e.preventDefault(); }

    const handleChange = async (e) => {  }

    const handleButton = () => { }

    const refresco = () => { }

    const mover = () => { }

    const oMousePos = (evt) => { }

    async function InicioTurno() { }

    const finTiempo = () => { }


    return (
        <React.Fragment>
            {inicializado === "false" &&
                <div>
                    <h1 style={{ textAlign: 'center' }}>Cargando tablero... </h1>
                </div>
            }
            <div className="container">                
                <hr></hr>
                <h1 style={{ marginTop: "200px" }}>Match: {matchName}</h1>
                <img id="source" src={tablero} alt="alt" style={{ display: 'none' }} />
                <img id="KNIGHT-BLACK" src={knightBlack} alt="alt" style={{ display: 'none' }} />
                <img id="KNIGHT-WHITE" src={knightWhite} alt="alt" style={{ display: 'none' }} />
                <img id="KING-BLACK" src={kingBlack} alt="alt" style={{ display: 'none' }} />
                <img id="KING-WHITE" src={kingWhite} alt="alt" style={{ display: 'none' }} />
                <img id="BISHOP-BLACK" src={bishopBlack} alt="alt" style={{ display: 'none' }} />
                <img id="BISHOP-WHITE" src={bishopWhite} alt="alt" style={{ display: 'none' }} />
                <img id="PAWN-BLACK" src={pawnBlack} alt="alt" style={{ display: 'none' }} />
                <img id="PAWN-WHITE" src={pawnWhite} alt="alt" style={{ display: 'none' }} />
                <img id="ROOK-WHITE" src={rookWhite} alt="alt" style={{ display: 'none' }} />
                <img id="ROOK-BLACK" src={rookBlack} alt="alt" style={{ display: 'none' }} />
                <img id="QUEEN-WHITE" src={queenWhite} alt="alt" style={{ display: 'none' }} />
                <img id="QUEEN-BLACK" src={queenBlack} alt="alt" style={{ display: 'none' }} />
                
                <h1 id="msg"></h1>
                
                <canvas id="canvas" width={800} height={800} onClick={oMousePos} style={{ marginTop: "800px" }}> </canvas>

                {inicializado === "true" &&
                    <div>
                        <p>
                            
                        </p>
                        <p>
                            <DrawBoard />
                        </p>
                    </div>
                }
            </div>
        </React.Fragment>
    )


}

export default Match;