# TGFISS — Tabela de ISS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AliquotaISS | Alíquotas de ISS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBENEFFIS | Número de identificação do Benefício Municipal | S |  |  | N |
| CODCID | Cidade | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLST | Tipo de Serviço | I |  |  | N |
| CODNATOPERISS | Cód. Natureza Oper. ISS (NFS-e) | S |  |  | N |
| CODPROD | Serviço | I |  |  | N |
| CODTRIBISS | Cód. Tributação ISS | I |  |  | N |
| CODTRIBMUNISS | Cod. Trib. Município | S |  |  | N |
| IDALIQ | Código Alíquota | I |  |  | N |
| NAOCALCULA | Não Calcula | S |  |  | N |
| PERCDEDUCAO | Perc. de dedução na base do ISS | F |  |  | N |
| PERCINSS | Percentual de ISS | F |  |  | N |
| TIPODEDUCAO | Tipo de dedução de base do ISS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaISS / [TGFISS](TGFISS.md) | AliquotaISSParceiro / [TGFAIP](TGFAIP.md) | CODCID->CODCID; CODEMP->CODEMP; CODPROD->CODPROD | I |
| AliquotaISS / [TGFISS](TGFISS.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| AliquotaISS / [TGFISS](TGFISS.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AliquotaISS / [TGFISS](TGFISS.md) | ListaServicoSintegra / [TGFLST](TGFLST.md) | CODLST->CODLST | I |
| AliquotaISS / [TGFISS](TGFISS.md) | NaturezaOperacao / [TGFNTP](TGFNTP.md) | CODNATOPERISS->CODNATOPERISS | I |
| AliquotaISS / [TGFISS](TGFISS.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Cidade / [TSICID](TSICID.md) | AliquotaISS / [TGFISS](TGFISS.md) | CODCID->CODCID | I |
| Servico / [TGFPRO](TGFPRO.md) | AliquotaISS / [TGFISS](TGFISS.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
