package com.garcia.giovane.form.contract;

import com.garcia.giovane.form.contract.mapper.UserContractResponseMapper;
import com.garcia.giovane.form.contract.mapper.UserImplRequestMapper;
import com.garcia.giovane.form.contract.model.UserContractResponse;
import com.garcia.giovane.form.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "User controller")
@RequestMapping(path = "users")
public class UserRestController {
    private final UserService userService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Save new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Save new user",
                            content = @Content(schema = @Schema(implementation = UserContractResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "name not informed",
                            content = @Content(schema = @Schema(example = """
                                    {
                                        "timestamp": "2022-09-14T00:13:45.044+00:00",
                                        "status": 400,
                                        "error": "Bad Request",
                                        "message": "name not informed",
                                        "path": "/users"
                                    }
                                    """))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid birth date",
                            content = @Content(schema = @Schema(example = """
                                    {
                                        "timestamp": "2022-09-14T00:13:45.044+00:00",
                                        "status": 422,
                                        "error": "Unprocessable Entity",
                                        "message": "Invalid birth date",
                                        "path": "/users"
                                    }
                                    """))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(schema = @Schema(example = """
                                    {
                                        "timestamp": "2022-09-14T00:13:45.044+00:00",
                                        "status": 500,
                                        "error": "Internal server error",
                                        "message": "Internal server error",
                                        "path": "/users"
                                    }
                                    """))
                    )
            }
    )
    public UserContractResponse save(
            @RequestPart String name,
            @RequestPart(required = false) String birthDate,
            @RequestPart(required = false) MultipartFile image
    ) {
        final var implRequest = UserImplRequestMapper.mapFrom(name, birthDate, image);
        return UserContractResponseMapper.mapFrom(userService.save(implRequest));
    }
}
