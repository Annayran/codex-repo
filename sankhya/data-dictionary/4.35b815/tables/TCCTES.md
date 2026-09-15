# TCCTES — TABLE TCCTES

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TestemunhaCivil | Testemunha Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | CODCC | I |  |  | N |
| CPF | CPF | S |  |  | N |
| NOME | Nome | S |  |  | N |
| RG | Nro. Identidade | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | TestemunhaCivil / [TCCTES](TCCTES.md) | CODCC->CODCC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
