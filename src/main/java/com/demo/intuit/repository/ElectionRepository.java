package com.demo.intuit.repository;

import com.demo.intuit.model.election.Citizen;
import com.demo.intuit.model.election.Manifesto;
import com.demo.intuit.model.election.ManifestoObjectResponse;
import com.demo.intuit.model.election.Rating;

import java.util.List;

public interface ElectionRepository {
    long saveCitizen(Citizen citizen);
    List<Citizen> getAllCitizens();
    ManifestoObjectResponse saveManifesto(Manifesto manifesto);
    long saveRating(Rating rating);
}
