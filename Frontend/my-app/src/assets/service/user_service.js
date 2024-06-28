import {
  getAddFavoritesEndpoint,
  getAddToFavorites,
  getCreateUserEndpoint,
  getDeleteFromFavorites,
  getDeleteFromFavoritesEndpoint,
  getUserEndpoint,
  getUsersEndpoint,
} from "./api_endpoints";

export class UserService {
  async getUser(username, email, password) {
    try {
      const user = await getUserEndpoint(username, email, password);
      return user;
    } catch (error) {
      console.error(error);
    }
  }

  async getUsers(email, password) {
    try {
      const user = await getUsersEndpoint(email, password);
      return user;
    } catch (error) {
      console.error(error);
    }
  }

  async createUser(user) {
    try {
      const createdUser = await getCreateUserEndpoint(user);
      return createdUser.data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async userAddToFavorite(username, imdbId) {
    try {
      const createdUser = await getAddFavoritesEndpoint(username, imdbId);
      return createdUser; // removed .data
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
  
  async userDeleteFromFavorite(username, imdbId) {
    try {
      const createdUser = await getDeleteFromFavoritesEndpoint(username, imdbId);
      return createdUser; // removed .data
    } catch (error) {
      console.error(error);
      throw error;
    }
  }  

}
