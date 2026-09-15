# TGFNFSE — NF eletrônica de serviços

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ArquivoNFSe | Arquivo NFSe | N | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVEACESSONFSE | Chave de Acesso NFS-e | S |  |  | N |
| CODVERIFNFSE | Código verificação NFS-e | S |  |  | N |
| DHEMISSNFSE | Dt. Emissão NFS-e | H |  |  | N |
| LINKNFSE | LINKNFSE | S |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| XMLCONFCANC | XML Confirmação cancelamento | C |  |  | N |
| XMLDPS | XML do RPS | C |  |  | N |
| XMLINTEGRACAO | XML Integração | C |  |  | N |
| XMLNFSE | XML da NFSe | C |  |  | N |
| XMLPEDCANC | XML Pedido cancelamento | C |  |  | N |
| XMLRPS | XML do RPS | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ArquivoNFSe / [TGFNFSE](TGFNFSE.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
