# TGFNPA — Notas Pendentes de Autorização

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotaPendenteAutorizacao | Notas Pendentes de Autorização | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Parceiro | I |  |  | S |
| CODUSU | Cód. Usuário | I |  |  | N |
| COMPENSAR | Compensar | S |  |  | N |
| DHINCLUSAO | Dt. Inclusão | H |  |  | N |
| DHULTTENTA | Dt. Última tentativa envio | H |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| QTDENVIO | Qtd. Envio | I |  |  | N |
| TIPO | Tipo | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NotaPendenteAutorizacao / [TGFNPA](TGFNPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| NotaPendenteAutorizacao / [TGFNPA](TGFNPA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
