import { getMovieEndpoint, getMoviesEndpoint } from "./api_endpoints";

export class MovieService {
  async getMovies() {
    try {
      const movies = await getMoviesEndpoint();
      return movies;
    } catch (error) {
      console.error(error);
    }
  }

  async getMovie(imdbId) {
    try {
      const movie = await getMovieEndpoint(imdbId);
      return movie;
    } catch (error) {
      console.error(error);
    }
  }
}
