## API-Endpoints

### <u> Movies </u>

| Method | URL                           | Description                                          | Body/Parameters - Example      |
|--------|-------------------------------|------------------------------------------------------|--------------------------------|
| `GET`  | `/movies`                     | Fetches a list of all movies.                        | `/movies`                      |
| `GET`  | `/movies/{imdbId}`            | Fetches details of a single movie using its IMDB ID. | `/movies/tt0848228`            |
| `GET`  | `/movies/title/{title}`       | Searches for movies by a part of the title           | `/movies/title/avatar`         |
| `GET`  | `/movies/genre`               | Fetches movies grouped by genre.                     | `/movies/genre`                |
| `GET`  | `/movies/genre/{genre}`       | Finds movies by genre.                               | `/movies/genre/horror`         |
| `GET`  | `/movies/newest`              | Fetches all the newest movies.                       | `/movies/newest`               |
| `GET`  | `/movies/director/{director}` | Finds movies by director.                            | `/movies/director/Joss Whedon` |
| `GET`  | `/movies/bestrated`           | Fetches all movies sorted by their rating.           | `/movies/bestrated`            |

### <u> Reviews </u>

| Method   | URL                                                                                     | Description                       | Body/Parameters - Example                                                                                  |
|----------|-----------------------------------------------------------------------------------------|-----------------------------------|------------------------------------------------------------------------------------------------------------|
| `POST`   | `/reviews/create?reviewBody={body}&rating={rating}&imdbId={imdbId}&username={username}` | Creates a new review for a movie. | `/reviews/create?reviewBody=Very%20good%20movie&rating=5&imdbId=tt0499549&username=user`                   |
| `DELETE` | `/reviews/remove?reviewId={ObjectId}`                                                   | Deletes a review by its ID.       | `/reviews/remove?reviewId={ObjectId}`                                                                      |
| `PUT`    | `/reviews/update?reviewId={ObjectId}&body={body}&rating={rating}`                       | Updates a a review.               | `/reviews/update?reviewId=66759045ee799c4abf888e62&body=This%20is%20the%20best%20movie%20ever%21&rating=5` |


### <u> Users </u>

| Method   | URL                                                                                           | Description                              | Body/Parameters - Example                                                                                  |
|----------|-----------------------------------------------------------------------------------------------|------------------------------------------|------------------------------------------------------------------------------------------------------------|
| `POST`   | `/users/create`                                                                               | Creates a new user.                      | `{ "name": "John", "surname": "Doe", "username": "JohnDoe", "password": "1234", "email": "john@doe.net" }` |
| `POST`   | `/users/login?email={email}&password={password}`                                              | Logs in a user.                          | `/users/login?email=user%40gmail.com&password=12345678`                                                    |
| `GET`    | `/users/{username}?email={email}&password={password}`                                         | Gets a user profile.                     | `/users/{username}?username=user&email=user%40gmail.com&password=12345678`                                 |
| `POST`   | `/users/favorites/add?username={username}&imdbId={imdbId}`                                    | Adds a movie to a user´s favorites.      | `/users/favorites/add?username=user&imdbId=tt0499549`                                                      |
| `DELETE` | `/users/favorites/remove?username={username}&imdbId={imdbId}`                                 | Removes a movie from a user´s favorites. | `/users/favorites/remove?username=user&imdbId=tt0499549`                                                   |
| `DELETE` | `/users/delete?email={email}&password={password}`                                             | Deletes a user.                          | `/users/delete?email=user%40gmail.com&password=12345678`                                                   |
| `PATCH`  | `/users/update/password?email={email}&password={current password}&newPassword={new Password}` | Update a user´s password.                | `/users/update/password?email=user%40mail.com&password=123456789&newPassword=12345678`                     |
| `PATCH`  | `/users/update/email?email={current email}&password={password}&newEmail={new email}`          | Update a user´s email.                   | `/users/update/email?email=user%40mail.com&password=12345678&newEmail=user%40gmail.com`                    |


