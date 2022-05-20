package com.example.projetsem2qrcode.controlleradmin;

import com.example.projetsem2qrcode.exceptions.ProfInnexistantExcepton;
import com.example.projetsem2qrcode.modele.Cours;
import com.example.projetsem2qrcode.modele.GroupeTp;
import com.example.projetsem2qrcode.modele.Prof;
import com.example.projetsem2qrcode.service.ProfService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ProfController.class})
@ExtendWith(SpringExtension.class)
class ProfControllerTest {

    @Autowired
    private ProfController profController;

    @MockBean
    private ProfService profService;

    /**
     * Method under test: {@link ProfController#createProf(Prof)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateProf() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.example.projetsem2qrcode.controlleradmin.ProfController.createProf(ProfController.java:29)
        //   In order to prevent createProf(Prof)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   createProf(Prof).
        //   See https://diff.blue/R013 to resolve this issue.

        ProfController profController = new ProfController();
        profController.createProf(new Prof("42", "Nom", "Prenom", new HashSet<>()));
    }

    /**
     * Method under test: {@link ProfController#createProf(Prof)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateProf2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.example.projetsem2qrcode.controlleradmin.ProfController.createProf(ProfController.java:29)
        //   In order to prevent createProf(Prof)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   createProf(Prof).
        //   See https://diff.blue/R013 to resolve this issue.

        ProfController profController = new ProfController();
        Cours cours = mock(Cours.class);
        doNothing().when(cours).setHeureDebut((LocalDate) any());
        doNothing().when(cours).setHeureFin((LocalDate) any());
        doNothing().when(cours).setId((String) any());
        doNothing().when(cours).setLesGroupes((java.util.Set<GroupeTp>) any());
        doNothing().when(cours).setNom((String) any());
        doNothing().when(cours).setProf((Prof) any());
        cours.setHeureDebut(null);
        cours.setHeureFin(null);
        cours.setId("42");
        cours.setLesGroupes(new HashSet<>());
        cours.setNom("Nom");
        cours.setProf(new Prof("42", "Nom", "Prenom", new HashSet<>()));

        HashSet<Cours> coursSet = new HashSet<>();
        coursSet.add(cours);
        profController.createProf(new Prof("42", "Nom", "Prenom", coursSet));
    }

    /**
     * Method under test: {@link ProfController#deleteAllCoursInProf(String)}
     */
    @Test
    void testDeleteAllCoursInProf() throws Exception {
        doNothing().when(this.profService).deleteAllCoursForProf((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof/{nomProf}/cours",
                "Nom Prof");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteAllCoursInProf(String)}
     */
    @Test
    void testDeleteAllCoursInProf2() throws Exception {
        doNothing().when(this.profService).deleteAllCoursForProf((String) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/prof/{nomProf}/cours", "Nom Prof");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteAllCoursInProf(String)}
     */
    @Test
    void testDeleteAllCoursInProf3() throws Exception {
        doThrow(new ProfInnexistantExcepton()).when(this.profService).deleteAllCoursForProf((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof/{nomProf}/cours",
                "Nom Prof");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ProfController#deleteAllCoursInProf(String)}
     */
    @Test
    void testDeleteAllCoursInProf4() throws Exception {
        doNothing().when(this.profService).deleteProfByNom((String) any());
        doNothing().when(this.profService).deleteAllCoursForProf((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof/{nomProf}/cours", "",
                "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteAllCoursInProf(String)}
     */
    @Test
    void testDeleteAllCoursInProf5() throws Exception {
        doNothing().when(this.profService).deleteProfByNom((String) any());
        doNothing().when(this.profService).deleteAllCoursForProf((String) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/prof/{nomProf}/cours", "",
                "Uri Vars");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteAllCoursInProf(String)}
     */
    @Test
    void testDeleteAllCoursInProf6() throws Exception {
        doThrow(new ProfInnexistantExcepton()).when(this.profService).deleteProfByNom((String) any());
        doNothing().when(this.profService).deleteAllCoursForProf((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof/{nomProf}/cours", "",
                "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ProfController#deleteProfByName(String)}
     */
    @Test
    void testDeleteProfByName() throws Exception {
        doNothing().when(this.profService).deleteProfByNom((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof/{nomProf}", "Nom Prof");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteProfByName(String)}
     */
    @Test
    void testDeleteProfByName2() throws Exception {
        doNothing().when(this.profService).deleteProfByNom((String) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/prof/{nomProf}", "Nom Prof");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteProfByName(String)}
     */
    @Test
    void testDeleteProfByName3() throws Exception {
        doThrow(new ProfInnexistantExcepton()).when(this.profService).deleteProfByNom((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof/{nomProf}", "Nom Prof");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ProfController#deleteProfByName(String)}
     */
    @Test
    void testDeleteProfByName4() throws Exception {
        doNothing().when(this.profService).deleteAllProf();
        doNothing().when(this.profService).deleteProfByNom((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof/{nomProf}", "", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteProfByName(String)}
     */
    @Test
    void testDeleteProfByName5() throws Exception {
        doNothing().when(this.profService).deleteAllProf();
        doNothing().when(this.profService).deleteProfByNom((String) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/prof/{nomProf}", "", "Uri Vars");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#getAllProf()}
     */
    @Test
    void testGetAllProf() throws Exception {
        when(this.profService.getAllProf()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/prof");
        MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ProfController#getAllProf()}
     */
    @Test
    void testGetAllProf2() throws Exception {
        when(this.profService.getAllProf()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/prof");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ProfController#deleteAllProf()}
     */
    @Test
    void testDeleteAllProf() throws Exception {
        doNothing().when(this.profService).deleteAllProf();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/prof");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#deleteAllProf()}
     */
    @Test
    void testDeleteAllProf2() throws Exception {
        doNothing().when(this.profService).deleteAllProf();
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/prof");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link ProfController#getProfByNom(String)}
     */
    @Test
    void testGetProfByNom() throws Exception {
        when(this.profService.findProfByNom((String) any())).thenReturn(new Prof("42", "Nom", "Prenom", new HashSet<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/prof/{nomProf}", "Nom Prof");
        MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":\"42\",\"nom\":\"Nom\",\"prenom\":\"Prenom\",\"coursDuProf\":[]}"));
    }

    /**
     * Method under test: {@link ProfController#getProfByNom(String)}
     */
    @Test
    void testGetProfByNom2() throws Exception {
        when(this.profService.findProfByNom((String) any())).thenReturn(new Prof("42", "Nom", "Prenom", new HashSet<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/prof/{nomProf}", "Nom Prof");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":\"42\",\"nom\":\"Nom\",\"prenom\":\"Prenom\",\"coursDuProf\":[]}"));
    }

    /**
     * Method under test: {@link ProfController#getProfByNom(String)}
     */
    @Test
    void testGetProfByNom3() throws Exception {
        when(this.profService.findProfByNom((String) any())).thenThrow(new ProfInnexistantExcepton());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/prof/{nomProf}", "Nom Prof");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.profController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}