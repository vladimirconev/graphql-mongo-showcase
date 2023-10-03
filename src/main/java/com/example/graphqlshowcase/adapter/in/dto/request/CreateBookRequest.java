package com.example.graphqlshowcase.adapter.in.dto.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record CreateBookRequest(
    @NotNull(message = "ISBN can not be null.") String isbn,
    @NotNull(message = "Title can not be null.") String title,
    @NotNull(message = "Authors can not be null.") @Valid List<AuthorRequest> authors,
    @NotNull(message = "Genre can not be null.")
        @Pattern(
            regexp =
                "SCI_FI"
                    + "|FANTASY"
                    + "|OTHER"
                    + "|MYSTERY"
                    + "|THRILLER"
                    + "|ROMANCE"
                    + "|WESTERNS"
                    + "|DYSTOPIAN"
                    + "|CONTEMPORARY",
            message =
                "Genre must have one of the following values:"
                    + "SCI_FI"
                    + "|FANTASY"
                    + "|OTHER"
                    + "|MYSTERY"
                    + "|THRILLER"
                    + "|ROMANCE"
                    + "|WESTERNS"
                    + "|DYSTOPIAN"
                    + "|CONTEMPORARY.")
        String genre,
    @NotNull(message = "Publisher can not be null.") PublisherRequest publisher)
    implements Serializable {}
