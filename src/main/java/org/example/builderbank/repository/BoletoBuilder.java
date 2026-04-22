package org.example.builderbank.repository;

import org.example.builderbank.model.Boleto;
import java.time.LocalDate;

public interface BoletoBuilder {
    void buildDadosBanco();
    void buildBeneficiario(String nome, String documento, String agencia);
    void buildPagador(String nome, String documento, String endereco, String cidade);
    void buildValores(Double valor, LocalDate vencimento, String numeroDocumento);
    void buildIdentificadores();
    Boleto getBoleto();
}
