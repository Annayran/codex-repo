# TGFVOA — Volume Alternativo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VolumeAlternativo | Unidades Alternativas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CAMADAS | Camadas | I |  |  | N |
| CODBARRA | Código de Barras | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DESCRDANFE | Descrição de unidade/qtd. para o DANFE | S |  |  | N |
| DESCRUNTRIBEXPORT | Descrição Un. Tributação Exportação | S |  |  | N |
| DIVIDEMULTIPLICA | Operação | S |  |  | N |
| LASTRO | Lastro | I |  |  | N |
| M3 | Metros Cúbicos | F |  |  | N |
| MULTIPVLR | Multiplicador do valor | F |  |  | N |
| OPCAOSEP | Apresentar nas tarefas do WMS | S |  |  | N |
| OPCOESGERAR0220 | Opções para gerar Registros 0220 | S |  |  | N |
| QTDDECIMAISUPF | Qtd Casas Decimais UPF | I |  |  | N |
| QUANTIDADE | Quantidade | F |  |  | N |
| SELECIONADO | Seleção | S |  |  | N |
| TIPCODBARRA | Tipo do código de barras | S |  |  | N |
| TIPGTINNFE | EAN/GTIN Unid.Tributação | I |  |  | N |
| UNDTRIBRECOB | Unidade de Tributação para RECOB | S |  |  | N |
| UNIDSELO | Unid. Selo | S |  |  | N |
| UNIDTRIB | Unid. Tributação | S |  |  | N |
| UNTRIBEXPORTACAO | Un. Tributação Exportação em Toneladas | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoUnidadeAlternativa / [TGFVOAH](TGFVOAH.md) | VolumeAlternativo / [TGFVOA](TGFVOA.md) | CODPROD->CODPROD; CODVOL->CODVOL; CONTROLE->CONTROLE | I |
| Produto / [TGFPRO](TGFPRO.md) | VolumeAlternativo / [TGFVOA](TGFVOA.md) | CODPROD->CODPROD | I |
| RelacionamentoParceiroProduto / [TGFPAP](TGFPAP.md) | VolumeAlternativo / [TGFVOA](TGFVOA.md) | CODPROD->CODPROD; CONTROLE->CONTROLE; UNIDADELOTE->CODVOL | I |
| VolumeAlternativo / [TGFVOA](TGFVOA.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
