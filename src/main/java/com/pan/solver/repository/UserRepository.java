package com.pan.solver.repository;

import com.pan.solver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yemingfeng
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAddress(String emailAddress);

    User findByNickname(String nickname);

}
