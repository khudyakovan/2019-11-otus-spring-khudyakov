package ru.otus.graduation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractProposal implements Serializable {
    private long proposalNumber;
    private String mobilePhone;
    private String email;
    private Date currentDate;
    private String time;
    private Status status;
    private Map<String, Integer> details = new HashMap<>();
}
