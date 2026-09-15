# TIMCNF — Tim Modelo Nota

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimModeloNota | Conf. Modelos de Notas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CNFCODCFO | CFOP | I |  |  | N |
| CNFCODCTABCOINT | Conta Bancária | I |  |  | N |
| CNFCODPROD | Serviço | I |  |  | N |
| CNFCONFIRMARNOTA | Confirmar | S |  |  | N |
| CNFDESCRICAO | Descrição | S |  |  | N |
| CNFNUNOTA | Modelo NF. | I |  |  | N |
| CNFPROJCORRBANC | Projeto Corresp. Bancário | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimMFD / [TIMMFD](TIMMFD.md) | TimModeloNota / [TIMCNF](TIMCNF.md) | MFDMODNOTA->CNFNUNOTA | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | CabecalhoNotaModelo / [TGFCAB](TGFCAB.md) | CNFNUNOTA->NUNOTA | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | CNFCODCFO->CODCFO | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | ContaBancaria / [TSICTA](TSICTA.md) | CNFCODCTABCOINT->CODCTABCOINT | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | Produto / [TGFPRO](TGFPRO.md) | CNFCODPROD->CODPROD | I |
| TimModeloNota / [TIMCNF](TIMCNF.md) | TimModeloNotaCriterio / [TIMCNI](TIMCNI.md) | CNFNUNOTA->CNINUNOTA | I |
| TimModeloNotaCriterio / [TIMCNI](TIMCNI.md) | TimModeloNota / [TIMCNF](TIMCNF.md) | CNINUNOTA->CNFNUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
