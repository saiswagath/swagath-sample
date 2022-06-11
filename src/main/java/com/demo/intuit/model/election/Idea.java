package com.demo.intuit.model.election;

import java.util.List;

public class Idea {
    private long ideaId;
    private long manifestoId;
    private String ideaDescription;
    private List<Rating> ratingsList;

    public Idea(){

    }

    public Idea(long ideaId, long manifestoId, String ideaDescription, List<Rating> ratingsList) {
        this.ideaId = ideaId;
        this.manifestoId = manifestoId;
        this.ideaDescription = ideaDescription;
        this.ratingsList = ratingsList;
    }

    public Idea(long ideaId, long manifestoId, String ideaDescription) {
        this.ideaId = ideaId;
        this.manifestoId = manifestoId;
        this.ideaDescription = ideaDescription;
    }

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    public long getManifestoId() {
        return manifestoId;
    }

    public void setManifestoId(long manifestoId) {
        this.manifestoId = manifestoId;
    }

    public String getIdeaDescription() {
        return ideaDescription;
    }

    public void setIdeaDescription(String ideaDescription) {
        this.ideaDescription = ideaDescription;
    }

    public List<Rating> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<Rating> ratingsList) {
        this.ratingsList = ratingsList;
    }
}
