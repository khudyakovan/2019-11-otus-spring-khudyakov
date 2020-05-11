package ru.otus.graduation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.graduation.dto.SaleDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sales")
public class Sale {
    @Id
    private ObjectId id;
    private long saleNumber;
    private long orderNumber;
    private List<SaleDto> details = new ArrayList<>();
    private Date currentDate;
}
