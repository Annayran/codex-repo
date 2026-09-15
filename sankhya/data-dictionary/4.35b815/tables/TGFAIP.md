# TGFAIP — Tabela de Retenção por Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AliquotaISSParceiro | Tabela de Retenção por Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCID | Cidade | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Serviço | I |  |  | N |
| RETEMISS | Retém ISS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaISS / [TGFISS](TGFISS.md) | AliquotaISSParceiro / [TGFAIP](TGFAIP.md) | CODCID->CODCID; CODEMP->CODEMP; CODPROD->CODPROD | I |
| AliquotaISSParceiro / [TGFAIP](TGFAIP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
