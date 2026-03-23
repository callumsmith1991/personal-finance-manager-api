package com.personal_finance_manager_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.personal_finance_manager_api.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
}
