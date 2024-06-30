import {
  getCreateReviewEndpoint,
  getCreateUserEndpoint,
} from "./api_endpoints";

export class ReviewService {
  async getCreateReview(reviewBody, rating, imdbId, email) {
    try {
      const review = await getCreateReviewEndpoint(
        reviewBody,
        rating,
        imdbId,
        email
      );
      return review;
    } catch (error) {
      console.error(error);
    }
  }
}
