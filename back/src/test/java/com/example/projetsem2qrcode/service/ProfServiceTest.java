package com.example.projetsem2qrcode.service;

import com.example.projetsem2qrcode.exceptions.NomProfInvalideException;
import com.example.projetsem2qrcode.exceptions.PrenomProfInvalideException;
import com.example.projetsem2qrcode.exceptions.ProfDejaCreerException;
import com.example.projetsem2qrcode.exceptions.ProfInnexistantExcepton;
import com.example.projetsem2qrcode.modele.Prof;
import com.example.projetsem2qrcode.repository.ProfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProfService.class})
@ExtendWith(SpringExtension.class)
class ProfServiceTest {

    @Autowired
    private ProfService profService;

    @MockBean
    private ProfRepository profRepository;

    @BeforeEach
    void init() {
        profService = new ProfService(profRepository);
    }

    /**
     * Method under test: {@link ProfService#saveProf(Prof)}
     */
    @Test
    void testSaveProf() throws NomProfInvalideException, PrenomProfInvalideException, ProfDejaCreerException {
        //when
        when(this.profRepository.save((Prof) any())).thenReturn(new Prof("42", "Nom", "Prenom", new HashSet<>()));
        when(this.profRepository.findByNom((String) any()))
                .thenReturn(Optional.of(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        //assert
        assertThrows(ProfDejaCreerException.class,
                () -> this.profService.saveProf(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        verify(this.profRepository).findByNom((String) any());
    }

    /**
     * Method under test: {@link ProfService#saveProf(Prof)}
     */
    @Test
    void testSaveProf3() throws NomProfInvalideException, PrenomProfInvalideException, ProfDejaCreerException {
        //given
        Prof prof = new Prof("42", "Nom", "Prenom", new HashSet<>());

        //when
        when(this.profRepository.save((Prof) any())).thenReturn(prof);
        when(this.profRepository.findByNom((String) any())).thenReturn(Optional.empty());
        //Assert
        assertSame(prof, this.profService.saveProf(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        verify(this.profRepository).save((Prof) any());
        verify(this.profRepository).findByNom((String) any());
    }

    /**
     * Method under test: {@link ProfService#saveProf(Prof)}
     */
    @Test
    void testSaveProf4() throws NomProfInvalideException, PrenomProfInvalideException, ProfDejaCreerException {
        //when
        when(this.profRepository.save((Prof) any())).thenReturn(new Prof("42", "Nom", "Prenom", new HashSet<>()));
        when(this.profRepository.findByNom((String) any()))
                .thenReturn(Optional.of(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        //Assert
        assertThrows(NomProfInvalideException.class,
                () -> this.profService.saveProf(new Prof("42", null, "Prenom", new HashSet<>())));
    }

    /**
     * Method under test: {@link ProfService#saveProf(Prof)}
     */
    @Test
    void testSaveProf5() throws NomProfInvalideException, PrenomProfInvalideException, ProfDejaCreerException {
        //when
        when(this.profRepository.save((Prof) any())).thenReturn(new Prof("42", "Nom", "Prenom", new HashSet<>()));
        when(this.profRepository.findByNom((String) any()))
                .thenReturn(Optional.of(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        //Assert
        assertThrows(NomProfInvalideException.class,
                () -> this.profService.saveProf(new Prof("42", "", "Prenom", new HashSet<>())));
    }

    /**
     * Method under test: {@link ProfService#saveProf(Prof)}
     */
    @Test
    void testSaveProf6() throws NomProfInvalideException, PrenomProfInvalideException, ProfDejaCreerException {
        //when
        when(this.profRepository.save((Prof) any())).thenReturn(new Prof("42", "Nom", "Prenom", new HashSet<>()));
        when(this.profRepository.findByNom((String) any()))
                .thenReturn(Optional.of(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        //Assert
        assertThrows(PrenomProfInvalideException.class,
                () -> this.profService.saveProf(new Prof("42", "Nom", null, new HashSet<>())));
    }

    /**
     * Method under test: {@link ProfService#saveProf(Prof)}
     */
    @Test
    void testSaveProf7() throws NomProfInvalideException, PrenomProfInvalideException, ProfDejaCreerException {
        //when
        when(this.profRepository.save((Prof) any())).thenReturn(new Prof("42", "Nom", "Prenom", new HashSet<>()));
        when(this.profRepository.findByNom((String) any()))
                .thenReturn(Optional.of(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        //assert
        assertThrows(PrenomProfInvalideException.class,
                () -> this.profService.saveProf(new Prof("42", "Nom", "", new HashSet<>())));
    }


    /**
     * Method under test: {@link ProfService#findProfByNom(String)}
     */
    @Test
    void testFindProfByNomOk() throws ProfInnexistantExcepton {
        //given
        Prof prof = new Prof("42", "Nom", "Prenom", new HashSet<>());
        //when
        when(this.profRepository.findByNom((String) any())).thenReturn(Optional.of(prof));
        //assert
        assertSame(prof, this.profService.findProfByNom("Nom Prof"));
        verify(this.profRepository).findByNom((String) any());
    }

    /**
     * Method under test: {@link ProfService#findProfByNom(String)}
     */
    @Test
    void testFindProfByNomKo() throws ProfInnexistantExcepton {
        //when
        when(this.profRepository.findByNom((String) any())).thenReturn(Optional.empty());
        //assert
        assertThrows(ProfInnexistantExcepton.class, () -> this.profService.findProfByNom("Nom Prof"));
        verify(this.profRepository).findByNom((String) any());
    }

    /**
     * Method under test: {@link ProfService#getAllProf()}
     */
    @Test
    void testGetAllProf() {
        //given
        ArrayList<Prof> profList = new ArrayList<>();
        //when
        when(this.profRepository.findAll()).thenReturn(profList);
        List<Prof> actualAllProf = this.profService.getAllProf();
        //assert
        assertSame(profList, actualAllProf);
        assertTrue(actualAllProf.isEmpty());
        verify(this.profRepository).findAll();
    }

    /**
     * Method under test: {@link ProfService#deleteProfByNom(String)}
     */
    @Test
    void testDeleteProfByNom() throws ProfInnexistantExcepton {
        //when
        doNothing().when(this.profRepository).deleteById((String) any());
        when(this.profRepository.findByNom((String) any()))
                .thenReturn(Optional.of(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        //assert
        assertThrows(ProfInnexistantExcepton.class, () -> this.profService.deleteProfByNom("Nom Prof"));
        verify(this.profRepository).findByNom((String) any());
        verify(this.profRepository).deleteById((String) any());
    }

    /**
     * Method under test: {@link ProfService#deleteProfByNom(String)}
     */
    @Test
    void testDeleteProfByNom2() throws ProfInnexistantExcepton {
        //when
        doNothing().when(this.profRepository).deleteById((String) any());
        when(this.profRepository.findByNom((String) any())).thenReturn(Optional.empty());
        //assert
        assertThrows(ProfInnexistantExcepton.class, () -> this.profService.deleteProfByNom("Nom Prof"));
        verify(this.profRepository).findByNom((String) any());
    }

    /**
     * Method under test: {@link ProfService#deleteAllProf()}
     */
    @Test
    void testDeleteAllProfOk() {
        //when
        doNothing().when(this.profRepository).deleteAll();
        this.profService.deleteAllProf();
        //assert
        verify(this.profRepository).deleteAll();
        assertTrue(this.profService.getAllProf().isEmpty());
    }

    /**
     * Method under test: {@link ProfService#deleteAllCoursForProf(String)}
     */
    @Test
    void testDeleteAllCoursForProf() throws ProfInnexistantExcepton {
        //when
        when(this.profRepository.findByNom((String) any()))
                .thenReturn(Optional.of(new Prof("42", "Nom", "Prenom", new HashSet<>())));
        //assert
        assertThrows(ProfInnexistantExcepton.class, () -> this.profService.deleteAllCoursForProf("Nom Prof"));
        verify(this.profRepository).findByNom((String) any());
    }

    /**
     * Method under test: {@link ProfService#deleteAllCoursForProf(String)}
     */
    @Test
    void testDeleteAllCoursForProf2() throws ProfInnexistantExcepton {
        //when
        when(this.profRepository.findByNom((String) any())).thenReturn(Optional.empty());
        //assert
        assertThrows(ProfInnexistantExcepton.class, () -> this.profService.deleteAllCoursForProf("Nom Prof"));
        verify(this.profRepository).findByNom((String) any());
    }

}