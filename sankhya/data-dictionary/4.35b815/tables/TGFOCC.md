# TGFOCC — Ocorrências de Parceiros

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OcorrenciaParceiro | Ocorrências de Parceiros | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALTERGRUAUTOR | Alteração grupo autorização | S |  |  | N |
| BLOQPARC | Bloquear parceiro | S |  |  | N |
| CODOCC | Código | I |  |  | N |
| DESCROCC | Descrição | S |  |  | N |
| IDREGISTRO | Registro | S |  |  | N |
| PADRAO | Padrão | S |  |  | N |
| SITPARC | Situação | I |  |  | N |
| STPEXEC | Nome da Stored Procedure | S |  |  | N |
| TIPOPARC | Tipo de parceiro | S |  |  | N |
| ZERARLIMITECRED | Zerar limite de crédito | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MovimentoOcorrencia / [TGFMOP](TGFMOP.md) | OcorrenciaParceiro / [TGFOCC](TGFOCC.md) | CODOCC->CODOCC | I |
| OcorrenciaParceiro / [TGFOCC](TGFOCC.md) | PalavraOcorrencia / [TGFPOP](TGFPOP.md) | CODOCC->CODOCC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
