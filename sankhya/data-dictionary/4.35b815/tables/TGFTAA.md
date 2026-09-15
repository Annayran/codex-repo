# TGFTAA — Termo de Acordo da Apuração

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TermoAcordoApuracao | Termo de Acordo da Apuração | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCFO | CFO | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODIGO | Código Apuração | I |  |  | N |
| PERC | Percentual | F |  |  | N |
| TEXTO | Texto | S |  |  | N |
| TIPAJUSTE | Tipo Ajuste | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ClassificacaoFiscalOperacao / [TGFCFO](TGFCFO.md) | TermoAcordoApuracao / [TGFTAA](TGFTAA.md) | CODCFO->CODCFO | I |
| TermoAcordoApuracao / [TGFTAA](TGFTAA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
