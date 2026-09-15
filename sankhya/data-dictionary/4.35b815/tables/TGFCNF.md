# TGFCNF — Contingências

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Contingencia | Contingências NF-e | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHABERTURA | Dh. Abertura | H |  |  | N |
| DHALTER | Dh. Alter | H |  |  | N |
| DHFECHAMENTO | Dh. Fechamento | H |  |  | N |
| JUSTIFICATIVA | Justificativa | S |  |  | N |
| NUCONTING | Nro. Contingência | I |  |  | N |
| TIPOCONTING | Tipo Contingência | S |  |  | N |
| TIPODOC | Tipo de documento | S |  |  | N |
| TPEMISCTE | Emissão CT-e | I |  |  | N |
| TPEMISNFCOM | Emissão NFCom | I |  |  | N |
| TPEMISNFE | Emissão NF-e | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contingencia / [TGFCNF](TGFCNF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Contingencia / [TGFCNF](TGFCNF.md) | NotaContingencia / [TGFICNF](TGFICNF.md) | NUCONTING->NUCONTING | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
