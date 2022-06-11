package com.demo.intuit.repository;

import com.demo.intuit.model.election.Citizen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class JdbcElectionRepositoryTest {

    @Mock
    ElectionRepository electionRepository;

    @Test
    @DisplayName("Should Test If saving citizen is working as expected")
    void saveCitizen() {
        Citizen citizen = new Citizen("K.A Sai Swagath", true);

        electionRepository.saveCitizen(citizen);

        Mockito.verify(electionRepository, times(1)).saveCitizen(citizen);
    }
}