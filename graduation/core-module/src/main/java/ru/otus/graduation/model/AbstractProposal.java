package ru.otus.graduation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractProposal {
    private long proposalNumber;
    private String mobilePhone;
    private Date currentDate;
    private Status status;
    private Map<String, Integer> proposalDetails;
}
