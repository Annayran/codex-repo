# TFXBLC — FOX-TFXBLC

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroBalancasPdv | CadastroBalancasPdv | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativa | S |  |  | N |
| CODBALANCA | Cód.Balança | I |  |  | N |
| CODPDV | Cód.PDV | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| MODELO | Modelo | S |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| PORTA | Porta | S |  |  | N |
| TIMEOUT | TimeOut | I |  |  | N |
| VELOCIDADE | Velocidade | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroBalancasPdv / [TFXBLC](TFXBLC.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
