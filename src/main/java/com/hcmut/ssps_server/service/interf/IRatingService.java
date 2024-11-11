package com.hcmut.ssps_server.service.interf;

import com.hcmut.ssps_server.model.Rating;

import java.util.List;

public interface IRatingService {
    Rating createRating(Rating rating); //Hào
    List<Rating> getAllRatings(); // Triết
    List<Rating> getRatingOfCurrentStudent(); //Hào
    Rating getRatingByPrintingId(int printingId); // Triết
    List<Rating> getRatingsByStudentId(Long studentId); //Triết
    Rating updateRating(Rating rating); //Hào
    void deleteRating(int ratingId); // Triết
}
