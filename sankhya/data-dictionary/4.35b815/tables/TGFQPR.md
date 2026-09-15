# TGFQPR — Quantidade Parceiros por Usuário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| QuantidadeParceiroUsuario | Quantidade Parceiros por Usuário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| QTDPARC | Quantidade Parcelas | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| QuantidadeParceiroUsuario / [TGFQPR](TGFQPR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| QuantidadeParceiroUsuario / [TGFQPR](TGFQPR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Usuario / [TSIUSU](TSIUSU.md) | QuantidadeParceiroUsuario / [TGFQPR](TGFQPR.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
