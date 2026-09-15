# TGFINUJ — Tarefa de inutilização de documento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TarefaInutilizacaoDocumento | Tarefa de inutilização de documento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODMODDOC | Modelo do Documento | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dh. Alteração | H |  |  | N |
| DTNEG | Dt. Neg. | H |  |  | N |
| ENTSAI | Entrada/Saída | S |  |  | N |
| JUSTIFICATIVA | Justificativa | S |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| OCORRENCIA | Ocorrência | S |  |  | N |
| SERIENOTA | Série da nota | S |  |  | N |
| STATUSPROC | Situação do processamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TarefaInutilizacaoDocumento / [TGFINUJ](TGFINUJ.md) | CabecalhoNotaExcluida / [TGFCAB_EXC](TGFCAB_EXC.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
