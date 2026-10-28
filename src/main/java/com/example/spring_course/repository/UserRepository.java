package com.example.spring_course.repository;

import com.example.spring_course.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByNome(String nome);

    @Query ("SELECT u FROM UserEntity u WHERE u.email = :email")
    List<UserEntity> findByEmail(@Param("email") String email);

    //Opzione non consiglita
//    @Query (value = "SELECT * FROM users AS u WHERE u.nome = :nome", nativeQuery = true)
//    List<UserEntity> findByName(@Param("nome") String nome);
}
