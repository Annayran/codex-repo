# TGFEVEVEI — Tabela de eventos

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
| EventoVeiculoCadastro | Eventos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | Evento | I |  |  | N |
| CODVEICULO | Veículo | I |  |  | N |
| DTREF | Data Vigor | D |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoVeiculoCadastro / [TGFEVEVEI](TGFEVEVEI.md) | EventoVeiculo / [TSIEVE](TSIEVE.md) | CODEVENTO->CODEVENTO | I |
| Veiculo / [TGFVEI](TGFVEI.md) | EventoVeiculoCadastro / [TGFEVEVEI](TGFEVEVEI.md) | CODVEICULO->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
