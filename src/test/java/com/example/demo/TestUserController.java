package com.example.demo;


import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private CreateUserRequest getUser(String userName, String passWord, String confirmPassword){
        CreateUserRequest user = new CreateUserRequest();
        user.setUsername(userName);
        user.setPassword(passWord);
        user.setConfirmPassword(confirmPassword);
        return user;
    }


    @Before
    public void init() throws Exception {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", this.userRepository);
        TestUtils.injectObjects(userController, "cartRepository", this.cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", this.bCryptPasswordEncoder);
    }

    @Test
    public void createUserNegative() throws Exception {
        CreateUserRequest createUser = getUser("XYZ", "less7", "less7");
        ResponseEntity<User> response = userController.createUser(createUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createUserPositive() {
        CreateUserRequest createUser = getUser("XYZ", "morethan7", "morethan7");
        ResponseEntity<User> response = userController.createUser(createUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findUser() throws Exception {
        User user = new User();
        user.setUsername("XYZ");
        when(this.userRepository.findByUsername("XYZ")).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.findByUserName("XYZ");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void findById() throws Exception {
        User user = new User();
        user.setUsername("XYZ");
        when(this.userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        ResponseEntity<User> responseEntity = userController.findById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }



}
