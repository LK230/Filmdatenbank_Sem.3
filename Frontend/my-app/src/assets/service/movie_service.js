import {
  getBestRatedMoviesEndpoint,
  getGenreEndpoint,
  getGenreMoviesEndpoint,
  getMovieEndpoint,
  getMoviesEndpoint,
} from "./api_endpoints";

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

  async getBestRatedMovie() {
    try {
      const movie = await getBestRatedMoviesEndpoint();
      return movie;
    } catch (error) {
      console.error(error);
    }
  }

  async getGenre() {
    try {
      const genre = await getGenreEndpoint();
      return genre;
    } catch (error) {
      console.error(error);
    }
  }

  async getGenreMovies(genre) {
    try {
      const genreMovies = await getGenreMoviesEndpoint(genre);
      return genreMovies;
    } catch (error) {
      console.error(error);
    }
  }
}
