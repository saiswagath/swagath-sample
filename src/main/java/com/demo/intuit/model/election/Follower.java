package com.demo.intuit.model.election;

public class Follower {
    private long followerRecordId;
    private long citizenId;
    private long followerId;

    public Follower(){

    }

    public Follower(long followerRecordId, long citizenId, long followerId) {
        this.followerRecordId = followerRecordId;
        this.citizenId = citizenId;
        this.followerId = followerId;
    }

    public long getFollowerRecordId() {
        return followerRecordId;
    }

    public void setFollowerRecordId(long followerRecordId) {
        this.followerRecordId = followerRecordId;
    }

    public long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(long citizenId) {
        this.citizenId = citizenId;
    }

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }
}
