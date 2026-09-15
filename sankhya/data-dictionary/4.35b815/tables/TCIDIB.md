# TCIDIB — Despesa Instalacao Bem

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DespesaInstalacaoBem | Despesa Instalação Bem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Código Usuário | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| NUNOTADESP | N° Único Nota de Despesa | I |  |  | N |
| NURATEIO | Nº de Rateio | I |  |  | N |
| STATUSRATEIO | Status do Rateio | S |  |  | N |
| VLRPENDNOTA | Valor Pendente da Nota | F |  |  | S |
| VLRTOTRATEIO | Valor Total Rateio | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DespesaInstalacaoBem / [TCIDIB](TCIDIB.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTADESP->NUNOTA | I |
| DespesaInstalacaoBem / [TCIDIB](TCIDIB.md) | ItemDespesaInstalacaoBem / [TCIDIBI](TCIDIBI.md) | NURATEIO->NURATEIO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
