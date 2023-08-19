package com.apiexample.api.resources;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.apiexample.api.domain.User;
import com.apiexample.api.domain.dto.UserDTO;
import com.apiexample.api.services.UserService;

@SpringBootTest
public class UserResourceTest {
    @InjectMocks
    UserResource userResource;

    @Mock
    UserService service;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;

    private static final Integer ID = 1;
    private static final String NAME = "Will";
    private static final String EMAIL = "w@gmail.com";
    private static final String PASSWORD = "123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void findById() {
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}
