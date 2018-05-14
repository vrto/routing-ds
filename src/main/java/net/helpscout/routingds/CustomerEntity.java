package net.helpscout.routingds;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "customers")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(name = "first_name")
    String first;

    @Column(name = "last_name")
    String last;

    public CustomerEntity(String first, String last) {
        this.first = first;
        this.last = last;
    }
}
