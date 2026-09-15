# TSIPAR — Parâmetros

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParametroSistema | Parâmetros | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABA | Aba | S |  |  | N |
| CHAVE | Chave | S |  |  | N |
| CLASSE | Classe | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DATA | Data | H |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| INTEIRO | Inteiro | I |  |  | N |
| LOGICO | Lógico | S |  |  | N |
| MODULO | Módulo do Sistema | S |  |  | N |
| NUMDEC | Número decimal | F |  |  | N |
| TEXTO | Texto | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ParametroSistema / [TSIPAR](TSIPAR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
