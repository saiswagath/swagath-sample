package com.demo.intuit.model.election;

import java.util.List;

public class ManifestoObjectResponse {

    long manifestoId;
    List<Long> ideaIds;

    public long getManifestoId() {
        return manifestoId;
    }

    public void setManifestoId(long manifestoId) {
        this.manifestoId = manifestoId;
    }

    public List<Long> getIdeaIds() {
        return ideaIds;
    }

    public void setIdeaIds(List<Long> ideaIds) {
        this.ideaIds = ideaIds;
    }
}
