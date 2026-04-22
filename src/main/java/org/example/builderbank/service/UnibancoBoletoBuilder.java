package org.example.builderbank.service;

import org.example.builderbank.model.Boleto;
import org.example.builderbank.repository.BoletoBuilder;
import java.time.LocalDate;
import java.util.Random;

public class UnibancoBoletoBuilder implements BoletoBuilder {
    private Boleto boleto = new Boleto();

    @Override
    public void buildDadosBanco() {
        boleto.setBanco("Unibanco - União de Bancos Brasileiros");
        boleto.setCodigoBanco("409-0");
        boleto.setLogo("logoBank/Unibanco.png");
    }

    @Override
    public void buildBeneficiario(String nome, String documento, String agencia) {
        boleto.setBeneficiario(nome);
        boleto.setDocumentoBeneficiario(documento);
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
        boleto.setEspecieDoc("DM");
    }

    @Override
    public void buildIdentificadores() {
        Random random = new Random();
        String sequencial = String.format("%011d", random.nextInt(100000000));
        boleto.setNossoNumero(sequencial + "-0");

        boleto.setLinhaDigitavel("40990.00107 " + sequencial.substring(0, 5) +
                ".678905 12345.678901 1 " + (4000 + random.nextInt(5000)));
        boleto.setCodigoBarras("4099" + String.format("%010d", random.nextInt(1000000000)) + "123456789012345678901234567890");
    }

    @Override
    public Boleto getBoleto() {
        return this.boleto;
    }
}
