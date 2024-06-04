import React from 'react';
import RatingStars from './RatingStars'; 

export default function RatingView({ user, comment, rating }) {
    return (
        <div className='RatingView'>
            <div className='top-container'>
                <div className='user-container'>
                    <p>{user}</p>
                </div>
                <div className='rating-container'>
                    <RatingStars rating={rating} />
                </div>
            </div>
            <div className='comment-container'>
                <p>{comment}</p>
            </div>
        </div>
    )
}


