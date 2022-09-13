package com.garcia.giovane.form.contract.mapper;

import com.garcia.giovane.form.impl.model.UserModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserImplRequestMapper {

    public static UserModel mapFrom(String name, String birthDate, MultipartFile image) {
        final var user = UserModel.builder()
                .name(name)
                .birthDate(UserImplRequestMapper.mapFrom(birthDate))
                .build();
        Optional.ofNullable(image).ifPresent(it -> UserImplRequestMapper.setImage(it, user));
        return user;
    }

    private static LocalDate mapFrom(String date) {
        return Optional.ofNullable(date)
                .map(UserImplRequestMapper::parse)
                .orElse(null);
    }

    private static LocalDate parse(String it) {
        try {
            return LocalDate.parse(it);
        } catch (Exception e) {
            log.error("error parsing date", e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid birth date", e);
        }
    }

    private static void setImage(MultipartFile file, UserModel userModel) {
        try {
            userModel.setImage(file.getBytes());
            userModel.setImageName(file.getOriginalFilename());
        } catch (IOException e) {
            log.error("error setting image data", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "An error ocurred when parsing image", e
            );
        }
    }
}
