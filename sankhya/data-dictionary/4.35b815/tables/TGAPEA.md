# TGAPEA — Pesagem Avulsa

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 33
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PesagemAvulsa | Pesagem Avulsa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód Empresa | I |  |  | N |
| CODMOTO | Motorista | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPESAV | Ticket | I |  |  | N |
| CODPROD | Produto | S |  |  | N |
| CODUSUCANCEL | Usuário Cancelamento | I |  |  | N |
| CODUSUPB | Usuario Peso Bruto | I |  |  | N |
| CODUSUTARA | Usuario Tara | I |  |  | N |
| CODVEI | Veículo | S |  |  | N |
| CPF | CPF | S |  |  | N |
| DHCANCEL | Data Cancelamento | H |  |  | N |
| DHPEFIM | Data Tara | H |  |  | N |
| DHPEINIC | Data Movimento | H |  |  | N |
| DHPESAGEM | Data Peso Bruto | H |  |  | N |
| KMFINAL | Km Final | S |  |  | N |
| KMINICIAL | Km Inicial | S |  |  | N |
| KMPERCORRIDO | Km Percorrido | S |  |  | N |
| MOTORIS | Motorista | S |  |  | N |
| NMBALPESBRUTO | Balança Peso Bruto | S |  |  | N |
| NMBALTARA | Balança Tara | S |  |  | N |
| NOMEBALANCA | Balança | S |  |  | N |
| NOMEUSUPB | Nome Usuário Peso Bruto | S |  |  | S |
| NOMEUSUTARA | Nome Usuário Tara | S |  |  | S |
| OBSERVACAO | Observação | S |  |  | N |
| PESAGEMMANUAL | Pesagem Manual | S |  |  | N |
| PESFIN | Peso Liquido | F |  |  | N |
| PESINI | Peso Bruto | F |  |  | N |
| PESODECLARADO | Peso Declarado | S |  |  | N |
| PLACA | Placa | S |  |  | N |
| PRODUTO | Produto | S |  |  | N |
| STATUS | Status da Pesagem | S |  |  | N |
| TARA | Tara | F |  |  | N |
| TIPO | Tipo de Pesagem | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Motorista / [TGFPAR](TGFPAR.md) | CODMOTO->CODPARC | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| PesagemAvulsa / [TGAPEA](TGAPEA.md) | Veiculo / [TGFVEI](TGFVEI.md) | CODVEI->CODVEICULO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
