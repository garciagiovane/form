package com.garcia.giovane.form.contract;

import com.garcia.giovane.form.contract.mapper.UserContractResponseMapper;
import com.garcia.giovane.form.contract.mapper.UserImplRequestMapper;
import com.garcia.giovane.form.contract.model.UserContractResponse;
import com.garcia.giovane.form.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users")
public class FormRestController {
    private final UserService userService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserContractResponse save(
            @RequestPart String name,
            @RequestPart(required = false) String birthDate,
            @RequestPart(required = false) MultipartFile image
    ) {
        final var implRequest = UserImplRequestMapper.mapFrom(name, birthDate, image);
        return UserContractResponseMapper.mapFrom(userService.save(implRequest));
    }
}
