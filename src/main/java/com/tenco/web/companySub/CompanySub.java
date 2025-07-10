package com.tenco.web.companySub;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "companysub_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "company_id"})
})
@Entity
public class CompanySub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;





}
