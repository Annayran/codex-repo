# TCSIE — TABLE TCSIE

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| IndiceEfetividade | Índice de Efetividade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| INDICE1 | Índice 1 | F |  |  | N |
| INDICE2 | Índice 2 | F |  |  | N |
| INDICE3 | Índice 3 | F |  |  | N |
| REFERENCIA | Referência | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| IndiceEfetividade / [TCSIE](TCSIE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| IndiceEfetividade / [TCSIE](TCSIE.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
