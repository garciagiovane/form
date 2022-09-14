package com.garcia.giovane.form.contract;

import com.garcia.giovane.form.impl.UserService;
import com.garcia.giovane.form.impl.stub.UserModelStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(controllers = UserRestController.class)
class UserRestControllerTest {
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
    void shouldReturn422WhenImageExtensionIsNotAllowed() throws Exception {
        final var file = new MockMultipartFile(
                "image", "image.json", MediaType.APPLICATION_JSON_VALUE, "content".getBytes(StandardCharsets.UTF_8)
        );
        final var request = MockMvcRequestBuilders
                .multipart("/users")
                .part(
                        new MockPart("birthDate", "2022-03-14".getBytes(StandardCharsets.UTF_8)),
                        new MockPart("name", "Tester".getBytes(StandardCharsets.UTF_8))
                )
                .file(file);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @ParameterizedTest
    @ValueSource(strings = {".png", ".jpg", ".jpeg", ""})
    void shouldReturn200WhenBodyIsWellFormed(String extension) throws Exception {
        final var originalFilename = extension.isEmpty() ? "" : "image".concat(extension);
        final var file = new MockMultipartFile("image", originalFilename, "application/json", "content".getBytes(StandardCharsets.UTF_8));
        final var request = MockMvcRequestBuilders
                .multipart("/users")
                .part(
                        new MockPart("birthDate", "2022-03-14".getBytes(StandardCharsets.UTF_8)),
                        new MockPart("name", "Tester".getBytes(StandardCharsets.UTF_8))
                )
                .file(file);

        final var user = UserModelStub.withImage(originalFilename);
        Mockito.when(userService.save(user)).thenReturn(user);

        final var json = """
                {
                    "name": "Tester",
                    "birthDate": "2022-03-14",
                    "id": null,
                    "imageName": "${imageName}"
                }
                """.replace("${imageName}", originalFilename);

        final var contentAsString = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(json))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(contentAsString);
    }
}