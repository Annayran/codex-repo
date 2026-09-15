# TGFUFP — TGFUFP

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
| UFsPercurso | UFs do percurso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUF | UF | I |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| ORDEM | Ordem Percurso | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| SEQVIAGEM | SEQVIAGEM | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | UFsPercurso / [TGFUFP](TGFUFP.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| UFsPercurso / [TGFUFP](TGFUFP.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
