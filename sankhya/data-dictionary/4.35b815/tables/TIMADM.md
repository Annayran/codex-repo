# TIMADM — Contrato de Administração do Imóvel

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 71
- Instâncias DWF: 2
- Relações DWF: 17
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimContratoAdm | Contrato de Administração do Imóvel | S | N |
| TimContratoAdmAtivo | TimContratoAdmAtivo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ADMAVLPRECOALUG | Vlr. Avaliação P/ Aluguel | F |  |  | N |
| ADMAVLPRECODEVENDA | Vlr. Avaliação P/ Venda | F |  |  | N |
| ADMCAPTADOR | Captador | I |  |  | N |
| ADMCARENCIALOC | Dias de Carência p/ contrato | I |  |  | N |
| ADMCHAVES | Chaves | S |  |  | N |
| ADMCODUSUINC | Usuário Inclusão | I |  |  | N |
| ADMCOMPIMOVELDIFF | Compensa imóveis diferentes | S |  |  | N |
| ADMCONDICOESDAVENDA | Condições Venda | S |  |  | N |
| ADMCONDLOC | Condições Locação | S |  |  | N |
| ADMCORRESPBANCARIO | Correspondente Bancário | S |  |  | N |
| ADMDATA | Dt. Contrato | D |  |  | N |
| ADMDATACADASTRO | Dh. Inclusão | H |  |  | N |
| ADMDATADESOCUPADO | Dt. Baixa Locação | D |  |  | N |
| ADMDATADESOCUPADOVENDA | Dt. Baixa Venda | D |  |  | N |
| ADMDATAFIMOPCAO | Dt. Fim Exclusividade | D |  |  | N |
| ADMDATALIBERACAO | Dt. Captação | D |  |  | N |
| ADMDESCRIMOVEL | Descrição Imóvel | S |  |  | S |
| ADMDESTAQUE | Destaque Site | S |  |  | N |
| ADMDESTINACAO | Destinação | S |  |  | N |
| ADMDFAGENTEFINANCEIRO | Agente Financeiro | I |  |  | N |
| ADMDFCOMISSAO | Comissão (%) | F |  |  | N |
| ADMDFCOMISSAOADM | Tx. Adm. (%) | F |  |  | N |
| ADMDFFINANCIAMENTO | Financiamento | S |  |  | N |
| ADMDFPOUPANCA | Vlr. Poupança | F |  |  | N |
| ADMDFPRECOALUGUEL | Vlr. Aluguel | F |  |  | N |
| ADMDFPRECODEVENDA | Vlr. Venda | F |  |  | N |
| ADMDFPRESTACAO | Vlr. Prestação Financiamento | F |  |  | N |
| ADMDFQUITADO | Quitado | S |  |  | N |
| ADMDFSALDODEVEDOR | Vlr. saldo devedor | F |  |  | N |
| ADMDFTRANSFERENCIA | Transferência (%) | F |  |  | N |
| ADMDHALTER | Dh. Alteração | H |  |  | N |
| ADMDIASOPCAO | Qtd. dias exclusividade | I |  |  | N |
| ADMDIASREPASSE | Qtd. dias até o repasse | I |  |  | N |
| ADMDIASUTEIS | Tipo Dias | S |  |  | N |
| ADMDIASUTEISCALCPROP | Qtd. Dias Mês Proporcionalizar | S |  |  | N |
| ADMEMPRESANF | Empresa NF | I |  |  | N |
| ADMESTAGIO | Estágio | S |  |  | N |
| ADMEXCLUSIVO | Ven. Exclusiva | S |  |  | N |
| ADMEXCLUSIVOLOC | Loc. Exclusiva | S |  |  | N |
| ADMFINANCIAMENTOBCO | Banco Financiamento | I |  |  | N |
| ADMGARANTIDO | Garantido | S |  |  | N |
| ADMGARANTIDOPOR | Garantido por (meses) | I |  |  | N |
| ADMIMOB2 | Imobiliária Venda | I |  |  | N |
| ADMIMOBILIARIA | Imobiliária Locação | I |  |  | N |
| ADMIMOVEL | Nro. Imóvel | I |  |  | N |
| ADMINDICETXADM | Índice para Tx Adm | I |  |  | N |
| ADMJUSTIFICAINTER | Justificativa Intermediação | S |  |  | N |
| ADMLOCACAO | Imóvel para locação | S |  |  | N |
| ADMNUCHECKLIST | CheckList do contrato | I |  |  | N |
| ADMNUCONTRATO | Nro. Contrato | I |  |  | N |
| ADMOBSVISITAS | Obs. sobre visitas | S |  |  | N |
| ADMPERCREPMULTAPROPRIE | Perc. Multa Repasse Proprietário (%) | I |  |  | N |
| ADMPORQUEDESOCUPOU | Motivo Baixa Locação | S |  |  | N |
| ADMPQDESOCUPOU | Motivo Baixa Venda | S |  |  | N |
| ADMPRAZOADM | Vigência p/ Adm em Meses | I |  |  | N |
| ADMPRAZOLOC | Vigência p/ Locação em Meses | I |  |  | N |
| ADMPRIMEIROALUGUEL | Cobrar Tx. Interm. | S |  |  | N |
| ADMPRIPARPRI | Nro. parcela de início de cobrança | I |  |  | N |
| ADMQTDEGARANTIDA | Garantia Total | S |  |  | N |
| ADMQTDPARPRI | Diluído em (qtd. parc.) | I |  |  | N |
| ADMQUANTIDADEANUNCIO | Qtd. Anúncios | I |  |  | N |
| ADMSEMCHAVES | Sem Chaves | S |  |  | N |
| ADMSEMPLACA | Sem Placa | S |  |  | N |
| ADMSEMTXMINADM | Não cobra adm mínima | S |  |  | N |
| ADMTAXADIFERENTE | Tx. Interm. (%) | F |  |  | N |
| ADMTIPOTXADM | Tipo de Tx Adm | S |  |  | N |
| ADMTXADMVLR | Tx Adm (Valor) | F |  |  | N |
| ADMUSUARIO | Usuário Alteração | I |  |  | N |
| ADMVAISITE | Anuncia no site | S |  |  | N |
| ADMVENDA | Imóvel para venda | S |  |  | N |
| ADMVISITAS | Visitas | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimContratoAdm / [TIMADM](TIMADM.md) | TIMCONTRATOADM->ADMNUCONTRATO | I |
| TimCheckList / [TIMCHK](TIMCHK.md) | TimContratoAdm / [TIMADM](TIMADM.md) | CHKCONTRATOADM->ADMNUCONTRATO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | Banco / [TSIBCO](TSIBCO.md) | ADMDFAGENTEFINANCEIRO->CODBCO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | Empresa / [TSIEMP](TSIEMP.md) | ADMEMPRESANF->CODEMP | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | Moeda / [TSIMOE](TSIMOE.md) | ADMINDICETXADM->CODMOEDA | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimBanco / [TSIBCO](TSIBCO.md) | ADMFINANCIAMENTOBCO->CODBCO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimCheckList / [TIMCHK](TIMCHK.md) | ADMNUCHECKLIST->CHKNUCHECK | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimCorretor / [TIMCOR](TIMCOR.md) | ADMCAPTADOR->CORCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | ADMIMOBILIARIA->IMBCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimImobiliariaVenda / [TIMIMB](TIMIMB.md) | ADMIMOB2->IMBCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimImovel / [TIMIMV](TIMIMV.md) | ADMIMOVEL->IMVCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimProprietario / [TIMIMP](TIMIMP.md) | ADMIMOVEL->IMPIMOVEL; ADMNUCONTRATO->IMPCONTRATO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | ADMUSUARIO->CODUSU | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | ADMCODUSUINC->CODUSU | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimContratoAdm / [TIMADM](TIMADM.md) | CLACONTRATOADM->ADMNUCONTRATO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimContratoAdm / [TIMADM](TIMADM.md) | LOCCONTRATOADM->ADMNUCONTRATO | I |
| TimMFD / [TIMMFD](TIMMFD.md) | TimContratoAdm / [TIMADM](TIMADM.md) | MFDCONTRATOADM->ADMNUCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
