package ru.otus.graduation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements Serializable {
    @Id
    private ObjectId id;
    private String mobilePhone;
    private String firstName;
    private String email;
    private String password;
    private Date registrationDate;
    private List<Role> roles = new ArrayList<>();
}
