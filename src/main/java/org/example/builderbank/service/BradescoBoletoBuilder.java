package org.example.builderbank.service;

import org.example.builderbank.model.Boleto;
import org.example.builderbank.repository.BoletoBuilder;
import java.time.LocalDate;
import java.util.Random;

public class BradescoBoletoBuilder implements BoletoBuilder {
    private Boleto boleto = new Boleto();

    @Override
    public void buildDadosBanco() {
        boleto.setBanco("Bradesco S.A.");
        boleto.setCodigoBanco("237-2");
        boleto.setLogo("logoBank/Bradesco.png");
    }

    @Override
    public void buildBeneficiario(String nome, String documento, String agencia) {
        boleto.setBeneficiario(nome);
        boleto.setDocumentoBeneficiario(documento);
        boleto.setAgenciaCodigoBeneficiario(agencia);
    }

    @Override
    public void buildPagador(String nome, String documento, String endereco, String cidade) {
        boleto.setPagador(nome);
        boleto.setDocumentoPagador(documento);
        boleto.setEndePagador(endereco);
        boleto.setCidaPagador(cidade);
    }

    @Override
    public void buildValores(Double valor, LocalDate vencimento, String numeroDocumento) {
        boleto.setValor(valor != null ? valor : 0.0);
        boleto.setVencimento(vencimento);
        boleto.setDataDocumento(LocalDate.now());
        boleto.setNumeroDocumento(numeroDocumento);
        boleto.setEspecieDoc("DS");
    }

    @Override
    public void buildIdentificadores() {
        Random random = new Random();
        String sequencial = String.format("%011d", random.nextInt(100000000));
        boleto.setNossoNumero("09/" + sequencial + "-P");

        boleto.setLinhaDigitavel("23790.00107 " + sequencial.substring(0, 5) +
                ".678905 12345.678901 1 " + (2000 + random.nextInt(5000)));
        boleto.setCodigoBarras("2379" + String.format("%010d", random.nextInt(1000000000)) + "123456789012345678901234567890");
    }

    @Override
    public Boleto getBoleto() {
        return this.boleto;
    }
}