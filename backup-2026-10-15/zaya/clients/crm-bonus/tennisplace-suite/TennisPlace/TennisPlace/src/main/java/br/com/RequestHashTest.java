package br.com;

import br.com.crmbonus.clientes.service.RequestHashService;
import br.com.crmbonus.clientes.service.impl.RequestHashServiceImpl;

import java.math.BigDecimal;

public class RequestHashTest {

    public static void main(String[] args) {

        RequestHashService service = new RequestHashServiceImpl();

        BigDecimal nuNota = BigDecimal.valueOf(12345);

        String hash = service.generateHash(nuNota);

        System.out.println("Hash gerado:");
        System.out.println(hash);
        System.out.println("Tamanho: " + hash.length());
    }
}