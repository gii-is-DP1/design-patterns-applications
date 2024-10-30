import React, { useEffect, useState } from 'react';
import tokenService from '../services/token.service';
import { useLocation } from "react-router-dom"
import './Game.css';

const apiUrl = "/api/v1";

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
    

    const location = useLocation();
    


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

    const partida = () => {
        const token  = tokenService.getLocalAccessToken();

        // Check if the URL is `/matches/new`
        if (location.pathname === "/matches/new") {
            // If it's `/matches/new`, make a POST request to create a new match
            fetch("/api/v1/matches", {
                method: "POST", headers: {"Content-Type": "application/json", "Authorization": `Bearer ${token}`}
            })
            .then(response => response.json())
            .then(json => {
                // Handle the response, setting pieces or match state as needed
                setMatchName(json.name);
                setPieces(json.board.pieces);
                setInicializado("true");
            })
            .catch(error => console.error("Error creating match:", error));
        } else {

            let url = apiUrl + location.pathname;
            fetch(url, {headers: { "Authorization": `Bearer  ${token}`}})
                .then(response => response.json())
                .then(json => {
                    setMatchName(json.name);
                    setPieces(json.board.pieces);
                    setInicializado("true");
                })
                .catch(error => console.error("Error fetching match:", error));
            }
    }

    useEffect(() => {
        partida();
    }, [])

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