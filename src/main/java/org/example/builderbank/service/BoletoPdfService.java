package org.example.builderbank.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.example.builderbank.model.Boleto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.awt.Color;

@Service
public class BoletoPdfService {

    public byte[] gerarPdf(Boleto boleto) {

        Document document = new Document(PageSize.A4, 25, 25, 30, 30);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA, 6, Color.DARK_GRAY);
            Font fontValue = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
            Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.BLACK);
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            PdfPTable header = new PdfPTable(new float[]{4, 2f, 6});
            header.setWidthPercentage(100);

            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.BOTTOM);
            logoCell.setBorderWidthBottom(1f);
            logoCell.setPaddingBottom(2f);

            PdfPTable inner = new PdfPTable(new float[]{1, 3});
            inner.setWidthPercentage(100);

            try {
                ClassPathResource res = new ClassPathResource("static/" + boleto.getLogo());
                Image img = Image.getInstance(res.getURL());
                img.scaleToFit(70, 30);

                PdfPCell imgCell = new PdfPCell(img);
                imgCell.setBorder(Rectangle.NO_BORDER);
                inner.addCell(imgCell);

                PdfPCell nameCell = new PdfPCell(new Phrase(boleto.getBanco(), fontBold));
                nameCell.setBorder(Rectangle.NO_BORDER);
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                inner.addCell(nameCell);

            } catch (Exception e) {
                inner.addCell(new PdfPCell(new Phrase(boleto.getBanco(), fontBold)));
            }

            logoCell.addElement(inner);
            header.addCell(logoCell);

            PdfPCell codeCell = new PdfPCell(new Phrase(boleto.getCodigoBanco(), fontHeader));
            codeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            codeCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
            codeCell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
            codeCell.setBorderWidth(1f);
            codeCell.setPaddingBottom(10f);
            header.addCell(codeCell);

            PdfPCell linhaCell = new PdfPCell(
                    new Phrase(boleto.getLinhaDigitavel(),
                            FontFactory.getFont(FontFactory.COURIER_BOLD, 10))
            );
            linhaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            linhaCell.setBorder(Rectangle.BOTTOM);
            linhaCell.setBorderWidthBottom(1f);
            //linhaCell.setPaddingBottom(1f);
            linhaCell.setPaddingTop(10f);
            header.addCell(linhaCell);

            document.add(header);

            PdfPTable table = new PdfPTable(new float[]{2.5f, 2f, 2f, 2f, 2.5f});
            table.setWidthPercentage(100);

            table.addCell(cell("LOCAL DE PAGAMENTO" + "\n",
                    "PAGÁVEL EM QUALQUER BANCO ATÉ O VENCIMENTO",
                    4, fontLabel, fontValue));

            table.addCell(cell("VENCIMENTO" + "\n",
                    boleto.getVencimento().toString(),
                    1, fontLabel, fontBold));

            table.addCell(cell("CEDENTE / BENEFICIÁRIO" + "\n",
                    boleto.getBeneficiario(),
                    3, fontLabel, fontValue));

            table.addCell(cell("CPF/CNPJ" + "\n",
                    boleto.getDocumentoBeneficiario(),
                    1, fontLabel, fontValue));

            table.addCell(cell("AGÊNCIA / CÓDIGO" + "\n",
                    boleto.getAgenciaCodigoBeneficiario(),
                    1, fontLabel, fontValue));

            table.addCell(cell("DATA DO DOCUMENTO" + "\n",
                    boleto.getDataDocumento().toString(),
                    1, fontLabel, fontValue));

            table.addCell(cell("Nº DOCUMENTO" + "\n",
                    boleto.getNumeroDocumento(),
                    1, fontLabel, fontValue));

            table.addCell(cell("ESPÉCIE DOC." + "\n",
                    boleto.getEspecieDoc(),
                    1, fontLabel, fontValue));

            table.addCell(cell("ACEITE" + "\n",
                    "N",
                    1, fontLabel, fontValue));

            table.addCell(cell("NOSSO NÚMERO" + "\n",
                    boleto.getNossoNumero(),
                    1, fontLabel, fontValue));

            // LINHA 4 - INSTRUÇÕES
            PdfPCell inst = cell("INSTRUÇÕES"+ "\n",
                    "- Cobrar multa de 2% após vencimento\n" + "\n" +
                            "- Receber até 10 dias após vencimento\n" + "\n" +
                            "- BuilderBank",
                    4, fontLabel, fontValue);
            inst.setFixedHeight(60f);
            table.addCell(inst);

            table.addCell(cell("(=) VALOR DOCUMENTO" +"\n",
                    "R$ " + String.format("%.2f", boleto.getValor()),
                    1, fontLabel, fontBold));

            // LINHA 5 - SACADO
            PdfPCell sacado = cell("SACADO" +"\n",
                    boleto.getPagador() +
                            "\n"+
                            "\nCPF: " + boleto.getDocumentoPagador() +
                            "\n"+
                            "\nEndereço: " + boleto.getEndePagador() +
                            "\n"+
                            "\nCidade: " + boleto.getCidaPagador(),
                    5, fontLabel, fontValue);

            sacado.setFixedHeight(55f);
            table.addCell(sacado);

            document.add(table);

            document.add(new Paragraph(" "));

            PdfContentByte cb = writer.getDirectContent();

            BarcodeInter25 barcode = new BarcodeInter25();
            barcode.setCode(boleto.getCodigoBarras().replaceAll("\\D", ""));
            barcode.setBarHeight(50f);
            barcode.setX(1.2f);
            barcode.setFont(null);

            Image barcodeImg = barcode.createImageWithBarcode(cb, null, null);
            barcodeImg.setAlignment(Element.ALIGN_LEFT);

            document.add(barcodeImg);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private PdfPCell cell(String label, String value, int colspan, Font fLabel, Font fValue) {

        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setFixedHeight(28f);

        cell.setPaddingTop(2f);
        cell.setPaddingBottom(2f);
        cell.setPaddingLeft(3f);
        cell.setPaddingRight(3f);

        cell.setBorderColor(Color.BLACK);
        cell.setBorderWidth(0.5f);

        Paragraph p = new Paragraph();
        p.setLeading(7f);

        p.add(new Phrase(label + "\n", fLabel));
        p.add(new Phrase(value, fValue));

        cell.addElement(p);

        return cell;
    }
}