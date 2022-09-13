package com.garcia.giovane.form.contract.mapper;

import com.garcia.giovane.form.contract.model.UserContractResponse;
import com.garcia.giovane.form.impl.model.UserModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContractResponseMapper {
    public static UserContractResponse mapFrom(UserModel user) {
        return UserContractResponse.builder()
                .name(user.getName())
                .id(user.getId())
                .birthDate(user.getBirthDate())
                .imageName(user.getImageName())
                .build();
    }
}
