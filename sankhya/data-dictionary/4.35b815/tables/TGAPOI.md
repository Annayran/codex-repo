# TGAPOI — Contratos por Portaria

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContratosPortaria | Contratos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVENFE | Chave NF-e | S |  |  | N |
| CODPARC | Parceiro | I |  |  | S |
| CODPORT | CODPORT | I |  |  | N |
| CODPROC | Procedência | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| NUMNOTATRANS | Nota Transporte | I |  |  | N |
| NUMPROD | NF Produtor | I |  |  | N |
| PESONF | Peso NF | F |  |  | N |
| VALTOTAL | Vlr Total | F |  |  | N |
| VALUNIT | Vlr Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratosPortaria / [TGAPOI](TGAPOI.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMNOTATRANS->NUNOTA | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | ControlePortaria / [TGARPO](TGARPO.md) | CODPORT->CODPORT | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | Procedencia / [TGFPAR](TGFPAR.md) | CODPROC->CODPARC | I |
| ContratosPortaria / [TGAPOI](TGAPOI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ControlePortaria / [TGARPO](TGARPO.md) | ContratosPortaria / [TGAPOI](TGAPOI.md) | CODPORT->CODPORT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
