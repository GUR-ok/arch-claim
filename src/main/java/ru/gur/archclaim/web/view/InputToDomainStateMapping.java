package ru.gur.archclaim.web.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import ru.gur.archclaim.entity.State;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum InputToDomainStateMapping {

    ALL(Set.of(State.values())),
    NOT_COMPLETED(State.getProcessingStates()),
    COMPLETED(State.getCompleted());

    private final Set<State> mappedStates;

    private static final Map<String, InputToDomainStateMapping> NAME_TO_VALUES =
            Arrays.stream(InputToDomainStateMapping.values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    public static Set<State> map(final String view) {
        Assert.hasText(view, "view must not be blank");

        return Optional.ofNullable(NAME_TO_VALUES.get(view))
                .map(InputToDomainStateMapping::getMappedStates)
                .orElse(Collections.emptySet());
    }
}
