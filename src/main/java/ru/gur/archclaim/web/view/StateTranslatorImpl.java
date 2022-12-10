package ru.gur.archclaim.web.view;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.gur.archclaim.entity.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StateTranslatorImpl implements StateTranslator {

    private static final Set<String> STATES = Arrays.stream(State.values())
            .map(State::name)
            .collect(Collectors.toSet());

    @Override
    public Set<State> inputViewToStates(final String input) {
        Assert.hasText(input, "input must not be blank");

        if (STATES.contains(input)) {
            return Set.of(State.valueOf(input));
        }
        return InputToDomainStateMapping.map(input);
    }

    @Override
    public Set<State> inputViewsToStates(final List<String> inputs) {
        Assert.notNull(inputs, "inputs must not be null");

        Set<State> result = new HashSet<>();
        for (String input : inputs) {
            result.addAll(this.inputViewToStates(input));
        }

        return result;
    }
}
