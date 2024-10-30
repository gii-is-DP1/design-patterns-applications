import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import tokenService from '../services/token.service';

const ChessMatchList = () => {
    const [matches, setMatches] = useState([]); // Estado para almacenar las partidas
    const [loading, setLoading] = useState(true); // Estado para manejar el estado de carga
    const [error, setError] = useState(null); // Estado para manejar posibles errores

    useEffect(() => {
        const token  = tokenService.getLocalAccessToken();
        // Realizar la solicitud GET al cargar el componente
        fetch('/api/v1/matches', {headers: { "Authorization": `Bearer  ${token}`}})
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
                        <Link to={`/matches/${match.id}`}>{match.name}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ChessMatchList;