# TGFCAN — Nota Cancelada

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 52
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotaCancelada | Nota Cancelada | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALLIVFIS | Atualiza Livros Fiscais | S |  |  | N |
| ATUALLIVISS | Atualiza Livro de ISS | S |  |  | N |
| CHAVECTE | Chave CT-e | S |  |  | N |
| CHAVENFCOM | Chave NFCom | S |  |  | N |
| CHAVENFE | Chave NF-e | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMAQ | Idendifica impressora CF | I |  |  | N |
| CODMODDOC | Modelo do Documento | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| DHCANCPREFNFSE | Data cancelamento prefeitura (NFS-e) | H |  |  | N |
| DHPROTOCCAN | Dt. Protocolo Cancelamento | H |  |  | N |
| DHPROTOCCANCTE | Dh. Protocolo de Autorização de Cancelamento | H |  |  | N |
| DHPROTOCCTE | Dh. Protocolo de Autorização | H |  |  | N |
| DHPROTOCESPONT | Dh. Protoc. Canc. Esp. | H |  |  | N |
| DHPROTOCNFE | Dt. Protocolo NF-e | H |  |  | N |
| DHREGDPEC | Dt. Reg. DPEC | H |  |  | N |
| DTCANC | Dt. de Canc. | D |  |  | N |
| DTFATUR | Dt. de Fatur. | D |  |  | N |
| DTMOV | Dt. de Mov. | D |  |  | N |
| DTNEG | Dt. de Neg. | D |  |  | N |
| IDPONTUACAOPARCERIA | Id de pontuação no parceiro | F |  |  | N |
| MODELONFDES | Modelo da Nota Fiscal | S |  |  | N |
| MOTCANCEL | Motivo | S |  |  | N |
| NATUREZAOPERDES | Natureza de Operação | S |  |  | N |
| NFSECABCEXT | NFS-e Cancelada Extemporaneamente | S |  |  | N |
| NROREDZ | Nro. Redução Z | I |  |  | N |
| NUMCF | Nro. Conhec. de Frete | I |  |  | N |
| NUMNFSE | Nro. NFS-e | S |  |  | N |
| NUMNOTA | Número da Nota | I |  |  | N |
| NUMPROTOCCAN | Nro. Protocolo Cancelamento | S |  |  | N |
| NUMPROTOCCANCTE | Nro. Protocolo de Autorização de Cancelamento | S |  |  | N |
| NUMPROTOCCANFCOM | Nro. Cancelamento Protocolo NFCom | S |  |  | N |
| NUMPROTOCCTE | Nro. Protocolo de Autorização | S |  |  | N |
| NUMPROTOCESPONT | Nro. Protoc. Canc. Esp. | S |  |  | N |
| NUMPROTOCNFCOM | Nro. Protocolo NFCom | S |  |  | N |
| NUMPROTOCNFE | Nro. Protocolo NF-e | S |  |  | N |
| NUMREGDPEC | Nro. Reg. DPEC | S |  |  | N |
| NUNOTA | Número Único da Nota | I |  |  | N |
| NUNOTASUB | Nro. Unico. Nota Substituída | I |  |  | N |
| NUREM | Remessa | I |  |  | N |
| PROTCANCNFSE | Protocolo de cancelamento (NFS-e) | S |  |  | N |
| SERIENFDES | Série NF DES-BH | S |  |  | N |
| SERIENOTA | Série | S |  |  | N |
| STATUSVM | Status Venda Mais | S |  |  | S |
| TIPCANCNFSE | Tipo cancelamento NFS-e | S |  |  | N |
| TPAMBCTE | Tipo de Ambiente de CT-e | S |  |  | N |
| TPAMBNFE | TPAMBNFE | S |  |  | N |
| TPAMBNFSE | Ambiente NFS-e | S |  |  | N |
| TPEMISCTE | Tipo de Emissão de CT-e | I |  |  | N |
| TPEMISNFCOM | Emissão NFCom | I |  |  | N |
| TPEMISNFE | Emissão NF-e | I |  |  | N |
| VLRNOTA | Valor da Nota | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NotaCancelada / [TGFCAN](TGFCAN.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotaCancelada / [TGFCAN](TGFCAN.md) | CabecalhoNotaExcluida / [TGFCAB_EXC](TGFCAB_EXC.md) | NUNOTA->NUNOTA | I |
| NotaCancelada / [TGFCAN](TGFCAN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| NotaCancelada / [TGFCAN](TGFCAN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
