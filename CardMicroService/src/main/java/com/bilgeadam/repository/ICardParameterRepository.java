package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.CardParameter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICardParameterRepository extends MongoRepository<CardParameter,String> {
    Optional<CardParameter> findByGroupName(String groupName);
}
