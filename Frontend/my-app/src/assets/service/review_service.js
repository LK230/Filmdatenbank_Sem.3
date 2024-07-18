import { deleteReviewEndpoint, getCreateReviewEndpoint } from "./api_endpoints";

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

  async deleteReview(reviewId) {
    try {
      console.log("reviewId", reviewId);
      const review = await deleteReviewEndpoint({ reviewId });
      return review;
    } catch (error) {
      console.error(error);
    }
  }
}
