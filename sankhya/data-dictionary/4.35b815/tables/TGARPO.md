# TGARPO — Controle de Portaria

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControlePortaria | Controle Portaria | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODMOTO | Motorista | I |  |  | N |
| CODPORT | Contador Portaria | I |  |  | N |
| CODUF | UF Veículo | I |  |  | N |
| CODUSU | Usuario | I |  |  | N |
| CODVEIC | Veiculo | I |  |  | N |
| DHCHEGA | Dt. Chegada | H |  |  | N |
| DHENTRA | Dt. Entrada | H |  |  | N |
| DHPESOBRUTO | Dt. Peso Bruto | H |  |  | S |
| DHSAIDA | Dt. Saida | H |  |  | N |
| DHTARA | Dt. Tara | H |  |  | S |
| MODALIDADE | Modalidade do Contrato | S |  |  | N |
| MOTIVO | Motivo | S |  |  | N |
| MOTORISTA | Motorista | S |  |  | N |
| NUCLL | N° Laudo | I |  |  | N |
| NUMPESAGEM | N° Pesagem | I |  |  | N |
| NUMPORT | Senha Portaria | I |  |  | N |
| OBSERV | Observação | S |  |  | N |
| PLACA | Placa | S |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPO | Tipo de Movimentação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratosPortaria / [TGAPOI](TGAPOI.md) | ControlePortaria / [TGARPO](TGARPO.md) | CODPORT->CODPORT | I |
| ControlePortaria / [TGARPO](TGARPO.md) | ContratosPortaria / [TGAPOI](TGAPOI.md) | CODPORT->CODPORT | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTO->CODPARC | I |
| ControlePortaria / [TGARPO](TGARPO.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ControlePortaria / [TGARPO](TGARPO.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIC->CODVEICULO | I |
| LaudoClassificacao / [TGACLL](TGACLL.md) | ControlePortaria / [TGARPO](TGARPO.md) | CODPORT->CODPORT | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | ControlePortaria / [TGARPO](TGARPO.md) | CODPORT->CODPORT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
