import api from "../api/axiosConfig";

export async function getUserEndpoint(username, email, password) {
  try {
    const response = await api.get(
      `/users/${username}?username=${username}&email=${encodeURIComponent(
        email
      )}&password=${password}`
    );
    return response.data;
  } catch (error) {
    console.error("Error:", error);
    throw error;
  }
}

export async function getUsersEndpoint(email, password) {
  try {
    const response = await api.post(
      `/users/login?email=${encodeURIComponent(email)}&password=${password}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching User:", error);
    throw error;
  }
}

export async function getCreateUserEndpoint(user) {
  try {
    const response = await api.post("/users/create", user);
    return response;
  } catch (error) {
    console.error("Error creating User:", error);
    throw error;
  }
}

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
