# TGFRFT — Resumo Faturamento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ResumoLoteFaturamento | ResumoLoteFaturamento | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DHINCLUSAO | Dh. inclusão | H |  |  | N |
| DHULTTENTA | Dh. Últ. tentativa faturamento | H |  |  | N |
| NUMLOTE | Nro. lote | I |  |  | N |
| NUNOTA | Nro. único documento | I |  |  | N |
| QTDTENTFAT | Qtd. tentativas faturamento | I |  |  | N |
| STATUS | Status faturamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ResumoLoteFaturamento / [TGFRFT](TGFRFT.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
