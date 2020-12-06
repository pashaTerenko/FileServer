package com.terenko.fileserver.Repository;


import com.terenko.fileserver.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<CustomUser, String> {
    CustomUser findByLogin(@Param("login") String login);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.login = :login")
   boolean existsByLogin(@Param("login") String login);



}
