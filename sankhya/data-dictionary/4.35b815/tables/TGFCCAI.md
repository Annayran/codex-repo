# TGFCCAI — TGFCCAI

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 46
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConferenciaFechamentoCaixa | Conferência de Fechamento de Caixa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCONTAPDV | Conta Bancaria PDV | I |  |  | N |
| CODCONTATES | Conta Tesouraria | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPDV | Cód. PDV | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUALTER | Cód. Usuário Alteração | I |  |  | N |
| DHFECHAMENTO | Dh. Fechamento | H |  |  | N |
| DTABERTURA | Dh. Abertura | H |  |  | N |
| NUCAIXA | Nro. Caixa | I |  |  | N |
| NUCONF | Cod. Conferência de Caixa | I |  |  | N |
| OBSFECHAMENTO | Obs. Fechamento | S |  |  | N |
| SANGAUTO | Sangria Automática | S |  |  | N |
| STATUSCX | Status Caixa | S |  |  | N |
| VLRAPRAZO | Vlr. a Prazo Calculado | F |  |  | N |
| VLRAPRAZOCX | Vlr. a Prazo Digitado | F |  |  | N |
| VLRAVISTA | Vlr. a Vista Calculado | F |  |  | N |
| VLRAVISTACX | Vlr. a Vista Digitado | F |  |  | N |
| VLRCARTAOCRE | Vlr. Cartão Crédito Calculado | F |  |  | N |
| VLRCARTAOCRECX | Vlr. Cartão Crédito Digitado | F |  |  | N |
| VLRCARTAODEB | Vlr. Cartão Débito Calculado | F |  |  | N |
| VLRCARTAODEBCX | Vlr. Cartão de Débito Digitado | F |  |  | N |
| VLRCHEQUEPRE | Vlr. Cheque Pré-Datado Calculado | F |  |  | N |
| VLRCHEQUEPRECX | Vlr. Cheque Pré-Datado Digitado | F |  |  | N |
| VLRCREDITO | Vlr. Crédito Calculado | F |  |  | N |
| VLRCREDITOCX | Vlr. Crédito Digitado | F |  |  | N |
| VLRDEBITO | Vlr. Débito Calculado | F |  |  | N |
| VLRDEBITOCX | Vlr. Débito Digitado | F |  |  | N |
| VLRDEVOLUCAOTROCA | Vlr. Devolução/Troca | F |  |  | N |
| VLRFINAL | Vlr. Final Calculado | F |  |  | N |
| VLRFINALCX | Vlr. Final Digitado | F |  |  | N |
| VLRFINALOCO | Vlr. Soma de Ocorrências | F |  |  | N |
| VLRPARCELADO | Vlr. Parcelado Calculado | F |  |  | N |
| VLRPARCELADOCX | Vlr. Parcelado Digitado | F |  |  | N |
| VLRPIX | Vlr. Pix Calculado | F |  |  | N |
| VLRPIXCX | Vlr. Pix Digitado | F |  |  | N |
| VLRPIXPOS | Vlr. PixPos Calculado | F |  |  | N |
| VLRPIXPOSCX | Vlr. PixPos Digitado | F |  |  | N |
| VLRQUEBRA | Vlr. Quebra de caixa | F |  |  | N |
| VLRSANGRIA | Vlr. Sangria Calculado | F |  |  | N |
| VLRSANGRIAAUT | Vlr. Sangria Automatica | F |  |  | N |
| VLRSANGRIACX | Vlr. Sangria Digitado | F |  |  | N |
| VLRSUPRIMENTO | Vlr. Suprimento Calculado | F |  |  | N |
| VLRSUPRIMENTOCX | Vlr. Suprimento Informado | F |  |  | N |
| VLRTRANSFER | Valor Transferido | F |  |  | N |
| VLRVOUCHER | Vlr. Voucher Calculado | F |  |  | N |
| VLRVOUCHERCX | Vlr. Voucher Digitado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | CadastroPDV / [TGFPDV](TGFPDV.md) | CODPDV->CODPDV | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | ControleCaixa / [TGFCAI](TGFCAI.md) | NUCAIXA->NUCAIXA | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | NUCONF->NUCONF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
