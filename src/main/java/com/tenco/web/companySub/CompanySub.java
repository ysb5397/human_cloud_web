package com.tenco.web.companySub;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "company_tb")
public class CompanySub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;





}
