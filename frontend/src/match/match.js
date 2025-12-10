import React, { useEffect, useState } from 'react';
import tokenService from '../services/token.service';
import getErrorModal from '../util/getErrorModal'
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router";
import useIntervalFetchState from '../util/useIntervalFetchState';
import './Game.css';

const apiUrl = "/api/v1";

function formatPiece(move) {
    if (!move || !move.piece) return 'Piece';
    return `${move.piece.color} ${move.piece.type}`;
}

const moveRenderers = [
    {
        canRender: (move) => move.moveType === "CASTLING",
        render: (move) => move.description || `Castling: ${formatPiece(move)} from (${move.fromX},${move.fromY}) to (${move.toX},${move.toY})`
    },
    {
        canRender: (move) => move.moveType === "PROMOTION",
        render: (move) => move.description || `Promotion: ${formatPiece(move)} to ${move.promotionType || 'QUEEN'} at (${move.toX},${move.toY})`
    },
    {
        canRender: (move) => move.moveType === "CAPTURE",
        render: (move) => move.description || `${formatPiece(move)} captures on (${move.toX},${move.toY})`
    },
    {
        canRender: () => true,
        render: (move) => move.description || `${formatPiece(move)} from (${move.fromX},${move.fromY}) to (${move.toX},${move.toY})`
    }
];

const compositeRenderMove = (move) => {
    const renderer = moveRenderers.find(r => r.canRender(move));
    return renderer ? renderer.render(move) : '';
}

