package org.example.builderbank.controller;

import org.example.builderbank.service.*;
import org.example.builderbank.dto.PaymentRequest;
import org.example.builderbank.model.Boleto;
import org.example.builderbank.repository.BoletoBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:8080")
public class PaymentController {

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPayment(@RequestBody PaymentRequest request) {

        BoletoBuilder builder;
        switch (request.bankSelection().toUpperCase()) {
            case "NUBANK" -> builder = new NubankBoletoBuilder();
            case "UNIBANCO" -> builder = new UnibancoBoletoBuilder();
            default -> builder = new BradescoBoletoBuilder();
        }

        BoletoDirector director = new BoletoDirector(builder);
        Boleto boletoGerado = director.construir(
                request.customerName(), request.cpfCnpj(), request.custumerEnde(),
                request.custumerCida(), request.value(), request.dueDate(),
                request.cedenteNome(), request.cedenteCpfCnpj(), request.cedenteAgencia()
        );

        BoletoPdfService pdfService = new BoletoPdfService();
        byte[] pdfBytes = pdfService.gerarPdf(boletoGerado);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=boleto.pdf")
                .body(pdfBytes);
    }

    /*
    @PostMapping("/create")
    public ResponseEntity<Boleto> createPayment(@RequestBody PaymentRequest request) {

        BoletoBuilder builder;

        switch (request.bankSelection().toUpperCase()) {
            case "NUBANK" -> builder = new NubankBoletoBuilder();
            case "UNIBANCO" -> builder = new UnibancoBoletoBuilder();
            case "BRADESCO" -> builder = new BradescoBoletoBuilder();
            default -> builder = new BradescoBoletoBuilder();
        }

        BoletoDirector director = new BoletoDirector(builder);

        Boleto boletoGerado = director.construir(
                request.customerName(),
                request.cpfCnpj(),
                request.custumerEnde(),
                request.custumerCida(),
                request.value(),
                request.dueDate(),
                request.cedenteNome(),
                request.cedenteCpfCnpj(),
                request.cedenteAgencia()
        );

        return ResponseEntity.ok(boletoGerado);
    }

     */
}
