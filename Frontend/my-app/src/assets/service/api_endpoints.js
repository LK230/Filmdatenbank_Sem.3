import api from "../api/axiosConfig";

export async function getMoviesEndpoint() {
  try {
    const response = await api.get("/movies");
    return response.data;
  } catch (error) {
    console.error("Error fetching movies:", error);
    throw error;
  }
}

export async function getMovieEndpoint(imdbId) {
  try {
    const response = await api.get("/movies/" + imdbId);
    return response.data;
  } catch (error) {
    console.error("Error fetching movie:", error);
    throw error;
  }
}
