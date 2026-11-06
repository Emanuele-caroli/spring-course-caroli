package com.example.spring_course;

import com.example.spring_course.model.UserResponse;
import com.example.spring_course.repository.UserRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.DemoService;
import com.example.spring_course.service.dto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApplicationTest {

    @InjectMocks
    private DemoService demoService;

    @Mock
    private UserRepository userRepository;

    @Test
    void calcolaTotale() {
        double prezzo = 10.0;
        int quantita = 3;
        double totale = prezzo * quantita;

        assertEquals(30.0,  totale);
    }

    @Test
    void createUserTest() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("email@example.com");
        userRequestDto.setEta(20);
        userRequestDto.setIndirizzo("MI - BO");
        userRequestDto.setNome("Mario");

        when(userRepository.findByEmail(anyString())).
            thenReturn(new UserEntity());


        UserResponse userRespone = demoService.createUser(userRequestDto);
        assertNotNull(userRespone);
        assertThrows(Exception.class, ()  ->
                demoService.createUser(null));
        //TODO sistemare da requestDto a request

    }
}
