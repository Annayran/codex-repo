# TCATEN — Tipos de Encerramentos

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoEncerramento | Tipos de Encerramentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTPENS | Cód.Tipo Encerramento | I |  |  | N |
| NOME | Nome | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoEncerramento / [TCATEN](TCATEN.md) | Curso / [TCACUR](TCACUR.md) | CODTPENS->CODTPENS | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
