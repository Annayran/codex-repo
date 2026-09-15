# TGFITE_EXC — Itens de Notas Excluídas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 68
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemNotaExcluida | Item de Nota Excluída | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMS | ALIQICMS | F |  |  | N |
| ALIQIPI | ALIQIPI | F |  |  | N |
| ALIQSTFCPSTANT | Alíquota ST/FCP ST Ant. | F |  |  | N |
| ATUALESTOQUE | ATUALESTOQUE | I |  |  | N |
| BASECALCSTEXTRANOTA | Base de Cálculo ST Extra Nota | F |  |  | N |
| BASEICMS | BASEICMS | F |  |  | N |
| BASEIPI | BASEIPI | F |  |  | N |
| BASESTFCPINTANT | Base ST FCP Interno Anterior | F |  |  | N |
| BASESUBSTIT | BASESUBSTIT | F |  |  | N |
| CNPJFABRICANTE | CNPJ do Fabricante da Mercadoria | S |  |  | N |
| CODAGREGACAO | Cód. de Agregação | S |  |  | N |
| CODBENEFNAUF | Cód. de Benefício Fiscal na UF | S |  |  | N |
| CODCFO | CODCFO | I |  |  | N |
| CODCFPS | CODCFPS | I |  |  | N |
| CODEMP | CODEMP | I |  |  | N |
| CODESPECST | Código Especificador ST | I |  |  | N |
| CODEXEC | CODEXEC | I |  |  | N |
| CODFCI | Código da FCI | S |  |  | N |
| CODLOCALORIG | Local de Origem | I |  |  | N |
| CODMOTDESONERAST | Cód. Mot. Desoneração do ICMS ST | I |  |  | N |
| CODOBSPADRAO | CODOBSPADRAO | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODTRIB | CODTRIB | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | CODVEND | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHEXCLUSAO | Dt. Exclusão | H |  |  | N |
| FATURAR | FATURAR | S |  |  | N |
| GTINNFE | GTINNFE | S |  |  | N |
| GTINTRIBNFE | GTINTRIBNFE | S |  |  | N |
| HOSTNAME | HOSTNAME | S |  |  | N |
| IDDESCPARCERIA | Id de desconto da parceria | F |  |  | N |
| INDESCALA | Indicador de Escala Relevante | S |  |  | N |
| MARGLUCRO | Margem de Lucro | F |  |  | N |
| NT_USERNAME | NT_USERNAME | S |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| NUTAB | NUTAB | I |  |  | N |
| OBSERVACAO | OBSERVACAO | S |  |  | N |
| PENDENTE | PENDENTE | S |  |  | N |
| PERCDESC | PERCDESC | F |  |  | N |
| PERCSTFCPINTANT | % ST FCP Interno Anterior | F |  |  | N |
| PERCUSAQUDECPE | Percentual/Coeficiente sobre Custo Aquisição - PE | F |  |  | N |
| PRODUTONFE | PRODUTONFE | S |  |  | N |
| PROGRAMA | PROGRAMA | S |  |  | N |
| QTDCONFERIDA | QTDCONFERIDA | F |  |  | N |
| QTDENTREGUE | QTDENTREGUE | F |  |  | N |
| QTDNEG | Qtd. Negociada | F |  |  | N |
| QTDTRIBEXPORT | Qtd. Tributação para Exportação | F |  |  | N |
| REDBASEST | Redução da Base ST | F |  |  | N |
| RESERVA | RESERVA | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| STATUSNOTA | STATUSNOTA | S |  |  | N |
| TIPUTILCOM | Tipo de Utilização | I |  |  | N |
| USOPROD | USOPROD | S |  |  | N |
| USUARIO | USUARIO | S |  |  | N |
| VLRCUS | Vlr. Custo | F |  |  | N |
| VLRCUSAQUDECPE | Valor Custo Aquisição - PE | F |  |  | N |
| VLRDESC | VLRDESC | F |  |  | N |
| VLRDESCBONIF | VLRDESCBONIF | F |  |  | N |
| VLRDESCPARCERIA | Valor cupom desconto parceria | F |  |  | N |
| VLRICMS | VLRICMS | F |  |  | N |
| VLRIPI | VLRIPI | F |  |  | N |
| VLRREPRED | VLRREPRED | F |  |  | N |
| VLRSTFCPINTANT | Vlr. ST FCP Interno Anterior | F |  |  | N |
| VLRSUBST | VLRSUBST | F |  |  | N |
| VLRTOT | Vlr. Total | F |  |  | N |
| VLRUNIT | Vlr. Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNotaExcluida / [TGFCAB_EXC](TGFCAB_EXC.md) | ItemNotaExcluida / [TGFITE_EXC](TGFITE_EXC.md) | DHEXCLUSAO->DHEXCLUSAO; NUNOTA->NUNOTA | I |
| ItemNotaExcluida / [TGFITE_EXC](TGFITE_EXC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| ItemNotaExcluida / [TGFITE_EXC](TGFITE_EXC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemNotaExcluida / [TGFITE_EXC](TGFITE_EXC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
