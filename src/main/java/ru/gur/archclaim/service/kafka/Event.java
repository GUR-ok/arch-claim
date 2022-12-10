package ru.gur.archclaim.service.kafka;

public enum Event {

    CLAIM_REJECTED,
    CLAIM_DONE,
    CLAIM_CANCEL,
    CLAIM_IN_PROGRESS,
    CLAIM_WAIT_FOR_SIGN,
    UPDATE_CLAIM
}
