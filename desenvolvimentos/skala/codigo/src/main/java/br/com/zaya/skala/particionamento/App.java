package br.com.zaya.skala.particionamento;

import br.com.zaya.skala.particionamento.model.Carreta;
import br.com.zaya.skala.particionamento.model.ItemPedido;
import br.com.zaya.skala.particionamento.service.ParticionamentoService;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<ItemPedido> itens = Arrays.asList(
                new ItemPedido("PROD-A", "Produto A", 20.0, 100.0, 10.0),
                new ItemPedido("PROD-B", "Produto B", 15.0, 60.0, 6.0)
        );

        ParticionamentoService service = new ParticionamentoService();
        List<Carreta> carretas = service.particionar(itens, 28.0);

        for (Carreta carreta : carretas) {
            System.out.println(carreta);
        }
    }
}

