# TFXSAT — FOX-TFXSAT

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroSatPdv | Cadastro de Sat do Pdv | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativo | S |  |  | N |
| CODATIVACAO | Cód.Ativação | S |  |  | N |
| CODPDV | Cód.PDV | I |  |  | N |
| CODSAT | Cód.Sat | I |  |  | N |
| COMPARTILHA | Compartilha | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| MODELO | Modelo | S |  |  | N |
| NROSERIE | Nro.Série | S |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| PORTA | Porta | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroDePdv / [TFXPDV](TFXPDV.md) | CadastroSatPdv / [TFXSAT](TFXSAT.md) | SATDEFAULT->CODSAT | I |
| CadastroSatPdv / [TFXSAT](TFXSAT.md) | CadastroDePdv / [TFXPDV](TFXPDV.md) | CODPDV->CODPDV | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
