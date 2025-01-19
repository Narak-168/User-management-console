package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@Data
public class User {
    private int id = 0;
    private String uuid;
    private String name;
    private String email;
    private boolean isDeleted;

    public User(int id,String name, String email) {
        this.id = ++id;
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.isDeleted = false;
    }
}
