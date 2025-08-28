package com.example.app.service;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.app.model.Mood;
import com.example.app.model.User;
import com.example.app.repository.MoodRepository;

@Service
public class MoodServices {


    @Autowired
    MoodRepository moodRepository;
    @Autowired
    UserServices userServices;


     public List<Mood> UserHistoryByEmail(String userEmail) {
        Optional<User> user = userServices.findByEmail(userEmail);
        return moodRepository.findByUserId(user.get().getId());
    }

     public List<Mood> UserHistoryByUserID(String userId) {
        return moodRepository.findByUserId(userId);
    }



    public Mood saveMood(Mood mood, String email){
        Optional<User> userObjOptional = userServices.findByEmail(email);
        String userID= userObjOptional.get().getId();
        mood.setUserId(userID);

        String timeZone= userObjOptional.get().getTimezone();
        ZoneId zone = ZoneId.of(timeZone);
        ZonedDateTime now = ZonedDateTime.now(zone);
     
        
        Instant currentTimeDate = now.toInstant();
        mood.setDate(currentTimeDate);
    

        return moodRepository.save(mood);
    }
  

    public String deleteNote(String id){

        Optional<Mood> userMood = moodRepository.findById(id);
        String userId =userMood.get().getUserId();
        moodRepository.deleteById(id);
        return userId;
        
    }
    
}
