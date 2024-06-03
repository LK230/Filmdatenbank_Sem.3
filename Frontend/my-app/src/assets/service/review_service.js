import { getCreateReviewEndpoint, getCreateUserEndpoint } from "./api_endpoints";

export class ReviewService {
    async getCreateReview(reviewBody, rating, imdbId, username) {
    try {
        const review = await getCreateReviewEndpoint (
            reviewBody,
            rating,
            imdbId,
            username
        );
        return review;
    } catch (error) {
        console.error(error);

    }
}
}