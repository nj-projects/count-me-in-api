package me.nolanjames.countmeinapi.event;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static me.nolanjames.countmeinapi.event.EventValidation.*;

public record EventRequest(
        @NotNull(message = EVENT_NAME_CANNOT_BE_EMPTY)
        @NotEmpty(message = EVENT_NAME_CANNOT_BE_EMPTY)
        String name,

        @NotNull(message = EVENT_DESCRIPTION_CANNOT_BE_EMPTY)
        @NotEmpty(message = EVENT_DESCRIPTION_CANNOT_BE_EMPTY)
        String description,

        @NotNull(message = EVENT_DATE_CANNOT_BE_EMPTY)
        @NotEmpty(message = EVENT_DATE_CANNOT_BE_EMPTY)
        String date,
        String imageUrl
) {
}
