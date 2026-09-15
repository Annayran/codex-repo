# TGFLRC — Log de processamento cartão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogProcessamentoCartao | Log de processamento cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DHPROC | Dt. processamento | H |  |  | N |
| NOMEARQ | Nome arquivo | S |  |  | N |
| NULRC | Nro. único | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemLogProcessamentoCartao / [TGFILRC](TGFILRC.md) | LogProcessamentoCartao / [TGFLRC](TGFLRC.md) | NULRC->NULRC | I |
| LogProcessamentoCartao / [TGFLRC](TGFLRC.md) | ItemLogProcessamentoCartao / [TGFILRC](TGFILRC.md) | NULRC->NULRC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
