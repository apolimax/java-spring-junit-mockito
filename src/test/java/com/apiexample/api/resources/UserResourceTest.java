package com.apiexample.api.resources;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void whenFindByIdTheReturnSuccess() {
        Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userResource.findById(ID);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());

    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        Mockito.when(service.findAll()).thenReturn(List.of(user));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = userResource.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
        Assertions.assertEquals(UserDTO.class, response.getBody().get(0).getClass());

        Assertions.assertEquals(ID, response.getBody().get(0).getId());
        Assertions.assertEquals(NAME, response.getBody().get(0).getName());
        Assertions.assertEquals(EMAIL, response.getBody().get(0).getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().get(0).getPassword());
    }

    @Test
    void whenCreateReturnCreated() {
        Mockito.when(service.create(Mockito.any())).thenReturn(user);

        ResponseEntity<UserDTO> response = userResource.create(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateReturnSuccess() {
        Mockito.when(service.update(userDTO)).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userResource.update(ID, userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());

        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        Mockito.doNothing().when(service).delete(Mockito.anyInt());

        ResponseEntity<UserDTO> response = userResource.delete(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,  response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).delete(Mockito.anyInt());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}
