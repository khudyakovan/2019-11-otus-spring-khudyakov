package ru.otus.graduation.catalogue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.graduation.catalogue.dto.CheckoutItemDto;
import ru.otus.graduation.catalogue.service.CheckoutEmitterService;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.*;
import ru.otus.graduation.repository.master.LevelRepository;
import ru.otus.graduation.repository.master.ProductRepository;
import ru.otus.graduation.repository.proposal.ProposalRepository;
import ru.otus.graduation.service.HelperService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final ApplicationConfig applicationConfig;
    private final LevelRepository levelRepository;
    private final ProductRepository productRepository;
    private final ProposalRepository proposalRepository;
    private final HelperService helperService;
    private final CheckoutEmitterService checkoutEmitterService;
    private static final String API_PREFIX = "/api/v1/catalog";
    private static final String NEW_USER_ROLE = "ROLE_CUSTOMER";

    @GetMapping(value = {API_PREFIX + "/shop/id"})
    public String getShopId() {
        return applicationConfig.getShopId();
    }

    @GetMapping(value = {API_PREFIX + "/levels"})
    public List<Level> getHierarchyRootLevels() {
        return levelRepository.findByParentId("null");
    }

    @GetMapping(value = {API_PREFIX + "/levels/{id}"})
    public Level getHierarchyLevelById(@PathVariable("id") String id) {
        return levelRepository.findById(id).get();
    }

    @GetMapping(value = {API_PREFIX + "/levels/parent/{parentId}"})
    public List<Level> getHierarchyLevelsByParentId(@PathVariable("parentId") String parentId) {
        return levelRepository.findByParentId(parentId);
    }

    @GetMapping(value = {API_PREFIX + "/goods/{parentId}"})
    public List<Product> getGoodsByHierarchyLevel(@PathVariable("parentId") String parentId) {
        parentId = helperService.getBeginningOfLevel(parentId);
        return productRepository.findByParentIdStartingWith(parentId);
    }

    @GetMapping(value = {API_PREFIX + "/goods/showcase"})
    public List<Product> getShowcase() {
        return productRepository.getRandomProducts();
    }


    @PostMapping(API_PREFIX + "/checkout")
    public void handleCheckout(@RequestBody CheckoutItemDto dto) {
        Proposal proposal = proposalRepository.save(this.createNewProposal(dto));
        //Отправка пользователя
        checkoutEmitterService.emitUser(this.createNewUser(dto, proposal));
        //Сохранение заявки (корзины)
        //Добавление к заявке списка позиций и заказанного количества
        //Отправка данных в систему формирования заказов
        proposal.setDetails(dto.getProposalDetails());
        checkoutEmitterService.emitProposal(proposal);
    }

    private User createNewUser(CheckoutItemDto dto, Proposal proposal){
        String temporaryPassword = "";
        if (!proposal.getMobilePhone().isEmpty()) {
            temporaryPassword = String.format("%s%s",
                    proposal.getMobilePhone().substring(proposal.getMobilePhone().length() - 4),
                    proposal.getProposalNumber());
        }else {
            temporaryPassword = String.format("%s%s",
                    dto.getUser().getEmail().substring(proposal.getMobilePhone().length() - 4),
                    proposal.getProposalNumber());
        }
        User user = dto.getUser();
        user.setPassword(temporaryPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(NEW_USER_ROLE));
        user.setRoles(roles);
        return user;
    }

    private Proposal createNewProposal(CheckoutItemDto dto) {
        Proposal proposal = new Proposal();
        proposal.setEmail(dto.getUser().getEmail());
        proposal.setProposalNumber(proposalRepository.findMaxProposalNumber() + 1);
        proposal.setMobilePhone(dto.getUser().getMobilePhone());
        proposal.setTime(dto.getTime());
        proposal.setCurrentDate(new Date());
        proposal.setStatus(Status.PROPOSAL);
        return proposal;
    }
}
