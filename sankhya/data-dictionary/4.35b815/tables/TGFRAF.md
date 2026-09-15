# TGFRAF — Registro de Alteração do Financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegistroAlteracaoFinanceiro | Registro de Alteração do Financeiro | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPO | Campo Alterado | S |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| NUFIN | Nro. Único Financeiro | I |  |  | N |
| NUREMESSA | Nro. Remessa | I |  |  | N |
| OCORRENCIA | Ocorrência | S |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |
| STATUS | Situação | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| TIPOENVIO | Tipo de Envio | S |  |  | N |
| VALORENVIADO | Valor enviado na remessa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegistroAlteracaoFinanceiro / [TGFRAF](TGFRAF.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| RegistroAlteracaoFinanceiro / [TGFRAF](TGFRAF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
