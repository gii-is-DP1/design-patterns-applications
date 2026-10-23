import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import * as matchesApi from '../services/matches';

const ChessMatchList = () => {
    const [matches, setMatches] = useState([]); // Estado para almacenar las partidas
    const [loading, setLoading] = useState(true); // Estado para manejar el estado de carga
    const [error, setError] = useState(null); // Estado para manejar posibles errores
    const navigate = useNavigate();
    useEffect(() => {
        // ownerId=0: every stored match (famous matches and openings)
        matchesApi.getMatches(0)
            .then(data => setMatches(data))
            .catch(() => setError('Error al obtener las partidas'))
            .finally(() => setLoading(false));
    }, []);

    const transformMatchIntoExercise = (matchId) => {
        matchesApi.useMatchAsExercise(matchId)
            .then(data => {
                console.log('Partida clonada con id:' + data.id);
                navigate(`/matches/${data.id}`);
            })
            .catch(() => setError('Error al clonar la partida con id: ' + matchId + ' para usarla como ejercicio'));
    };

    if (loading) {
        return <p>Loading Matches...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    return (
        <div>
            <h2>List of Matches</h2>
            <ul>
                {matches.map((match) => (
                    <li key={match.id}>
                        <button onClick={() => transformMatchIntoExercise(match.id)} className="btn btn-success">Use as exercise</button>  <Link to={`/matches/${match.id}`}>{match.name}</Link>                        
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ChessMatchList;