package com.demo.intuit.model.election;

import com.demo.intuit.model.election.Idea;

import java.util.List;

public class Manifesto {
    private long manifestoId;
    private long contenderId;
    private List<Idea> ideaList;

    public Manifesto() {

    }

    public Manifesto(long manifestoId, long contenderId) {
        this.manifestoId = manifestoId;
        this.contenderId = contenderId;
    }

    public Manifesto(long contenderId, List<Idea> ideaList) {
        this.contenderId = contenderId;
        this.ideaList = ideaList;
    }

    public long getManifestoId() {
        return manifestoId;
    }

    public void setManifestoId(long manifestoId) {
        this.manifestoId = manifestoId;
    }

    public long getContenderId() {
        return contenderId;
    }

    public void setContenderId(long contenderId) {
        this.contenderId = contenderId;
    }

    public List<Idea> getIdeaList() {
        return ideaList;
    }

    public void setIdeaList(List<Idea> ideaList) {
        this.ideaList = ideaList;
    }
}
