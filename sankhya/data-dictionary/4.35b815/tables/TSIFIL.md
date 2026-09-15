# TSIFIL — Fila de Impressão de Boleto

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FilaImpressaoBoleta | Fila de Impressão de Boleto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data de Alteração | D |  |  | N |
| IMPRESSORA | Impressora | S |  |  | N |
| NOMEFILA | Nome Fila | S |  |  | N |
| REMBCO | Último boleto impresso | I |  |  | N |
| REMFINAL | Último boleto no formulário | I |  |  | N |
| TIPOIMPRESSORA | Tipo Impressora | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FilaImpressaoBoleta / [TSIFIL](TSIFIL.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| FilaImpressaoBoleta / [TSIFIL](TSIFIL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | FilaImpressaoBoleta / [TSIFIL](TSIFIL.md) | NOMEFILA->NOMEFILA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
