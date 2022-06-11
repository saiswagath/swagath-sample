package com.demo.intuit.controller;

import com.demo.intuit.model.election.Citizen;
import com.demo.intuit.model.election.Manifesto;
import com.demo.intuit.model.election.ManifestoObjectResponse;
import com.demo.intuit.model.election.Rating;
import com.demo.intuit.model.inventory.OrderPlacedResponse;
import com.demo.intuit.model.inventory.SyncOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.intuit.repository.ElectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ElectionController {
    @Autowired
    ElectionRepository electionRepository;

    @PostMapping("/citizen")
    public ResponseEntity<String> createCitizen(@RequestBody Citizen citizen) {
        try {
            long citizenId = electionRepository.saveCitizen(new Citizen(citizen.getCitizenName(), citizen.getContender()));
            return new ResponseEntity<>("Citizen was created successfully with citizenId :" + citizenId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/rating")
    public ResponseEntity<String> createRating(@RequestBody Rating rating) {
        try {
            long ratingId = electionRepository.saveRating(new Rating(rating.getIdeaId(),rating.getRatedByCitizenId(),rating.getRating()));
            return new ResponseEntity<>("Rating was successfully rated for ideaId :" + rating.getIdeaId() + "and generated ratingId is " + ratingId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/manifesto")
    public ResponseEntity<?> createManifesto(@RequestBody Manifesto manifesto) {
        try {
            ManifestoObjectResponse manifestoObjectResponse = electionRepository.saveManifesto(new Manifesto(manifesto.getContenderId(), manifesto.getIdeaList()));
            return new ResponseEntity<>(manifestoObjectResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/getAllCitizens")
    public ResponseEntity<?> getAllCitizens() {
        try {
            List<Citizen> citizens = electionRepository.getAllCitizens();
            return new ResponseEntity<>(citizens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}