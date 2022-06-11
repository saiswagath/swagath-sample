package com.demo.intuit.repository;

import com.demo.intuit.exception.CraftDemoServicesException;
import com.demo.intuit.model.election.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class JdbcElectionRepository implements ElectionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long saveCitizen(Citizen citizen) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO CITIZEN (citizen_name, is_contender) VALUES(?,?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, citizen.getCitizenName());
                    statement.setBoolean(2, citizen.getContender());
                    return statement;
                }
            }, holder);
            long citizenUniqueId = holder.getKey().longValue();
            return citizenUniqueId;
        } catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while inserting citizen with name :" + citizen.getCitizenName() + " in JdbcElectionRepository::saveCitizen() due to " + e.getMessage());
        }
    }

    @Override
    public List<Citizen> getAllCitizens() {
        try {
            List<Citizen> citizens = jdbcTemplate.query("select * from CITIZEN", (result, rowNum) -> new Citizen(result.getInt("citizen_id"),
                    result.getString("citizen_name"), result.getBoolean("is_contender")));
            for (Citizen citizen : citizens) {

                if (citizen.getContender()) {
                    String manifestoQuery = "SELECT * FROM MANIFESTO WHERE contender_id = " + citizen.getCitizenId();
                    List<Manifesto> manifestoList = jdbcTemplate.query(manifestoQuery, (result, rowNum) -> new Manifesto(result.getInt("manifesto_id"),
                            result.getInt("contender_id")));
                    if (manifestoList != null) {
                        for (Manifesto manifesto : manifestoList) {
                            String ideaQuery = "SELECT * FROM IDEA WHERE manifesto_id = " + manifesto.getManifestoId();
                            List<Idea> ideaList = jdbcTemplate.query(ideaQuery, (result, rowNum) -> new Idea(result.getInt("idea_id"),
                                    result.getInt("manifesto_id"), result.getString("idea_description")));
                            if (ideaList != null && ideaList.size() > 0) {
                                manifesto.setIdeaList(ideaList);
                            }
                        }
                        citizen.setManifestoList(manifestoList);
                    }
                }
                String followingCitizensQuery = "SELECT * FROM FOLLOWER_INFO WHERE follower_id = " + citizen.getCitizenId();
                List<Follower> followingCitizens = jdbcTemplate.query(followingCitizensQuery, (result, rowNum) -> new Follower(result.getInt("follower_info_record_id"),
                        result.getInt("citizen_id"), result.getInt("follower_id")));
                if(followingCitizens != null && followingCitizens.size() > 0){
                    citizen.setFollowingContenders(followingCitizens);
                }

                String followedByCitizensQuery = "SELECT * FROM FOLLOWER_INFO WHERE citizen_id = " + citizen.getCitizenId();
                List<Follower> followedByCitizen = jdbcTemplate.query(followedByCitizensQuery, (result, rowNum) -> new Follower(result.getInt("follower_info_record_id"),
                        result.getInt("citizen_id"), result.getInt("follower_id")));
                if(followedByCitizen != null && followedByCitizen.size() > 0){
                    citizen.setFollowedByCitizens(followedByCitizen);
                }
            }
            return citizens;
        } catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while fetching all citizens in JdbcElectionRepository::getAllCitizens() due to " + e.getMessage());
        }
    }

    @Override
    public ManifestoObjectResponse saveManifesto(Manifesto manifesto) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO MANIFESTO (contender_id) VALUES(?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, manifesto.getContenderId());
                    return statement;
                }
            }, holder);
            long manifestoUniqueId = holder.getKey().longValue();
            ManifestoObjectResponse manifestoObjectResponse = new ManifestoObjectResponse();
            manifestoObjectResponse.setManifestoId(manifestoUniqueId);
            if(manifesto.getIdeaList() != null && manifesto.getIdeaList().size() > 0 && manifesto.getIdeaList().size() <= 3) {
                List<Long> ideaIdList = new ArrayList<>();
                for (Idea idea : manifesto.getIdeaList()) {
                    idea.setManifestoId(manifestoUniqueId);
                    long ideaUniqueId = saveIdea(idea);
                    ideaIdList.add(ideaUniqueId);
                    sendEmailToFollowers(ideaUniqueId, manifesto.getContenderId(), new HashSet<Long>());
                }
                manifestoObjectResponse.setIdeaIds(ideaIdList);
            }
            return manifestoObjectResponse;
        } catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while inserting manifesto for contenderId :" + manifesto.getContenderId() + " in JdbcElectionRepository::saveManifesto() due to " + e.getMessage());
        }
    }

    private void sendEmailToFollowers(long ideaId, long contenderId, Set<Long> cycleSet){
        try {
            cycleSet.add(contenderId);
            String followingCitizensQuery = "SELECT * FROM FOLLOWER_INFO WHERE citizen_id = " + contenderId;
            List<Follower> followingCitizens = jdbcTemplate.query(followingCitizensQuery, (result, rowNum) -> new Follower(result.getInt("follower_info_record_id"),
                    result.getInt("citizen_id"), result.getInt("follower_id")));

            if(followingCitizens != null){
                for(Follower follower : followingCitizens){
                    if(!cycleSet.contains(follower.getFollowerId())){
                        System.out.println("Email sent regarding Idea with ideaId:" + ideaId +"posted by contender with contenderId" + contenderId + "to follower " + follower.getFollowerId());
                        sendEmailToFollowers(ideaId, follower.getFollowerId(), cycleSet);
                    }

                }
            }else{
                return;
            }
        }catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while sending email to followers of contenderId :" + contenderId + " in JdbcElectionRepository::sendEmailToFollowers() due to " + e.getMessage());
        }

    }

    @Override
    public long saveRating(Rating rating) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO RATING (idea_id, rated_by_citizen_id, ratingScore) VALUES(?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, rating.getIdeaId());
                    statement.setLong(2, rating.getRatedByCitizenId());
                    statement.setLong(3, rating.getRating());
                    return statement;
                }
            }, holder);
            long ratingUniqueId = holder.getKey().longValue();
            if(rating.getRating() > 5){
                Idea ideaObj = jdbcTemplate.queryForObject("SELECT * from IDEA WHERE idea_id = ?",
                        BeanPropertyRowMapper.newInstance(Idea.class), rating.getIdeaId());
                if(ideaObj != null){
                    Manifesto manifesto = jdbcTemplate.queryForObject("SELECT * from MANIFESTO WHERE manifesto_id = ?",
                            BeanPropertyRowMapper.newInstance(Manifesto.class), ideaObj.getManifestoId());
                    if(manifesto != null){
                        String followerInsertQuery = "INSERT INTO FOLLOWER_INFO (citizen_id, follower_id) VALUES(?,?)";
                        jdbcTemplate.update(followerInsertQuery, new Object[] {manifesto.getContenderId(), rating.getRatedByCitizenId()});
                    }

                }
            }
            return ratingUniqueId;
        } catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while inserting rating for ideaId:" + rating.getIdeaId() + " in JdbcElectionRepository::saveRating() due to " + e.getMessage());
        }
    }

    public long saveIdea(Idea idea) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO IDEA (manifesto_id, idea_description) VALUES(?,?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, idea.getManifestoId());
                    statement.setString(2, idea.getIdeaDescription());
                    return statement;
                }
            }, holder);
            long ideaUniqueId = holder.getKey().longValue();
            return ideaUniqueId;
        } catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while inserting idea in JdbcElectionRepository::saveIdea() due to " + e.getMessage());
        }
    }
}
