# TGACLL — Laudo de Classificação Produto

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 29
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LaudoClassificacao | Laudo de Classificação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APROVADO | Aprovado | S |  |  | N |
| CODCLT | Padrão de Classificação | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Cód Parceiro | I |  |  | N |
| CODPORT | Senha Portaria | I |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| CODPROJ | Cód. Projeto | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEIC | Veículo | I |  |  | N |
| DHALTER | Dh. Alteração | H |  |  | N |
| DHANALISE | Dh. Término Análise | H |  |  | N |
| MODULO | Modulo | S |  |  | N |
| MOTIVOREPROV | Motivo Reprovação | S |  |  | N |
| NOTAROMANEIO | Número Nota Romaneio | I |  |  | N |
| NUAMOSTRA | Nro. Amostra | I |  |  | N |
| NUCLL | Nro. Laudo | I |  |  | N |
| NULAUDOPAI | Nro. Laudo Pai | I |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| NUMNOTATRANS | Nota de Transporte | I |  |  | N |
| NUMPESAGEM | Numero Pesagem | I |  |  | N |
| NUMPORT | Senha Portaria | I |  |  | S |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| NURAM | Nro. Amostra | I |  |  | N |
| PESOBRUTO | Peso Bruto | F |  |  | N |
| REFERENCIA | Referência do Produto | S |  |  | S |
| SEQUENCIA | Sequência | I |  |  | N |
| STATUS | Status | S |  |  | N |
| TARA | Tara | F |  |  | N |
| TRANSGENIA | Transgenia | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemLaudo / [TGACLR](TGACLR.md) | LaudoClassificacao / [TGACLL](TGACLL.md) | NUCLL->NUCLL | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUMNOTATRANS->NUNOTA | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | ControlePortaria / [TGARPO](TGARPO.md) | CODPORT->CODPORT | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | ItemLaudo / [TGACLR](TGACLR.md) | NUCLL->NUCLL | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | PadraoClassificacao / [TGACLT](TGACLT.md) | CODCLT->CODCLT | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIC->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
