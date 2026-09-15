# TGFTERDESC — TerminaisDescarregamento

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
| TerminaisDescarregamento | TerminaisDescarregamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTERDES | CODTERDES | I |  |  | N |
| CODTERMDESC | Cód. Terminal Descarregamento | S |  |  | N |
| NOMETERMDESC | Nome Terminal Descarregamento | S |  |  | N |
| NUVIAG | NUVIAG | I |  |  | N |
| SEQMDFE | SEQMDFE | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | TerminaisDescarregamento / [TGFTERDESC](TGFTERDESC.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
