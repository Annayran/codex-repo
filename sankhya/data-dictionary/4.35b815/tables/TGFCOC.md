# TGFCOC — ConvenioConta

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConvenioConta | Convênio Conta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta bancária | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONVENIO | Convênio | I |  |  | N |
| DTALTER | Data alteração | H |  |  | N |
| REMBCO | Último boleto | I |  |  | N |
| REMBCOMAX | Número máximo | I |  |  | N |
| ZERARAUT | Zerar automaticamente | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContaBancaria / [TSICTA](TSICTA.md) | ConvenioConta / [TGFCOC](TGFCOC.md) | CODCTABCOINT->CODCTABCOINT | I |
| ConvenioConta / [TGFCOC](TGFCOC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ConvenioContaParceiro / [TGFCPC](TGFCPC.md) | ConvenioConta / [TGFCOC](TGFCOC.md) | CODCTABCOINT->CODCTABCOINT; CONVENIO->CONVENIO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
