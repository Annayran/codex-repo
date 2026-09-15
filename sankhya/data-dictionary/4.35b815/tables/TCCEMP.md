# TCCEMP — Empreendimentos

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Empreendimento | Empreendimento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AREATERRENO | Área Terreno | F |  |  | N |
| CAIXAPOSTAL | Caixa Postal | S |  |  | N |
| CARACINCORP | Característica da Incorporação | S |  |  | N |
| CARACTERRENO | Caracteriística do Terreno | S |  |  | N |
| CEP | Cep | S |  |  | N |
| CODBAI | Bairro | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODEMPREE | Cód. Empreendimento | I |  |  | N |
| CODEND | Endereço | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| COMPLEMENTO | Complemento | S |  |  | N |
| DTINICIO | Dt. Inicio | D |  |  | N |
| MATRIMOVEL | Matricula | S |  |  | N |
| NOMEMPREE | Empreendimento | S |  |  | N |
| NUMEND | Nro | S |  |  | N |
| QTDANDAR | Qtd. Andares | I |  |  | N |
| QTDPAVIMENTO | Qtd. Pavimento | I |  |  | N |
| QTDTORRES | Qtd. Torres | I |  |  | N |
| QTDUNIDADES | Qtd. Unidades | I |  |  | N |
| QTDUNIDANDAR | Qtd. Unidades Andar | I |  |  | N |
| REGISCARTO | Nro. Registro Cartório | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | Empreendimento / [TCCEMP](TCCEMP.md) | CODEMPREE->CODEMPREE | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Bairro / [TSIBAI](TSIBAI.md) | CODBAI->CODBAI | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Endereco / [TSIEND](TSIEND.md) | CODEND->CODEND | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| Empreendimento / [TCCEMP](TCCEMP.md) | ResidencialEmpreendimento / [TCCRES](TCCRES.md) | CODEMPREE->CODEMPREE | I |
| ResidencialEmpreendimento / [TCCRES](TCCRES.md) | Empreendimento / [TCCEMP](TCCEMP.md) | CODEMPREE->CODEMPREE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
