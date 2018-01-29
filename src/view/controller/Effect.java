package view.controller;

import model.Card;

/**
 * Effect <br>
 *
 * An effect is meant enhance user experience or system management by providing a system <br>
 * than can react to a clicked Card in the MainView
 *
 * @author lturpinat
 *
 */
@FunctionalInterface
public interface Effect {
    /**
     * Trigger an action
     * @param card card related to the event
     */
    void trigger(Card card);
}
