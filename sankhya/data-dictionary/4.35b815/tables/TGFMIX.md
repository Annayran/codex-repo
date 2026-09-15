# TGFMIX — Modelo de Importação de XML

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ModeloImportacaoXML | Modelo de Importação de XML | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODNATOP | Natureza da Operação | S |  |  | N |
| NUNOTA | Modelo de notas e pedidos | I |  |  | N |
| PADRAO | Padrão | S |  |  | N |
| PESQCODNATOP | Pesquisa da nat. da operação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | ModeloImportacaoXML / [TGFMIX](TGFMIX.md) | CODEMP->CODEMP | I |
| ModeloImportacaoXML / [TGFMIX](TGFMIX.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
