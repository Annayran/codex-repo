# TCSOAT — Origem do Atendimento

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OrigemAtendimento | Origem do Atendimento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODOAT | Código | I |  |  | N |
| CODOATPAI | Codigo Pai | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCROAT | Descrição | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| GRAU | Grau | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrdemServico / [TCSOSE](TCSOSE.md) | OrigemAtendimento / [TCSOAT](TCSOAT.md) | CODOAT->CODOAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
