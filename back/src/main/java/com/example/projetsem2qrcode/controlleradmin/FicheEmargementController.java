package com.example.projetsem2qrcode.controlleradmin;


import com.example.projetsem2qrcode.modele.FicheEmargement;
import com.example.projetsem2qrcode.modele.User;
import com.example.projetsem2qrcode.service.FicheEmargementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/fiche")
public class FicheEmargementController {


    private FicheEmargementService ficheEmargementService;

    @Autowired
    public FicheEmargementController(FicheEmargementService ficheEmargementService) {
        this.ficheEmargementService = ficheEmargementService;
    }


    @PostMapping("/liste")
    public ResponseEntity<FicheEmargement> addFiche(@RequestBody FicheEmargement ficheEmargement){

        return new ResponseEntity<>(ficheEmargementService.addFiche(ficheEmargement),HttpStatus.CREATED);
    }
    @GetMapping("/liste/{nomCours}")
    public ResponseEntity<List<User>> getListeEtudiant(@PathVariable String nomCours){
        List<User> listeUserSigne = ficheEmargementService.getListeEtudiantSigneByCoursName(nomCours);
        return new ResponseEntity<>(listeUserSigne, HttpStatus.OK);
    }

    @GetMapping("/liste")
    public ResponseEntity<List<FicheEmargement>> getAllListes(){
        return new ResponseEntity<>(ficheEmargementService.getAllFiche(), HttpStatus.OK);
    }

    @GetMapping("/liste/signer/{idFiche}/{nomEtu}")
    public ResponseEntity<?> premiereSignatureFiche(@PathVariable String idFiche, @PathVariable String nomEtu){
        ficheEmargementService.signerFicheEmargementDebut(idFiche, nomEtu);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/liste/{idFiche}")
    public ResponseEntity<?> deleteById(@PathVariable String idFiche){
        ficheEmargementService.deleteFicheById(idFiche);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
