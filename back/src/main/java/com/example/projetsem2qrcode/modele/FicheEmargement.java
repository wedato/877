package com.example.projetsem2qrcode.modele;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "fiche_emargements")
@Data
@NoArgsConstructor
public class FicheEmargement {


    @Id
    private String id;
    private String nomCours;
    private List<User> listeEtudiantSigne;
    private List<String> listeEleves;
    private Date dateCours;
    private String imageUrl;

    public FicheEmargement(String nomCours) {
        this.nomCours = nomCours;
    }
}
