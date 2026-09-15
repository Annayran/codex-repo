# TGFICNF — Contingências por Nota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotaContingencia | Contingências por Nota | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | S |
| CODPARC | Cód.Parceiro | I |  |  | S |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dh. Alter | H |  |  | N |
| DTNEG | Dt.Negociação | D |  |  | S |
| NOMEPARC | Parceiro | S |  |  | S |
| NUCONTING | Nro. Contingência | I |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | S |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| SERIENOTA | Série da nota | S |  |  | S |
| STATUSNOTA | Status da Nota | S |  |  | S |
| TIPMOV | Tipo de Movimento | S |  |  | S |
| TPEMISNFCOM | Emissão NFCom | I |  |  | S |
| TPEMISNFE | Emissão NF-e | I |  |  | S |
| VLRNOTA | Valor da Nota | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contingencia / [TGFCNF](TGFCNF.md) | NotaContingencia / [TGFICNF](TGFICNF.md) | NUCONTING->NUCONTING | I |
| NotaContingencia / [TGFICNF](TGFICNF.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotaContingencia / [TGFICNF](TGFICNF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
