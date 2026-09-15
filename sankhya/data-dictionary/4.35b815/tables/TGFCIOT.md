# TGFCIOT — Código Identificador da Operação de Transporte

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CodigoIdentOperacaoTransporte | Código Identificador da Operação de Transporte | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CIOT | CIOT | S |  |  | N |
| CODPARCCIOT | Emitente do CIOT | I |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQCIOT | Sequência do CIOT | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CodigoIdentOperacaoTransporte / [TGFCIOT](TGFCIOT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCIOT->CODPARC | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | CodigoIdentOperacaoTransporte / [TGFCIOT](TGFCIOT.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
