# TGFECO — Pagamentos ECONF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventoEconf | Evento ECONF | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BANDEIRA | Bandeira | S |  |  | N |
| CNPJBENEF | CNPJ Beneficiário | S |  |  | N |
| CNPJINSTFIN | CNPJ da Instituição Financeira | S |  |  | N |
| CNPJPAG | CNPJ do Pagamento | S |  |  | N |
| CODAUTTRANS | Código de Autenticação | S |  |  | N |
| CODEVENTO | CODEVENTO | S |  |  | N |
| DHPAG | Data/Hora do Pagamento | D |  |  | N |
| FORMAPAG | Forma de Pagamento | S |  |  | N |
| MEIOPAG | Meio de Pagamento | S |  |  | N |
| MEIOPAGDESC | Descrição do Outro Meio | S |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| STATUS | Status | S |  |  | N |
| UFBENEF | UF Beneficiário | S |  |  | N |
| UFPAG | UF do Pagamento | S |  |  | N |
| VLRPAG | Valor do Pagamento | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EventoEconf / [TGFECO](TGFECO.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EventoEconf / [TGFECO](TGFECO.md) | EventoNotaFiscalEletronica / [TGFENFE](TGFENFE.md) | CODEVENTO->CODEVENTO; NUNOTA->NUNOTA; SEQUENCIA->SEQEVENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
