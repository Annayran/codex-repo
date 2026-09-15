# TSIKPC — Keep Connection (mantenha logado)

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| KeepConnection | Keep Connection (mantenha logado) | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APARELHO | APARELHO | S |  |  | N |
| APARELHOID | APARELHOID | S |  |  | N |
| APPNAME | APPNAME | S |  |  | N |
| APS | APS | S |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DTVALIDADE | DTVALIDADE | H |  |  | N |
| INTERNO | INTERNO | S |  |  | N |
| KEEPID | KEEPID | S |  |  | N |
| NUKEEP | NUKEEP | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| KeepConnection / [TSIKPC](TSIKPC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
