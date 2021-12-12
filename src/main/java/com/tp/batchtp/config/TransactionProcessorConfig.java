package com.tp.batchtp.config;

import com.tp.batchtp.entities.Compte;
import com.tp.batchtp.entities.Transaction;
import com.tp.batchtp.entities.TransactionDTO;
import com.tp.batchtp.repositories.CompteRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Configuration
public class TransactionProcessorConfig implements ItemProcessor<TransactionDTO, Transaction> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    private final CompteRepository compteRepository;

    @Autowired
    public TransactionProcessorConfig(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Transaction process(TransactionDTO transactionDTO) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setIdTransaction(transactionDTO.getIdTransaction());
        transaction.setDateTransaction(dateFormat.parse(transactionDTO.getDateTransaction()));

        compteRepository.findById(transactionDTO.getIdCompte()).ifPresentOrElse(compte -> {
            compte.setSolde(compte.getSolde() - transactionDTO.getMontant());
            transaction.setCompte(compte);
        }, () -> {
            Compte compte = new Compte();
            compte.setIdCompte(transactionDTO.getIdCompte());
            compte.setSolde(10000 - transactionDTO.getMontant());
            compteRepository.save(compte);
            transaction.setCompte(compte);
        });

        transaction.setMontant(transactionDTO.getMontant());
        transaction.setDateDebit(Date.from(Instant.now()));
        return transaction;
    }

}
