# TGAPES — Pesagem Armazenagem

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 36
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PesagemMultipla | Pesagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BALANCAPB | Balança Peso Bruto | I |  |  | N |
| BALANCATARA | Balança Tara | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMOTO | Motorista | I |  |  | N |
| CODPORT | Nro. Portaria | I |  |  | N |
| CODUF | Estado | I |  |  | N |
| CODUSUCANCEL | Usuário Cancelamento | I |  |  | N |
| CODUSUPB | Usuário Peso Bruto | I |  |  | N |
| CODUSUTARA | Usuário Tara | I |  |  | N |
| CODVEIC | Veículo | I |  |  | N |
| DHCANCEL | Data Cancelamento | H |  |  | N |
| DHPESOBRUTO | Data Peso Bruto | H |  |  | N |
| DHTARA | Data Tara | H |  |  | N |
| MODALIDADE | Modalidade do Contrato | S |  |  | N |
| MOTIVOCANCEL | Motivo do Cancelamento | S |  |  | N |
| MOTORISTA | Motorista | S |  |  | N |
| NMBALPESBRUTO | Balança Peso Bruto | S |  |  | N |
| NMBALTARA | Balança Tara | S |  |  | N |
| NOMEBALANCA | Nome Balanca | S |  |  | N |
| NOMEUSUCANCEL | Nome Usuário Cancelamento | S |  |  | S |
| NOMEUSUPB | Nome Usuário Peso Bruto | S |  |  | S |
| NOMEUSUTARA | Nome Usuário Tara | S |  |  | S |
| NUMPESAGEM | Ticket | I |  |  | N |
| NUMPORT | Senha Portaria | I |  |  | S |
| NUNOTA | Nro. Nota | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PESAGEMMANUAL | Pesagem Manual | S |  |  | N |
| PESOBRUTO | Peso Bruto | F |  |  | N |
| PESOCORRIGIDO | Peso Corrigido | F |  |  | N |
| PESODESCONTO | Descontos | F |  |  | N |
| PESOLIQ | Peso Líquido | F |  |  | N |
| PLACA | Placa | S |  |  | N |
| RETENCOES | Retenções | F |  |  | N |
| STATUS | Status | S |  |  | N |
| TARA | Tara | F |  |  | N |
| TIPO | Tipo de Movimentação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PesagemMultipla / [TGAPES](TGAPES.md) | ControlePortaria / [TGARPO](TGARPO.md) | CODPORT->CODPORT | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | ItensPesagem / [TGAPESI](TGAPESI.md) | NUMPESAGEM->NUMPESAGEM | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTO->CODPARC | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEIC->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
