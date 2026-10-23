import api from "./api";

/**
 * Chess matches API.
 * @param {number} [ownerId] - 0 returns every match (famous matches and openings);
 *   undefined returns the matches of the current user.
 */
export const getMatches = async (ownerId) => {
    const res = await api.get("/matches", { params: { ownerId } });
    return res.data;
};

export const getMatch = async (id) => {
    const res = await api.get(`/matches/${id}`);
    return res.data;
};

/** Creates a new match for the current user, initialized by the backend. */
export const createMatch = async () => {
    const res = await api.post("/matches");
    return res.data;
};

/** Creates a copy (exercise) of the given match for the current user. */
export const useMatchAsExercise = async (matchId) => {
    const res = await api.post("/matches", null, { params: { matchToUseAsExercise: matchId } });
    return res.data;
};
