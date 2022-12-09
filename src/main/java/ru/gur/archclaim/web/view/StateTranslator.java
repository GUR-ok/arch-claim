package ru.gur.archclaim.web.view;

import ru.gur.archclaim.entity.State;

import java.util.List;
import java.util.Set;

public interface StateTranslator {

    Set<State> inputViewToStates(String input);

    Set<State> inputViewsToStates(List<String> inputs);
}
