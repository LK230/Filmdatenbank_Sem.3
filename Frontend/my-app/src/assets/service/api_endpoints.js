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
    console.error("Error fetching User:", error);
    throw error.response.data;
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
    throw error.response.data;
  }
}

export async function getCreateUserEndpoint(user) {
  try {
    const response = await api.post("/users/create", user);
    return response;
  } catch (error) {
    console.error("Error fetching User:", error);
    throw error.response.data;
  }
}

export async function getUserMeEndpoint(email, password) {
  try {
    const response = await api.get(
      `/users/userme?email=${encodeURIComponent(email)}&password=${password}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movies:", error);
    throw error.response.data;
  }
}

export async function deleteUserProfileEndpoint(email, password) {
  try {
    const response = await api.delete(
      `/users/delete?email=${encodeURIComponent(email)}&password=${password}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movies:", error);
    throw error.response.data;
  }
}

export async function getMoviesEndpoint() {
  try {
    const response = await api.get("/movies");
    return response.data;
  } catch (error) {
    console.error("Error fetching movies:", error);
    throw error.response.data;
  }
}

export async function getBestRatedMoviesEndpoint() {
  try {
    const response = await api.get("/movies/bestrated");
    return response.data;
  } catch (error) {
    console.error("Error fetching movies:", error);
    throw error.response.data;
  }
}

export async function getMovieEndpoint(imdbId) {
  try {
    const response = await api.get("/movies/" + imdbId);
    return response.data;
  } catch (error) {
    console.error("Error fetching movie:", error);
    throw error.response.data;
  }
}

export async function getCreateReviewEndpoint(
  reviewBody,
  rating,
  imdbId,
  email
) {
  try {
    const response = await api.post(
      `/reviews/create?reviewBody=${reviewBody}&rating=${rating}&imdbId=${imdbId}&email=${encodeURIComponent(
        email
      )}`
    );

    return response.data;
  } catch (error) {
    console.error("Error:", error);
    throw error.response.data;
  }
}

export async function getAddFavoritesEndpoint(email, imdbId) {
  try {
    const response = await api.post(
      `/users/favorites/add?email=${encodeURIComponent(email)}&imdbId=${imdbId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error adding to favorites:", error);
    throw error.response.data;
  }
}

export async function getGenreEndpoint() {
  try {
    const response = await api.get("/movies/genre");
    return response.data;
  } catch (error) {
    console.error("Error fetching genre:", error);
    throw error.response.data;
  }
}

export async function getDeleteFromFavoritesEndpoint(email, imdbId) {
  try {
    const response = await api.delete(
      `/users/favorites/remove?email=${encodeURIComponent(
        email
      )}&imdbId=${imdbId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error deleting from favorites:", error);
    throw error.response.data;
  }
}

export async function getGenreMoviesEndpoint(genre) {
  try {
    const response = await api.get("/movies/genre/" + genre);
    return response.data;
  } catch (error) {
    console.error("Error fetching movie:", error);
    throw error.response.data;
  }
}

export async function patchUpdatePasswordEndpoint(
  email,
  password,
  newPassword
) {
  try {
    const response = await api.patch(
      `/users/update/password?email=${email}&password=${password}&newPassword=${newPassword}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie:", error);
    throw error.response.data;
  }
}

export async function patchUpdateEmailEndpoint(email, password, newEmail) {
  try {
    const response = await api.patch(
      `/users/update/email?email=${email}&password=${password}&newEmail=${newEmail}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie:", error);
    throw error.response.data;
  }
}

export async function deleteReviewEndpoint(username, imdbId) {
  try {
    const response = await api.delete(
      `/reviews/remove?username=${username}&imdbId=${imdbId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching movie:", error);
    throw error.response.data;
  }
}
