package com.apiexample.api.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.apiexample.api.domain.User;
import com.apiexample.api.domain.dto.UserDTO;
import com.apiexample.api.repositories.UserRepository;
import com.apiexample.api.services.exceptions.DataIntegrityViolationException;
import com.apiexample.api.services.exceptions.ObjectNotFoundException;
import com.apiexample.api.services.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

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
    void whenFindByIdThenReturnUserInstance() {
        // Quando repository.findById() for chamado, retorne optionalUser
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenFindByIdThenReturnObjectNotFoundException() {
        // Quando repository.findById() for chamado, lance uma exceção
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try {
            service.findById(ID);
        } catch (Exception e) {
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals("Objeto não encontrado", e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnListOfUsers() {
        Mockito.when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(User.class, response.get(0).getClass());
        Assertions.assertEquals(ID, response.get(0).getId());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getClass(), User.class);
        Assertions.assertEquals(ID, response.getId());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception e) {
            Assertions.assertEquals(DataIntegrityViolationException.class, e.getClass());
        }

    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getClass(), User.class);
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());
    }

    /*
     * @Test
     * void whenUpdateThenReturnDataIntegrityViolationException() {
     * Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUser);
     * 
     * try {
     * optionalUser.get().setId(2);
     * service.create(userDTO);
     * } catch (Exception e) {
     * Assertions.assertEquals(DataIntegrityViolationException.class, e.getClass());
     * }
     * 
     * }
     */

    @Test
    void deleteUserWithSuccess() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
        service.delete(ID);
        Mockito.verify(repository, Mockito.times(1)).deleteById(ID);
    }

    @Test
    void deleteUserWithObjectNotFoundException() {
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try {
            service.delete(ID);
        } catch (Exception e) {
            Assertions.assertEquals("Objeto não encontrado", e.getMessage());
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
        }
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}
