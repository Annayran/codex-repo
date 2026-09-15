# TGFMOP — Movimento de Ocorrências de Parceiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentoOcorrencia | Movimento de Ocorrências de Parceiros | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODOCC | Ocorrência | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CPF_CNPJ | CPF / CNPJ | S |  |  | N |
| DHMOV | Dh. Movimento | H |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| EVENTOEXEC | Executado | S |  |  | N |
| NUMOV | Nro. Movimento | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentoOcorrencia / [TGFMOP](TGFMOP.md) | OcorrenciaParceiro / [TGFOCC](TGFOCC.md) | CODOCC->CODOCC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
