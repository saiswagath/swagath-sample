package com.demo.intuit.model.election;

public class Rating {
    private long ratingId;
    private long ideaId;
    private long ratedByCitizenId;
    private int rating;

    public Rating(long ideaId, long ratedByCitizenId, int rating) {
        this.ideaId = ideaId;
        this.ratedByCitizenId = ratedByCitizenId;
        this.rating = rating;
    }

    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    public long getRatedByCitizenId() {
        return ratedByCitizenId;
    }

    public void setRatedByCitizenId(long ratedByCitizenId) {
        this.ratedByCitizenId = ratedByCitizenId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
