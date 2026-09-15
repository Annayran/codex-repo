# TSIACM — Acesso Menu

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcessoMenu | Acesso Menu | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMINHO | Caminho do Menu | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRMENU | Menu | S |  |  | N |
| DHACESSO | Dh. Acesso | H |  |  | N |
| RESOURCEID | Id da Tela | S |  |  | N |
| SEQACESSO | Seq. Acesso | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LogAcessos / [TSIRLG](TSIRLG.md) | AcessoMenu / [TSIACM](TSIACM.md) | CODUSU->CODUSU; SEQACESSO->SEQACESSO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
