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
import java.util.Set;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserImplRequestMapper {

    private static final String DOT = ".";
    private static final int NEXT_POSITION = 1;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("png", "jpg", "jpeg", "");

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

    private static Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.isEmpty() || f.contains(DOT))
                .map(f -> f.substring(filename.lastIndexOf(DOT) + NEXT_POSITION))
                .map(String::toLowerCase);
    }

    private static void setImage(MultipartFile file, UserModel userModel) {
        try {
            UserImplRequestMapper.validateFileExtension(file);
            userModel.setImage(file.getBytes());
            userModel.setImageName(file.getOriginalFilename());
        } catch (IOException e) {
            log.error("error setting image data", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "An error ocurred when parsing image", e
            );
        }
    }

    private static void validateFileExtension(MultipartFile file) {
        final var isPresent = UserImplRequestMapper.getFileExtension(file.getOriginalFilename())
                .filter(ALLOWED_EXTENSIONS::contains)
                .isPresent();
        if (!isPresent)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "file extension not acceptable");
    }
}
