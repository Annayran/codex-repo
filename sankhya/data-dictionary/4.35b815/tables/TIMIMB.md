# TIMIMB — Imobiliárias

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 26
- Instâncias DWF: 4
- Relações DWF: 24
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImobiliaria | Imobiliárias | S | N |
| TimImobiliariaFilial | Filiais da Imobiliaria | S | N |
| TimImobiliariaMatriz | Matriz das Imobiliarias | S | N |
| TimImobiliariaVenda | Imobiliária Venda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IMBCODCENCUSADM | Administrativo | I |  |  | N |
| IMBCODCENCUSCORBANCARIO | Corresp. Bancário | I |  |  | N |
| IMBCODCENCUSINCORPORA | Incorporadora | I |  |  | N |
| IMBCODCENCUSLOCACAO | Locação | I |  |  | N |
| IMBCODCENCUSLOTEAMENTO | Loteamento | I |  |  | N |
| IMBCODCENCUSREVENDA | Revenda | I |  |  | N |
| IMBCODCENCUSVENDA | Venda | I |  |  | N |
| IMBCODEMP | Empresa | I |  |  | N |
| IMBCODIGO | Código | I |  |  | N |
| IMBCODSERVTXADM | Cód. Serviço Tx. Adm | I |  |  | N |
| IMBCODSERVTXINTER | Cód. Serviço Tx. Intermed. | I |  |  | N |
| IMBCRECI | CRECI | I |  |  | N |
| IMBESTAGIO | Ativo | S |  |  | N |
| IMBEXPIRACRECI | Dt. Expiração | D |  |  | N |
| IMBGERENTE | Gerente | I |  |  | N |
| IMBIDIMOVELWEB | Cód. Imovelweb | S |  |  | N |
| IMBIDWIMOVEIS | Cód. Wimoveis | S |  |  | N |
| IMBIMOBILIARIA | Descrição | S |  |  | N |
| IMBIMOBILIARIAMATRIZ | Imobiliária Matriz | I |  |  | N |
| IMBOBSERVACAO | Observação | C |  |  | N |
| IMBPARCEIRO | Parceiro Imobiliária | I |  |  | N |
| IMBREGIAO | Região | I |  |  | N |
| IMBSERIENOTATXADM | Série Nota Tx. Adm. | S |  |  | N |
| IMBSERIENOTATXINTER | Série Nota Tx. Intermed. | S |  |  | N |
| IMBTXALUPROP | Tx. mínima s/ aluguel proporcional | S |  |  | N |
| IMBTXMINADM | Taxa Mínima Adm | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContratoAdm / [TIMADM](TIMADM.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | ADMIMOBILIARIA->IMBCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimImobiliariaVenda / [TIMIMB](TIMIMB.md) | ADMIMOB2->IMBCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | CLAIMOBILIARIA->IMBCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | LTVIMOBILIARIA->IMBCODIGO | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | CORIMOBILIARIA->IMBCODIGO | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | CentroResultado / [TSICUS](TSICUS.md) | IMBCODCENCUSLOCACAO->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | Empresa / [TSIEMP](TSIEMP.md) | IMBCODEMP->CODEMP | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | Parceiro / [TGFPAR](TGFPAR.md) | IMBPARCEIRO->CODPARC | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | Servico / [TGFPRO](TGFPRO.md) | IMBCODSERVTXINTER->CODPROD | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoAdm / [TSICUS](TSICUS.md) | IMBCODCENCUSADM->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoCorresBanc / [TSICUS](TSICUS.md) | IMBCODCENCUSCORBANCARIO->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoIncorporador / [TSICUS](TSICUS.md) | IMBCODCENCUSINCORPORA->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoLoteamento / [TSICUS](TSICUS.md) | IMBCODCENCUSLOTEAMENTO->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoRevenda / [TSICUS](TSICUS.md) | IMBCODCENCUSREVENDA->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCentroResultadoVenda / [TSICUS](TSICUS.md) | IMBCODCENCUSVENDA->CODCENCUS | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCorretor / [TIMCOR](TIMCOR.md) | IMBGERENTE->CORCODIGO | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimImobiliariaMatriz / [TIMIMB](TIMIMB.md) | IMBIMOBILIARIAMATRIZ->IMBCODIGO | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimServicoTxAdm / [TGFPRO](TGFPRO.md) | IMBCODSERVTXADM->CODPROD | I |
| TimImobiliariaFilial / [TIMIMB](TIMIMB.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | IMBIMOBILIARIAMATRIZ->IMBCODIGO | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | IMBCODIGO->IMBCODIGO | I |
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | IPRIMOBDEV->IMBCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | LOCIMOBILIARIA->IMBCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | LOTIMOBILIARIA->IMBCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | PPVIMOBILIARIA->IMBCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
