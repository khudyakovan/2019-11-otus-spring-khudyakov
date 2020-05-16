package ru.otus.graduation.catalogue.service;

import ru.otus.graduation.model.Proposal;
import ru.otus.graduation.model.User;

public interface CheckoutEmitterService {
    void emitUser(User user);
    void emitProposal(Proposal proposal);
}
