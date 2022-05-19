package com.example.projetsem2qrcode.service;

import com.example.projetsem2qrcode.repository.EtudiantRepository;
import com.example.projetsem2qrcode.repository.GroupeTpRepository;
import com.example.projetsem2qrcode.exceptions.*;
import com.example.projetsem2qrcode.modele.Etudiant;
import com.example.projetsem2qrcode.modele.GroupeTp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GroupeTpService {
    @Autowired
    private final GroupeTpRepository groupeTpRepository;

    @Autowired
    private final EtudiantRepository etudiantRepository;

    public GroupeTp saveGroupeTp (String nomGroupe) throws GroupeDejaCreerException, NomGroupeNonValideException {
        Optional<GroupeTp> groupeTp = groupeTpRepository.findByNomGroupe(nomGroupe);
        if (groupeTp.isPresent()){
            throw new GroupeDejaCreerException();
        }
        if (nomGroupe == null || nomGroupe.isBlank()){
            throw new NomGroupeNonValideException();
        }
        GroupeTp groupe = new GroupeTp(nomGroupe);
        return groupeTpRepository.save(groupe);
    }

    public GroupeTp findGroupeByNumGroupe (String numeroGroupe) throws GroupeInnexistantException {
        Optional<GroupeTp> groupeTp = groupeTpRepository.findByNomGroupe(numeroGroupe);
        if (groupeTp.isPresent()){
            return groupeTp.get();
        }
        throw new GroupeInnexistantException();
    }

    public List<GroupeTp> getAllGroupeTp(){
        return groupeTpRepository.findAll();
    }

    public void deleteAllGroupeTp(){
        groupeTpRepository.deleteAll();
    }

    public void deleteGroupeByNumGroupe (String nomGroupe) throws GroupeInnexistantException{
        Optional<GroupeTp> groupeTp = groupeTpRepository.findByNomGroupe(nomGroupe);
        if (groupeTp.isPresent()){
            GroupeTp groupeSupp = groupeTp.get();
            groupeTpRepository.deleteById(groupeSupp.getId());
        }
        throw new GroupeInnexistantException();
    }

    public GroupeTp addEtudiantInGroupe(String groupe, String numEtudiant) throws GroupeInnexistantException, EtudiantInnexistantException, EtudiantDejaDansUnGroupeException {
        Optional<GroupeTp> groupeTp = groupeTpRepository.findByNomGroupe(groupe);
        Optional<Etudiant> etudiant = etudiantRepository.findEtudiantByNumEtudiant(numEtudiant);
        if (groupeTp.isEmpty()){
            throw new GroupeInnexistantException();
        }
        if (etudiant.isEmpty()){
            throw new EtudiantInnexistantException();
        }
        Etudiant _etudiant = etudiant.get();
        GroupeTp _groupeTp = groupeTp.get();

        if (_groupeTp.getListeEtudiantGroupe().contains(_etudiant)){
            throw new EtudiantDejaDansUnGroupeException();
        }
        _etudiant.setGroupeTp(_groupeTp.getNomGroupe());
        etudiantRepository.save(_etudiant);
        _groupeTp.getListeEtudiantGroupe().add(_etudiant);
        return groupeTpRepository.save(_groupeTp);
    }

    public void deleteAllEtudiantInGroupeTp(String numeroGroupe){
        Optional<GroupeTp> groupeTp = groupeTpRepository.findByNomGroupe(numeroGroupe);
        groupeTp.get().getListeEtudiantGroupe().clear();
        groupeTpRepository.save(groupeTp.get());
    }
}
