import React from 'react'

export default function RatingView({ user, comment, rating}) {
    return (
        <div className='RatingView'>
            <div className='user-container'>
                <p>{user}</p>
            </div>
            <div className='comment-container'>
                <p>{comment}</p>
            </div>
            <div className='rating-container'>
                
            </div>
    </div>
  )
}

