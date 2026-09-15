# TGAPESI — Itens pesagem

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItensPesagem | Itens pesagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVENFE | Chave NF-e | S |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROC | Procedência | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| NUMNOTATRANS | Nota Transporte | I |  |  | N |
| NUMPESAGEM | Ticket | I |  |  | N |
| NUMPROD | Nro. Nota | I |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| NUNOTADEST | NUNOTADEST | I |  |  | N |
| PESONF | Peso Nota | F |  |  | N |
| PESOROMANEIO | Peso Rateio | F |  |  | N |
| VALTOTAL | Vlr. Total | F |  |  | N |
| VALUNIT | Vlr. Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPesagem / [TGAPESI](TGAPESI.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMNOTATRANS->NUNOTA | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | Procedencia / [TGFPAR](TGFPAR.md) | CODPROC->CODPARC | I |
| ItensPesagem / [TGAPESI](TGAPESI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | ItensPesagem / [TGAPESI](TGAPESI.md) | NUMPESAGEM->NUMPESAGEM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
