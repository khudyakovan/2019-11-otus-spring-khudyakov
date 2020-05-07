package ru.otus.graduation.catalogue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.graduation.catalogue.dto.CheckoutItemDto;
import ru.otus.graduation.catalogue.service.CheckoutEmitterService;
import ru.otus.graduation.config.ApplicationConfig;
import ru.otus.graduation.model.Level;
import ru.otus.graduation.model.Product;
import ru.otus.graduation.model.Proposal;
import ru.otus.graduation.model.Status;
import ru.otus.graduation.repository.LevelRepository;
import ru.otus.graduation.repository.ProductRepository;
import ru.otus.graduation.repository.ProposalRepository;
import ru.otus.graduation.service.HelperService;

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
    private static final String API_PREFIX = "/api/v1";

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

    @PostMapping("/api/v1/checkout")
    public void handleCheckout(@RequestBody CheckoutItemDto dto) {
        System.out.println(dto);
        //Сохранение заявки (корзины)
        Proposal proposal = proposalRepository.save(this.generateNewProposal(dto));
        checkoutEmitterService.emitUser(dto.getUser());
        //Добавление к заявке списка позиций и заказанного количества
        //Отправка данных в систему формирования заказов
        proposal.setProposalDetails(dto.getProposalDetails());
        checkoutEmitterService.emitProposal(proposal);
    }

    private Proposal generateNewProposal(CheckoutItemDto dto) {
        Proposal proposal = new Proposal();
        proposal.setProposalNumber(proposalRepository.findMaxProposalNumber() + 1);
        proposal.setMobilePhone(dto.getUser().getMobilePhone());
        proposal.setCurrentDate(new Date());
        proposal.setStatus(Status.PROPOSAL);
        return proposal;
    }
}
