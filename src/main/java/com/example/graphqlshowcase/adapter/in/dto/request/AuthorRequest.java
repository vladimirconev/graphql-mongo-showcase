package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record AuthorRequest(
    @NotNull(message = "first name can not be null.") String firstName,
    @NotNull(message = "last name can not be null. ") String lastName,
    @Email(message = "E-mail is not valid.") String email)
    implements Serializable {}
