package com.garcia.giovane.form.contract;

import com.garcia.giovane.form.impl.UserService;
import com.garcia.giovane.form.impl.stub.UserModelStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(controllers = FormRestController.class)
class FormRestControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnErrorWhenNameIsNotInformed() throws Exception {
        final var request = MockMvcRequestBuilders
                .multipart("/users")
                .part(new MockPart("birthDate", "2022-03-14".getBytes(StandardCharsets.UTF_8)));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturn422WhenBirthDateIsMalormed() throws Exception {
        final var request = MockMvcRequestBuilders
                .multipart("/users")
                .part(
                        new MockPart("birthDate", "2022-03-145".getBytes(StandardCharsets.UTF_8)),
                        new MockPart("name", "Tester".getBytes(StandardCharsets.UTF_8))
                );

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    void shouldReturn200WhenBodyIsWellFormed() throws Exception {
        final var file = new MockMultipartFile("image", "content".getBytes(StandardCharsets.UTF_8));
        final var request = MockMvcRequestBuilders
                .multipart("/users")
                .part(
                        new MockPart("birthDate", "2022-03-14".getBytes(StandardCharsets.UTF_8)),
                        new MockPart("name", "Tester".getBytes(StandardCharsets.UTF_8))
                )
                .file(file);

        final var user = UserModelStub.withImage();
        Mockito.when(userService.save(user)).thenReturn(user);

        final var json = """
                {
                    "name": "Tester",
                    "birthDate": "2022-03-14",
                    "id": null,
                    "imageName": ""
                }
                """;

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(json));
    }
}