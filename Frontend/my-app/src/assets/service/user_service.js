import {
  getAddFavoritesEndpoint,
  getAddToFavorites,
  getCreateUserEndpoint,
  getDeleteFromFavorites,
  getDeleteFromFavoritesEndpoint,
  getUserEndpoint,
  getUserMeEndpoint,
  getUsersEndpoint,
  patchUpdateEmailEndpoint,
  patchUpdatePasswordEndpoint,
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

  async getUserMe(email, password) {
    try {
      const user = await getUserMeEndpoint(email, password);
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

  async userAddToFavorite(email, imdbId) {
    try {
      const addFav = await getAddFavoritesEndpoint(email, imdbId);
      return addFav;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async userDeleteFromFavorite(email, imdbId) {
    try {
      const deleteFav = await getDeleteFromFavoritesEndpoint(email, imdbId);
      return deleteFav;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async userPatchUpdatePassword(email, password, newPassword) {
    try {
      const updateUserPassword = await patchUpdatePasswordEndpoint(email, password, newPassword);
      return updateUserPassword;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async userPatchUpdateEmail(email, password, newEmail) {
    try {
      const updateUserEmail = await patchUpdateEmailEndpoint(email, password, newEmail);
      return updateUserEmail;
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
}
