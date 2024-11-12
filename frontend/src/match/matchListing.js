import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import tokenService from '../services/token.service';

const ChessMatchList = () => {
    const token  = tokenService.getLocalAccessToken();
    const [matches, setMatches] = useState([]); // Estado para almacenar las partidas
    const [loading, setLoading] = useState(true); // Estado para manejar el estado de carga
    const [error, setError] = useState(null); // Estado para manejar posibles errores
    const navigate = useNavigate();
    useEffect(() => {        
        // Realizar la solicitud GET al cargar el componente
        fetch('/api/v1/matches?ownerId=0', {headers: { "Authorization": `Bearer  ${token}`}})
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al obtener las partidas');
                }
                return response.json();
            })
            .then(data => {
                setMatches(data); // Guardar las partidas en el estado
                setLoading(false); // Cambiar el estado de carga
            })
            .catch(error => {
                setError(error.message);
                setLoading(false);
            });
    }, []);

    const transformMatchIntoExercise = (matchId) => {
        fetch('/api/v1/matches?matchToUseAsExercise='+matchId, {method:"POST",headers: { "Authorization": `Bearer  ${token}`}})
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al clonar partida cond id:'+matchId+' para usarla como ejercicion');
                }
                return response.json();
            }).then(data => {
                console.log('Partida clonada con id:'+data.id);
                navigate(`/matches/${data.id}`);
            }).catch(error => {
                setError(error.message);                
            });
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