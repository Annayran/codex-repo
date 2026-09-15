# TGFASN — Anexos do Simples Nacional

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnexoSimplesNacional | Anexos do Simples Nacional | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCR | Descrição do Anexo | S |  |  | N |
| DTINICIO | Dt. Inicio | D |  |  | N |
| NUANEXO | Nro. do Anexo | I |  |  | N |
| TIPOANEXO | Tipo do Anexo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnexoSimplesNacional / [TGFASN](TGFASN.md) | FaixaSimplesNacional / [TGFFSN](TGFFSN.md) | NUANEXO->NUANEXO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
