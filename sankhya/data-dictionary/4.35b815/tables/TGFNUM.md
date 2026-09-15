# TGFNUM — Controle de Numeração

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleNumeracao | Controle de Numeração | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ARQUIVO | Arquivo | S |  |  | N |
| AUTOMATICO | Numeração Automática | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMAQ | Máquina | I |  |  | N |
| CODMODDOC | Mod.Doc.Fiscal | I |  |  | N |
| DIASAVISO | Dias para aviso | I |  |  | N |
| DTVAL | Dt. Validade | H |  |  | N |
| IMPNOTA | Caminho/Impressora | S |  |  | N |
| MODNOTAFIS | Modelo | I |  |  | N |
| NOMEARQ | Nome | S |  |  | N |
| QTDAVISO | Qtd. p/Aviso Poucas Notas | I |  |  | N |
| SERIE | Série | S |  |  | N |
| TIPOIMPRESSORA | Tipo Impressora | S |  |  | N |
| TOTITENSNOTA | Quantidade de itens na nota | I |  |  | N |
| TOTSERVNOTA | Quantidade de serviços da nota | I |  |  | N |
| ULTCOD | Último Código | I |  |  | N |
| ULTNOTATALAO | Último número do talão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleNumeracao / [TGFNUM](TGFNUM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControleNumeracao / [TGFNUM](TGFNUM.md) | ModeloNotaFiscal / [TGFMON](TGFMON.md) | MODNOTAFIS->CODMODNF | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ControleNumeracao / [TGFNUM](TGFNUM.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
