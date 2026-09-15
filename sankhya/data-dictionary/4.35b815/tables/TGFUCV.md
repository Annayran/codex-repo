# TGFUCV — UnidadesCargaVazia

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadesCargaVazia | Unidades Carga Vazia | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUNICV | CODUNICV | I |  |  | N |
| IDUNIDCARGAVAZIA | ID Unidade Carga Vazia | S |  |  | N |
| NUVIAG | NUVIAG | I |  |  | N |
| SEQMDFE | SEQMDFE | I |  |  | N |
| TIPOUNICARGAVAZIA | Tipo Unidade Carga Vazia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadesCargaVazia / [TGFUCV](TGFUCV.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
