package org.example.builderbank.service;

import org.example.builderbank.repository.BoletoBuilder;
import org.example.builderbank.model.Boleto;
import java.time.LocalDate;

public class BoletoDirector {
    private BoletoBuilder builder;

    public BoletoDirector(BoletoBuilder builder) {
        this.builder = builder;
    }

    public Boleto construir(String pagador, String cpf, String endereco, String cidade, Double valor, LocalDate vencimento, String nomeBeneficiario, String docBeneficiario, String agenciaBeneficiario) {
        builder.buildDadosBanco();
        builder.buildBeneficiario(nomeBeneficiario, docBeneficiario, agenciaBeneficiario);
        builder.buildPagador(pagador, cpf, endereco, cidade);
        String numDoc = "BB-" + System.currentTimeMillis();
        builder.buildValores(valor, vencimento, numDoc);
        builder.buildIdentificadores();
        return builder.getBoleto();
    }
}
