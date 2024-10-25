package com.npichuzhkin.JsonViewTask.repositories;

import com.npichuzhkin.JsonViewTask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
