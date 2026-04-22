package org.example.builderbank.model;

import java.time.LocalDate;

public class Boleto {

    private String beneficiario;
    private String agenciaCodigoBeneficiario;
    private String documentoBeneficiario;
    private String pagador;
    private String documentoPagador;
    private String endePagador;
    private String cidaPagador;
    private String banco;
    private String codigoBanco;
    private String logo;
    private Double valor;
    private LocalDate vencimento;
    private LocalDate dataDocumento;
    private String numeroDocumento;
    private String especieDoc;
    private String nossoNumero;
    private String linhaDigitavel;
    private String codigoBarras;


    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public String getCodigoBanco() { return codigoBanco; }
    public void setCodigoBanco(String codigoBanco) { this.codigoBanco = codigoBanco; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public LocalDate getVencimento() { return vencimento; }
    public void setVencimento(LocalDate vencimento) { this.vencimento = vencimento; }

    public LocalDate getDataDocumento() { return dataDocumento; }
    public void setDataDocumento(LocalDate dataDocumento) { this.dataDocumento = dataDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getEspecieDoc() { return especieDoc; }
    public void setEspecieDoc(String especieDoc) { this.especieDoc = especieDoc; }

    public String getNossoNumero() { return nossoNumero; }
    public void setNossoNumero(String nossoNumero) { this.nossoNumero = nossoNumero; }

    public String getAgenciaCodigoBeneficiario() { return agenciaCodigoBeneficiario; }
    public void setAgenciaCodigoBeneficiario(String agenciaCodigoBeneficiario) { this.agenciaCodigoBeneficiario = agenciaCodigoBeneficiario; }

    public String getLinhaDigitavel() { return linhaDigitavel; }
    public void setLinhaDigitavel(String linhaDigitavel) { this.linhaDigitavel = linhaDigitavel; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getBeneficiario() { return beneficiario; }
    public void setBeneficiario(String beneficiario) { this.beneficiario = beneficiario; }

    public String getDocumentoBeneficiario() { return documentoBeneficiario; }
    public void setDocumentoBeneficiario(String documentoBeneficiario) { this.documentoBeneficiario = documentoBeneficiario; }

    public String getPagador() { return pagador; }
    public void setPagador(String pagador) { this.pagador = pagador; }

    public String getDocumentoPagador() { return documentoPagador; }
    public void setDocumentoPagador(String documentoPagador) { this.documentoPagador = documentoPagador; }

    public String getEndePagador(){ return endePagador; }
    public void setEndePagador(String endePagador) { this.endePagador = endePagador; }

    public String getCidaPagador(){ return cidaPagador; }
    public void setCidaPagador(String cidaPagador) { this.cidaPagador = cidaPagador; }

}