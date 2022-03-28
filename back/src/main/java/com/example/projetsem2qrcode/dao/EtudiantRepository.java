package com.example.projetsem2qrcode.dao;

import com.example.projetsem2qrcode.modele.Etudiant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EtudiantRepository extends MongoRepository<Etudiant,String> {
    Optional<Etudiant> findEtudiantByEmail(String email);


}
