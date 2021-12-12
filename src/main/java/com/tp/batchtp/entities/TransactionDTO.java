package com.tp.batchtp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    //Cette classe est un conteneur
    private long idTransaction;
    private long idCompte;
    private double montant;
    private String dateTransaction;

}
