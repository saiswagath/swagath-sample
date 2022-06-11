package com.demo.intuit.model.election;

import java.util.List;

public class Citizen {
    private long citizenId;
    private String citizenName;
    private boolean contender;
    private List<Manifesto> manifestoList;
    private List<Follower> followingContenders;
    private List<Follower> followedByCitizens;

    public Citizen() {
    }

    public Citizen(long citizenId, String citizenName, boolean contender) {
        this.citizenId = citizenId;
        this.citizenName = citizenName;
        this.contender = contender;
    }

    public Citizen(String citizenName, boolean contender) {
        this.citizenName = citizenName;
        this.contender = contender;
    }

    public List<Manifesto> getManifestoList() {
        return manifestoList;
    }

    public void setManifestoList(List<Manifesto> manifestoList) {
        this.manifestoList = manifestoList;
    }

    public List<Follower> getFollowingContenders() {
        return followingContenders;
    }

    public void setFollowingContenders(List<Follower> followingContenders) {
        this.followingContenders = followingContenders;
    }

    public List<Follower> getFollowedByCitizens() {
        return followedByCitizens;
    }

    public void setFollowedByCitizens(List<Follower> followedByCitizens) {
        this.followedByCitizens = followedByCitizens;
    }

    public long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(long citizenId) {
        this.citizenId = citizenId;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public boolean getContender() {
        return contender;
    }

    public void setContender(boolean contender) {
        this.contender = contender;
    }
}
