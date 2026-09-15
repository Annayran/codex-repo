# TCSOCC — Ocorrências do Contrato

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OcorrenciaContrato | Ocorrências | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTATO | Contato | I |  |  | N |
| CODOCOR | Ocorrência | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUALTREG | Cód. Usuário Alteração | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTREG | Dt. Alteração | H |  |  | N |
| DTOCOR | Dt. ocorrência | D |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Contato / [TGFCTT](TGFCTT.md) | CODCONTATO->CODCONTATO; CODPARC->CODPARC | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Ocorrencia / [TCSOCO](TCSOCO.md) | CODOCOR->CODOCOR | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| OcorrenciaContrato / [TCSOCC](TCSOCC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | OcorrenciaContrato / [TCSOCC](TCSOCC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
