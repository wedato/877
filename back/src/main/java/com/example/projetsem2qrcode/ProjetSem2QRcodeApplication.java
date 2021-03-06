package com.example.projetsem2qrcode;

import com.example.projetsem2qrcode.modele.FicheEmargement;
import com.example.projetsem2qrcode.modele.User;
import com.example.projetsem2qrcode.repository.FicheEmargementRepository;
import com.example.projetsem2qrcode.service.FicheEmargementService;
import com.example.projetsem2qrcode.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.projetsem2qrcode.constant.FileConstant.USER_FOLDER;

@SpringBootApplication
public class ProjetSem2QRcodeApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProjetSem2QRcodeApplication.class, args);
        new File(USER_FOLDER).mkdirs();

    }

    // pour que le cors nous laisse récuperer notre jwt token
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.addAllowedOrigin("http://localhost:4200");
        corsConfiguration.addAllowedOrigin("http://localhost:8100");
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);



    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
   CommandLineRunner start(UserServiceImpl userService, FicheEmargementRepository ficheRepository , FicheEmargementService ficheService){
        return args -> {
            userService.deleteAdmin();
            userService.deletejo();
            ficheRepository.deleteAll();
            User jo = userService.addJo();
            User admin =userService.addNewAdmin();

            List<User> listeUserInscrit = new ArrayList<>();


            listeUserInscrit.add(admin);


            FicheEmargement webService = new FicheEmargement("WebService");
            webService.setImageUrl("https://thumbs.dreamstime.com/b/vector-global-web-service-icon-isolated-black-flat-design-concept-163719580.jpg");
            webService.setListeEtudiantSigne(listeUserInscrit);
            webService.setListeEleves(List.of("Jonathan","Louis","Clement","Mickael"));
            webService.setDateCours(new Date());
            ficheRepository.save(webService);











        };
   }




}
