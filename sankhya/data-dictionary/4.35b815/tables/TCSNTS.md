# TCSNTS — Natureza X Serviço

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NaturezaServico | Natureza X Serviço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Cód. Natureza | I |  |  | N |
| CODSERV | Serviço | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NaturezaServico / [TCSNTS](TCSNTS.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| NaturezaServico / [TCSNTS](TCSNTS.md) | Servico / [TGFPRO](TGFPRO.md) | CODSERV->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
