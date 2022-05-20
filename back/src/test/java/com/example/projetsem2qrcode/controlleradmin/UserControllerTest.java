package com.example.projetsem2qrcode.controlleradmin;

import com.example.projetsem2qrcode.config.JWTTokenProvider;
import com.example.projetsem2qrcode.exceptions.EmailNotFoundException;
import com.example.projetsem2qrcode.exceptions.UserNotFoundException;
import com.example.projetsem2qrcode.modele.User;
import com.example.projetsem2qrcode.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTTokenProvider jWTTokenProvider;

    @Autowired
    private UserController userController;

    @MockBean
    private UserServiceImpl userService;

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() throws Exception {
        when(this.userService.getUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/list");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() throws Exception {
        when(this.userService.getUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/list");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() throws Exception {
        when(this.userService.getUsers()).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/list");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link UserController#getProfileImage(String, String)}
     */
    @Test
    void testGetProfileImage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/image/{username}/{fileName}", "",
                "Uri Vars", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#getTempProfileImage(String)}
     */
    @Test
    void testGetTempProfileImage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/image/profile/{username}", "",
                "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#getUser(String)}
     */
    @Test
    void testGetUser() throws Exception {
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
        when(this.userService.findUserByUsername((String) any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/find/{username}", "janedoe");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#getUser(String)}
     */
    @Test
    void testGetUser2() throws Exception {
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
        when(this.userService.findUserByUsername((String) any())).thenReturn(user);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/find/{username}", "janedoe");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#getUser(String)}
     */
    @Test
    void testGetUser3() throws Exception {
        when(this.userService.findUserByUsername((String) any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/find/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link UserController#login(User)}
     */
    @Test
    void testLogin() throws Exception {
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
        when(this.userService.findUserByUsername((String) any())).thenReturn(user);
        when(this.jWTTokenProvider.generateJwtToken((com.example.projetsem2qrcode.modele.UserPrincipal) any()))
                .thenReturn("ABC123");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

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
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#register(User)}
     */
    @Test
    void testRegister() throws Exception {
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
        when(this.userService.register((String) any(), (String) any(), (String) any(), (String) any())).thenReturn(user);

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
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#addNewUser(String, String, String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testAddNewUser() throws Exception {
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
        when(this.userService.addNewUser((String) any(), (String) any(), (String) any(), (String) any(), (String) any(),
                anyBoolean(), anyBoolean(), (org.springframework.web.multipart.MultipartFile) any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/add")
                .param("email", "foo")
                .param("firstName", "foo")
                .param("isActive", "foo")
                .param("isNonLocked", "foo")
                .param("lastName", "foo")
                .param("role", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#addNewUser(String, String, String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testAddNewUser2() throws Exception {
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
        when(this.userService.addNewUser((String) any(), (String) any(), (String) any(), (String) any(), (String) any(),
                anyBoolean(), anyBoolean(), (org.springframework.web.multipart.MultipartFile) any())).thenReturn(user);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/add");
        postResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("email", "foo")
                .param("firstName", "foo")
                .param("isActive", "foo")
                .param("isNonLocked", "foo")
                .param("lastName", "foo")
                .param("role", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#updateProfileImage(String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateProfileImage() throws Exception {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.web.multipart.MultipartException: Current request is not a multipart request
        //       at org.springframework.web.method.annotation.RequestParamMethodArgumentResolver.handleMissingValueInternal(RequestParamMethodArgumentResolver.java:210)
        //       at org.springframework.web.method.annotation.RequestParamMethodArgumentResolver.handleMissingValue(RequestParamMethodArgumentResolver.java:193)
        //       at org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.resolveArgument(AbstractNamedValueMethodArgumentResolver.java:114)
        //       at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:122)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:179)
        //       at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:146)
        //       at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)
        //       at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)
        //       at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:199)
        //   In order to prevent perform(RequestBuilder)
        //   from throwing MultipartException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   perform(RequestBuilder).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        Object[] uriVars = new Object[]{};
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/updateProfileImage", uriVars);
        String[] values = new String[]{String.valueOf((Object) null)};
        String[] values1 = new String[]{"foo"};
        MockHttpServletRequestBuilder requestBuilder = postResult.param("profileImage", values).param("username", values1);
        Object[] controllers = new Object[]{this.userController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link UserController#updateUser(String, String, String, String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser() throws Exception {
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
        when(this.userService.updateUser((String) any(), (String) any(), (String) any(), (String) any(), (String) any(),
                (String) any(), anyBoolean(), anyBoolean(), (org.springframework.web.multipart.MultipartFile) any()))
                .thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .param("currentUsername", "foo")
                .param("email", "foo")
                .param("firstName", "foo")
                .param("isActive", "foo")
                .param("isNonLocked", "foo")
                .param("lastName", "foo")
                .param("role", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#updateUser(String, String, String, String, String, String, String, String, org.springframework.web.multipart.MultipartFile)}
     */
    @Test
    void testUpdateUser2() throws Exception {
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
        when(this.userService.updateUser((String) any(), (String) any(), (String) any(), (String) any(), (String) any(),
                (String) any(), anyBoolean(), anyBoolean(), (org.springframework.web.multipart.MultipartFile) any()))
                .thenReturn(user);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/update");
        postResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("currentUsername", "foo")
                .param("email", "foo")
                .param("firstName", "foo")
                .param("isActive", "foo")
                .param("isNonLocked", "foo")
                .param("lastName", "foo")
                .param("role", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"profileImageUrl"
                                        + "\":\"https://example.org/example\",\"lastLoginDate\":0,\"lastLoginDateDisplay\":0,\"joinDate\":0,\"role\":\"Role"
                                        + "\",\"authorities\":[\"JaneDoe\"],\"signedEndClass\":true,\"notLocked\":true}"));
    }

    /**
     * Method under test: {@link UserController#resetPassword(String)}
     */
    @Test
    void testResetPassword() throws Exception {
        doNothing().when(this.userService).resetPassword((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/resetpassword/{email}",
                "jane.doe@example.org");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#resetPassword(String)}
     */
    @Test
    void testResetPassword2() throws Exception {
        doNothing().when(this.userService).resetPassword((String) any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/resetpassword/{email}",
                "jane.doe@example.org");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#resetPassword(String)}
     */
    @Test
    void testResetPassword3() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.CONTINUE)).when(this.userService).resetPassword((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/resetpassword/{email}",
                "jane.doe@example.org");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link UserController#resetPassword(String)}
     */
    @Test
    void testResetPassword4() throws Exception {
        doThrow(new EmailNotFoundException("An error occurred")).when(this.userService).resetPassword((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/resetpassword/{email}",
                "jane.doe@example.org");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(this.userService).deleteUser((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("L'utilisateur a bien été supprimé"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        doNothing().when(this.userService).deleteUser((String) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/user/delete/{username}", "janedoe");
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("L'utilisateur a bien été supprimé"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser3() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.CONTINUE)).when(this.userService).deleteUser((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser4() throws Exception {
        doThrow(new UserNotFoundException("An error occurred")).when(this.userService).deleteUser((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser5() throws Exception {
        doThrow(new IOException("An error occurred")).when(this.userService).deleteUser((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link UserController#login(User)}
     */
    @Test
    void testLogin2() throws Exception {
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
        when(this.userService.findUserByUsername((String) any())).thenReturn(user);
        when(this.jWTTokenProvider.generateJwtToken((com.example.projetsem2qrcode.modele.UserPrincipal) any()))
                .thenReturn("ABC123");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenThrow(new LockedException("?"));

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
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(423));
    }

    /**
     * Method under test: {@link UserController#login(User)}
     */
    @Test
    void testLogin3() throws Exception {
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
        when(this.userService.findUserByUsername((String) any())).thenReturn(user);
        when(this.jWTTokenProvider.generateJwtToken((com.example.projetsem2qrcode.modele.UserPrincipal) any()))
                .thenReturn("ABC123");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));

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
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link UserController#login(User)}
     */
    @Test
    void testLogin4() throws Exception {
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
        when(this.userService.findUserByUsername((String) any())).thenReturn(user);
        when(this.jWTTokenProvider.generateJwtToken((com.example.projetsem2qrcode.modele.UserPrincipal) any()))
                .thenReturn("ABC123");
        when(this.authenticationManager.authenticate((org.springframework.security.core.Authentication) any()))
                .thenThrow(new BadCredentialsException("?"));

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
        String content = (new ObjectMapper()).writeValueAsString(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }




}