package com.garcia.giovane.form.impl.stub;

import com.garcia.giovane.form.impl.model.UserModel;

import java.time.LocalDate;
import java.time.Month;

public class UserModelStub {
    private UserModelStub() {
    }

    public static UserModel userSaved() {
        return UserModel.builder()
                .id(123L)
                .name("tester")
                .birthDate(LocalDate.of(2022, Month.FEBRUARY, 15))
                .imageName("file.json")
                .image(new byte[]{12, 5})
                .build();
    }

    public static UserModel user() {
        return UserModel.builder()
                .name("tester")
                .birthDate(LocalDate.of(2022, Month.FEBRUARY, 15))
                .imageName("file.json")
                .image(new byte[]{12, 5})
                .build();
    }

    public static UserModel withImage() {
        return UserModel.builder()
                .image(new byte[]{99, 111, 110, 116, 101, 110, 116})
                .name("Tester")
                .birthDate(LocalDate.of(2022, 3, 14))
                .imageName("")
                .id(null)
                .build();
    }
}