function Match() {

    const [matchName , setMatchName] = useState("");

    const [pieces, setPieces] = useState([]);
    const [movements, setMovements] = useState([]);

    const [color, setColor] = useState();

    const [turn, setTurn] = useState();

    const [jaque, setJaque] = useState();

    const [time, setTime] = useState();

    const [timeOpponent, setTimeOpponent] = useState();

    const [myTurn, setMyTurn] = useState(false);

    const [finPartida, setFinPartida] = useState(false);

    const [inicializado, setInicializado] = useState("false");

    const [fromX, setFromX] = useState();
    const [fromY, setFromY] = useState();
    const [toX, setToX] = useState();
    const [toY, setToY] = useState();
    const [firstClick, setFirstClick] = useState(false);
    
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);

    const location = useLocation();
    const navigate = useNavigate();
    const token  = tokenService.getLocalAccessToken();

    const matchUrl = location.pathname === "/matches/new" ? null : apiUrl + location.pathname;
    const [fetchedMatch, setFetchedMatch] = useIntervalFetchState(null, matchUrl, token, setMessage, setVisible,null,2000);


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

    useEffect(() => {
        if (location.pathname === "/matches/new") {
            fetch("/api/v1/matches", {
                method: "POST", headers: {"Content-Type": "application/json", "Authorization": `Bearer ${token}`}
            })
            .then(response => response.json())
            .then(json => {
                setFetchedMatch(json);
                navigate("/matches/"+json.id,{ replace: true} );
            })
            .catch(error => console.error("Error creating match:", error));
        }
    }, [location.pathname, navigate, setFetchedMatch, token]);

    useEffect(() => {
        if (fetchedMatch) {
            setMatchName(fetchedMatch.name);
            setPieces(fetchedMatch.board ? fetchedMatch.board.pieces : []);
            setMovements(fetchedMatch.commandsHistory || []);
            setInicializado("true");
        }
    }, [fetchedMatch]);

    const handleSubmit = (e) => { e.preventDefault(); }

    const handleChange = async (e) => {  }

    const handleButton = () => { }

    const refresco = () => {

    }

    const executeMove = (x1,y1,x2,y2) => {
        const token  = tokenService.getLocalAccessToken();
        let url = apiUrl + location.pathname + "/move?fromX=" + x1 + "&fromY=" + y1 + "&toX=" + x2 + "&toY=" + y2;
        fetch(url, {method: "PUT", headers: { "Authorization": `Bearer  ${token}`}})
            .then(async response => {
                const json = await response.json();
                if (!response.ok || json.message) {
                    setMessage(json.message || "Invalid move");
                    setVisible(true);
                    return;
                }
                setPieces(json.board.pieces);
                setMovements(json.commandsHistory);
            })
            .catch(error => {
                console.error("Error fetching match:", error);
                setMessage("Unable to process move");
                setVisible(true);
            });
    }

    const mover = () => {
        executeMove(fromX,fromY,toX,toY);;
    }


    const oMousePos = (evt) => { 

        var canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        var x = evt.clientX - rect.left;
        var y = evt.clientY - rect.top;
        var x1 = Math.floor(x / 100) + 1;
        var y1 = Math.floor(y / 100) + 1;
        console.log("x: " + x1 + " y: " + y1);
        if (!firstClick) {
            setFromX(x1);
            setFromY(y1);
            setFirstClick(true);
        } else {
            setToX(x1);
            setToY(y1);
            setFirstClick(false);
            executeMove(fromX,fromY,x1,y1);
        }
    }

    async function InicioTurno() { }

    const finTiempo = () => { }

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <React.Fragment>
            {inicializado === "false" &&
                <div>
                    <h1 style={{ textAlign: 'center' }}>Cargando tablero... </h1>
                </div>
            }
            <div className="container">
                {modal}               
                <hr></hr>
                <img id="source" src={require('../static/images/tablero.png')} alt="alt" style={{ display: 'none' }} />
                <img id="KNIGHT-BLACK" src={require('../static/images/HORSE-BLACK.png')} alt="alt" style={{ display: 'none' }} />
                <img id="KNIGHT-WHITE" src={require('../static/images/HORSE-WHITE.png')} alt="alt" style={{ display: 'none' }} />
                <img id="KING-BLACK" src={require('../static/images/KING-BLACK.png')} alt="alt" style={{ display: 'none' }} />
                <img id="KING-WHITE" src={require('../static/images/KING-WHITE.png')} alt="alt" style={{ display: 'none' }} />
                <img id="BISHOP-BLACK" src={require('../static/images/BISHOP-BLACK.png')} alt="alt" style={{ display: 'none' }} />
                <img id="BISHOP-WHITE" src={require('../static/images/BISHOP-WHITE.png')} alt="alt" style={{ display: 'none' }} />
                <img id="PAWN-BLACK" src={require('../static/images/PAWN-BLACK.png')} alt="alt" style={{ display: 'none' }} />
                <img id="PAWN-WHITE" src={require('../static/images/PAWN-WHITE.png')} alt="alt" style={{ display: 'none' }} />
                <img id="ROOK-WHITE" src={require('../static/images/TOWER-WHITE.png')} alt="alt" style={{ display: 'none' }} />
                <img id="ROOK-BLACK" src={require('../static/images/TOWER-BLACK.png')} alt="alt" style={{ display: 'none' }} />
                <img id="QUEEN-WHITE" src={require('../static/images/QUEEN-WHITE.png')} alt="alt" style={{ display: 'none' }} />
                <img id="QUEEN-BLACK" src={require('../static/images/QUEEN-BLACK.png')} alt="alt" style={{ display: 'none' }} />
                
                <h1 id="msg"></h1>

                <div style={{ marginTop: "200px" }}>
                    <label>
                        From column:
                        <input type="number" name="fromX" min={1} max={8} value={fromX} onChange={(e) => {setFromX(e.target.value)}} />
                    </label>&nbsp;&nbsp;
                    <label>
                        From row:
                        <input type="number" name="fromY" min={1} max={8} value={fromY} onChange={(e) => {setFromY(e.target.value)}} />
                    </label><br></br>
                    <label>
                        To column:
                        <input type="number" name="toX" min={1} max={8} value={toX} onChange={(e) => {setToX(e.target.value)}} />
                    </label>&nbsp;&nbsp;
                    <label>
                        To row:
                        <input type="number" name="toY" min={1} max={8} value={toY} onChange={(e) => {setToY(e.target.value)}} />
                    </label>
                    <div>
                    <button onClick={mover}>Move!</button>    
                </div>
                 
                </div>
                    
                <canvas id="canvas" width={800} height={800} onClick={oMousePos} style={{ marginTop: "1100px" }}> </canvas>

                {inicializado === "true" &&
                    <div>
                        <p>
                            <DrawBoard />
                        </p>
                        <div style={{ marginTop: "500px" }}>
                            <h1>Match: {matchName}</h1>
                            <h2>Movements history</h2>
                            {movements!=null &&
                                <ul>
                                    {movements.map((movement, index) => (
                                        <li key={index}>{compositeRenderMove(movement)}</li>
                                    ))}
                               </ul>
                            }
                        </div>
                    </div>
                    
                }
            </div>
        </React.Fragment>
    )


}

export default Match;
