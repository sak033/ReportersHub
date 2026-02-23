package com.example.demo.repository;

import com.example.demo.model.Rating;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByReporterAndUser(User reporter, User user);

    List<Rating> findByReporter(User reporter);
}