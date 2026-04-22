package org.example.builderbank.service;

import org.example.builderbank.model.Boleto;
import org.example.builderbank.repository.BoletoBuilder;
import java.time.LocalDate;
import java.util.Random;

public class NubankBoletoBuilder implements BoletoBuilder {
    private Boleto boleto = new Boleto();

    @Override
    public void buildDadosBanco() {
        boleto.setBanco("Nu Pagamentos S.A.");
        boleto.setCodigoBanco("260-4");
        boleto.setLogo("logoBank/Nubank.png");
    }

    @Override
    public void buildBeneficiario(String nome, String documento, String agencia) {
        boleto.setBeneficiario(nome);
        boleto.setBeneficiario(nome);
        boleto.setDocumentoBeneficiario(documento);
        boleto.setAgenciaCodigoBeneficiario(agencia);
    }

    @Override
    public void buildPagador(String nome, String documento, String endereco, String cidade ) {
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
        boleto.setNossoNumero(sequencial + "-0");

        boleto.setLinhaDigitavel("26091.17209 " + sequencial.substring(0, 5) +
                ".589645 52040.300502 3 " + (6000 + random.nextInt(5000)));
        boleto.setCodigoBarras("4099" + String.format("%010d", random.nextInt(1000000000)) + "260936045000029529511720600075");
    }

    @Override
    public Boleto getBoleto() {
        return this.boleto;
    }
}