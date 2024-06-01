export class AuthService {
  async login(email, password) {
    let loginResponse;
    await loginEndpoint(email, password)
      .then((response) => {
        loginResponse = response;
        if (response.status === 200) {
          // Set cookie:
          const access_token = response.data.access_token;
          this.setSessionCookie(access_token);
        }
      })
      .catch((err) => {
        console.error("Fetch-Error:", err);
      });
    return loginResponse;
  }
}
