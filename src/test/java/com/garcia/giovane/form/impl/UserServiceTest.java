package com.garcia.giovane.form.impl;

import com.garcia.giovane.form.impl.repository.UserRepository;
import com.garcia.giovane.form.impl.stub.UserModelStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void shouldReturnSameResponseReceivedFromRepository() {
        final var userToSave = UserModelStub.user();
        final var userSaved = UserModelStub.userSaved();
        Mockito.when(userRepository.save(userToSave)).thenReturn(userSaved);

        final var user = UserModelStub.user();
        final var response = userService.save(user);
        Assertions.assertEquals(userSaved, response);
    }
}