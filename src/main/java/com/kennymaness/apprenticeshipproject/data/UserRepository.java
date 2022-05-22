package com.kennymaness.apprenticeshipproject.data;

import com.kennymaness.apprenticeshipproject.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
