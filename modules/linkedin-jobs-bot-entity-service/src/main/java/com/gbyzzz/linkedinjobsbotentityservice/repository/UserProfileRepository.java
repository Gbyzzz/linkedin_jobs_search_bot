package com.gbyzzz.linkedinjobsbotentityservice.repository;

import com.gbyzzz.linkedinjobsbotentityservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
