package com.pdg.pymesbackend.repository;

import com.pdg.pymesbackend.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, String> {

    Optional<Company> findByLegalRepEmail(String email);



}
