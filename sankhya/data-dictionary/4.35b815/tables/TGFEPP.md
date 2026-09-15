# TGFEPP — Plano de Manutenção

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanoManutencao | Plano de Manutenção | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Serviço | I |  |  | N |
| CODPTC | Ponto de Controle | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| LIMITE | Limite | F |  |  | N |
| MAXIMO | Máximo | F |  |  | N |
| MINIMO | Mínimo | F |  |  | N |
| MULTIPLO | Múltiplo | S |  |  | N |
| NUMEQ | Núm. Equipamento | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PARADA | Parada | S |  |  | N |
| PERIODICIDADE | Periocidade | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TEMPOESTIMADO | Tempo estimado | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanoManutencao / [TGFEPP](TGFEPP.md) | MaquinasVeiculosEquipamentos / [TGFMEQ](TGFMEQ.md) | NUMEQ->NUMEQ | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | PontoControle / [TGFPTC](TGFPTC.md) | CODPTC->CODPTC | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| PlanoManutencao / [TGFEPP](TGFEPP.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
