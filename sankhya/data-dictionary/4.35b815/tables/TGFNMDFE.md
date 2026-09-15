# TGFNMDFE — Notas MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotasMDF | Notas do MDF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | S |
| CODPARC | Cód. Parceiro | I |  |  | S |
| INDREENTREGA | Id. Reentrega | S |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | S |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| ORDEMCARGA | Ordem de Carga | I |  |  | S |
| PESOBRUTO | Peso Bruto | F |  |  | S |
| RAZAOSOCIAL | Descrição da empresa | S |  |  | S |
| RAZAOSOCIALPARC | Descrição do parceiro | S |  |  | S |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| STATUSENVIO | Status de Envio | S |  |  | N |
| VLRNOTA | Valor da nota | F |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | NotasMDF / [TGFNMDFE](TGFNMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| NotasMDF / [TGFNMDFE](TGFNMDFE.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotasMDF / [TGFNMDFE](TGFNMDFE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| NotasMDF / [TGFNMDFE](TGFNMDFE.md) | ProdutoPerigosoNotaMDFE / [TGFPERI](TGFPERI.md) | NUNOTA->NUNOTA; NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
