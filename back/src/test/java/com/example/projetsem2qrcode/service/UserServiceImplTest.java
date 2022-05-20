package com.example.projetsem2qrcode.service;

import com.example.projetsem2qrcode.exceptions.EmailExistException;
import com.example.projetsem2qrcode.exceptions.EmailNotFoundException;
import com.example.projetsem2qrcode.exceptions.NotAnImageFileException;
import com.example.projetsem2qrcode.exceptions.UserNotFoundException;
import com.example.projetsem2qrcode.exceptions.UsernameExistException;
import com.example.projetsem2qrcode.modele.User;
import com.example.projetsem2qrcode.repository.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userServiceImpl;

    private UserServiceImpl underTest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private LoginAttemptService loginAttemptService;
    @Mock
    private EmailService emailService;


    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository, passwordEncoder, loginAttemptService, emailService);
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        when(this.loginAttemptService.hasExceededMaxAttempts((String) any())).thenReturn(true);
        UserDetails actualLoadUserByUsernameResult = this.userServiceImpl.loadUserByUsername("janedoe");
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertFalse(actualLoadUserByUsernameResult.isAccountNonLocked());
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.loginAttemptService).hasExceededMaxAttempts((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        when(this.loginAttemptService.hasExceededMaxAttempts((String) any()))
                .thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.loadUserByUsername("janedoe"));
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.loginAttemptService).hasExceededMaxAttempts((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#add(String, String, String, String)}
     */
    @Test
    void testAdd() throws EmailExistException, UserNotFoundException, UsernameExistException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        assertThrows(UsernameExistException.class,
                () -> this.userServiceImpl.add("Jane", "Doe", "janedoe", "jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#register(String, String, String, String)}
     */
    @Test
    void testRegister() throws EmailExistException, UserNotFoundException, UsernameExistException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        assertThrows(UsernameExistException.class,
                () -> this.userServiceImpl.register("Jane", "Doe", "janedoe", "jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#addNewUser(String, String, String, String, String, boolean, boolean, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testAddNewUser()
            throws EmailExistException, NotAnImageFileException, UserNotFoundException, UsernameExistException, IOException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        assertThrows(UsernameExistException.class,
                () -> this.userServiceImpl.addNewUser("Jane", "Doe", "janedoe", "jane.doe@example.org", "Role", true, true,
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#addNewAdmin()}
     */
    @Test
    void testAddNewAdmin() throws EmailExistException, UserNotFoundException, UsernameExistException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");

        User user2 = new User();
        user2.setAuthorities(new String[]{"JaneDoe"});
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId("42");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setJoinDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLoginDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLoginDateDisplay(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setLastName("Doe");
        user2.setNotLocked(true);
        user2.setPassword("iloveyou");
        user2.setProfileImageUrl("https://example.org/example");
        user2.setRole("Role");
        user2.setSignedEndClass(true);
        user2.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        when(this.userRepository.save((User) any())).thenReturn(user2);
        assertThrows(UsernameExistException.class, () -> this.userServiceImpl.addNewAdmin());
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteAdmin()}
     */
    @Test
    void testDeleteAdmin() {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        doNothing().when(this.userRepository).delete((User) any());
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        this.userServiceImpl.deleteAdmin();
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).delete((User) any());
        assertTrue(this.userServiceImpl.getUsers().isEmpty());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteAdmin()}
     */
    @Test
    void testDeleteAdmin2() {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        doThrow(new UsernameNotFoundException("admin")).when(this.userRepository).delete((User) any());
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.deleteAdmin());
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#addJo()}
     */
    @Test
    void testAddJo() throws EmailExistException, UserNotFoundException, UsernameExistException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");

        User user2 = new User();
        user2.setAuthorities(new String[]{"JaneDoe"});
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId("42");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setJoinDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLoginDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setLastLoginDateDisplay(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setLastName("Doe");
        user2.setNotLocked(true);
        user2.setPassword("iloveyou");
        user2.setProfileImageUrl("https://example.org/example");
        user2.setRole("Role");
        user2.setSignedEndClass(true);
        user2.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        when(this.userRepository.save((User) any())).thenReturn(user2);
        assertThrows(UsernameExistException.class, () -> this.userServiceImpl.addJo());
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deletejo()}
     */
    @Test
    void testDeletejo() {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        doNothing().when(this.userRepository).delete((User) any());
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        this.userServiceImpl.deletejo();
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).delete((User) any());
        assertTrue(this.userServiceImpl.getUsers().isEmpty());
    }

    /**
     * Method under test: {@link UserServiceImpl#deletejo()}
     */
    @Test
    void testDeletejo2() {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        doThrow(new UsernameNotFoundException("jo")).when(this.userRepository).delete((User) any());
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.deletejo());
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(String, String, String, String, String, String, boolean, boolean, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser()
            throws EmailExistException, NotAnImageFileException, UserNotFoundException, UsernameExistException, IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: No enum constant com.example.projetsem2qrcode.config.Role.ROLE
        //       at java.lang.Enum.valueOf(Enum.java:240)
        //       at com.example.projetsem2qrcode.config.Role.valueOf(Role.java:3)
        //       at com.example.projetsem2qrcode.service.UserServiceImpl.getRoleEnumName(UserServiceImpl.java:255)
        //       at com.example.projetsem2qrcode.service.UserServiceImpl.updateUser(UserServiceImpl.java:177)
        //   In order to prevent updateUser(String, String, String, String, String, String, boolean, boolean, MultipartFile)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   updateUser(String, String, String, String, String, String, boolean, boolean, MultipartFile).
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        this.userServiceImpl.updateUser("janedoe", "Jane", "Doe", "janedoe", "jane.doe@example.org", "Role", true, true,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(String, String, String, String, String, String, boolean, boolean, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser2()
            throws EmailExistException, NotAnImageFileException, UserNotFoundException, UsernameExistException, IOException {
        User user = mock(User.class);
        when(user.getId()).thenReturn("foo");
        doNothing().when(user).setAuthorities((String[]) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setFirstName((String) any());
        doNothing().when(user).setId((String) any());
        doNothing().when(user).setJoinDate((Date) any());
        doNothing().when(user).setLastLoginDate((Date) any());
        doNothing().when(user).setLastLoginDateDisplay((Date) any());
        doNothing().when(user).setLastName((String) any());
        doNothing().when(user).setNotLocked(anyBoolean());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setProfileImageUrl((String) any());
        doNothing().when(user).setRole((String) any());
        doNothing().when(user).setSignedEndClass(anyBoolean());
        doNothing().when(user).setUsername((String) any());
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        assertThrows(EmailExistException.class,
                () -> this.userServiceImpl.updateUser("janedoe", "Jane", "Doe", "janedoe", "jane.doe@example.org", "Role", true,
                        true, new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository, atLeast(1)).findUserByUsername((String) any());
        verify(user).getId();
        verify(user).setAuthorities((String[]) any());
        verify(user).setEmail((String) any());
        verify(user).setFirstName((String) any());
        verify(user).setId((String) any());
        verify(user).setJoinDate((Date) any());
        verify(user).setLastLoginDate((Date) any());
        verify(user).setLastLoginDateDisplay((Date) any());
        verify(user).setLastName((String) any());
        verify(user).setNotLocked(anyBoolean());
        verify(user).setPassword((String) any());
        verify(user).setProfileImageUrl((String) any());
        verify(user).setRole((String) any());
        verify(user).setSignedEndClass(anyBoolean());
        verify(user).setUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers() {
        ArrayList<User> userList = new ArrayList<>();
        when(this.userRepository.findAll()).thenReturn(userList);
        List<User> actualUsers = this.userServiceImpl.getUsers();
        assertSame(userList, actualUsers);
        assertTrue(actualUsers.isEmpty());
        verify(this.userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers()}
     */
    @Test
    void testGetUsers2() {
        when(this.userRepository.findAll()).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.getUsers());
        verify(this.userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#findUserByUsername(String)}
     */
    @Test
    void testFindUserByUsername() {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        assertSame(user, this.userServiceImpl.findUserByUsername("janedoe"));
        verify(this.userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#findUserByUsername(String)}
     */
    @Test
    void testFindUserByUsername2() {
        when(this.userRepository.findUserByUsername((String) any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.findUserByUsername("janedoe"));
        verify(this.userRepository).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#findUserByEmail(String)}
     */
    @Test
    void testFindUserByEmail() {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        assertSame(user, this.userServiceImpl.findUserByEmail("jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#findUserByEmail(String)}
     */
    @Test
    void testFindUserByEmail2() {
        when(this.userRepository.findUserByEmail((String) any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.findUserByEmail("jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser() throws UserNotFoundException, IOException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        this.userServiceImpl.deleteUser("janedoe");
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).deleteById((String) any());
        assertTrue(this.userServiceImpl.getUsers().isEmpty());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser2() throws UserNotFoundException, IOException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");
        doThrow(new UsernameNotFoundException("Msg")).when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.deleteUser("janedoe"));
        verify(this.userRepository).findUserByUsername((String) any());
        verify(this.userRepository).deleteById((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUser3() throws UserNotFoundException, IOException {
        // TODO: Complete this test.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access files outside the temporary directory (file '/Users/peredalouis/supportportal/user', permission 'delete').
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("");
        doNothing().when(this.userRepository).deleteById((String) any());
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user);
        this.userServiceImpl.deleteUser("janedoe");
    }

    /**
     * Method under test: {@link UserServiceImpl#resetPassword(String)}
     */
    @Test
    void testResetPassword() throws EmailNotFoundException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        doNothing().when(this.emailService).sendEmail((String) any(), (String) any(), (String) any());
        this.userServiceImpl.resetPassword("jane.doe@example.org");
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.emailService).sendEmail((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#resetPassword(String)}
     */
    @Test
    void testResetPassword2() throws EmailNotFoundException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        doThrow(new UsernameNotFoundException("Msg")).when(this.emailService)
                .sendEmail((String) any(), (String) any(), (String) any());
        assertThrows(UsernameNotFoundException.class, () -> this.userServiceImpl.resetPassword("jane.doe@example.org"));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.emailService).sendEmail((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateProfileImage(String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateProfileImage()
            throws EmailExistException, NotAnImageFileException, UserNotFoundException, UsernameExistException, IOException {
        User user = new User();
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        assertThrows(NotAnImageFileException.class, () -> this.userServiceImpl.updateProfileImage("janedoe",
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository, atLeast(1)).findUserByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateProfileImage(String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateProfileImage2()
            throws EmailExistException, NotAnImageFileException, UserNotFoundException, UsernameExistException, IOException {
        User user = mock(User.class);
        when(user.getId()).thenReturn("foo");
        doNothing().when(user).setAuthorities((String[]) any());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setFirstName((String) any());
        doNothing().when(user).setId((String) any());
        doNothing().when(user).setJoinDate((Date) any());
        doNothing().when(user).setLastLoginDate((Date) any());
        doNothing().when(user).setLastLoginDateDisplay((Date) any());
        doNothing().when(user).setLastName((String) any());
        doNothing().when(user).setNotLocked(anyBoolean());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setProfileImageUrl((String) any());
        doNothing().when(user).setRole((String) any());
        doNothing().when(user).setSignedEndClass(anyBoolean());
        doNothing().when(user).setUsername((String) any());
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId("42");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setJoinDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setLastLoginDateDisplay(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user.setLastName("Doe");
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setRole("Role");
        user.setSignedEndClass(true);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setAuthorities(new String[]{"JaneDoe"});
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setJoinDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setLastLoginDateDisplay(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setLastName("Doe");
        user1.setNotLocked(true);
        user1.setPassword("iloveyou");
        user1.setProfileImageUrl("https://example.org/example");
        user1.setRole("Role");
        user1.setSignedEndClass(true);
        user1.setUsername("janedoe");
        when(this.userRepository.findUserByEmail((String) any())).thenReturn(user);
        when(this.userRepository.findUserByUsername((String) any())).thenReturn(user1);
        assertThrows(EmailExistException.class, () -> this.userServiceImpl.updateProfileImage("janedoe",
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(this.userRepository).findUserByEmail((String) any());
        verify(this.userRepository, atLeast(1)).findUserByUsername((String) any());
        verify(user).getId();
        verify(user).setAuthorities((String[]) any());
        verify(user).setEmail((String) any());
        verify(user).setFirstName((String) any());
        verify(user).setId((String) any());
        verify(user).setJoinDate((Date) any());
        verify(user).setLastLoginDate((Date) any());
        verify(user).setLastLoginDateDisplay((Date) any());
        verify(user).setLastName((String) any());
        verify(user).setNotLocked(anyBoolean());
        verify(user).setPassword((String) any());
        verify(user).setProfileImageUrl((String) any());
        verify(user).setRole((String) any());
        verify(user).setSignedEndClass(anyBoolean());
        verify(user).setUsername((String) any());
    }


}