package ru.otus.graduation.catalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.graduation.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutItemDto {
    private User user;
    private List<Map<String, Integer>> items;
    private String time;

    public Map<String, Integer> getProposalDetails() {
        Map<String, Integer> total = new HashMap<>();
        this.items.forEach(item -> {
            item.entrySet().forEach(e -> {
                total.put(e.getKey(), e.getValue());
            });
        });
        return total;
    }
}
