# TCSCPA — Comercial Parceiro

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 19
- Instâncias DWF: 2
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ComercialParceiro | Comercial Parceiro | S | N |
| ParceiroInstalacao | Parceiro Instalação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CEP_PARC | Cep | S |  |  | S |
| CGC_CPF | CGC CPF | S |  |  | S |
| CIDADE_PARC | Cidade Parceiro | S |  |  | S |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| COMPLEMENTO_PARC | Complemento | S |  |  | S |
| CONTATO | Contato | S |  |  | N |
| DTINST | Data Instalação | D |  |  | N |
| DTPREVINST | Data Prevista Instalação | D |  |  | N |
| ENDERECO_PARC | Endereço | S |  |  | S |
| ESTADO_PARC | Estado | S |  |  | S |
| GRUPO | Grupo | I |  |  | N |
| NUMCONTRATO | Número do Contrato | I |  |  | N |
| NUMERO_PARC | Número | S |  |  | S |
| OBS1 | Observação | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TELEFONE | Telefone | S |  |  | N |
| TELEFONE_PARC | Telefone Parc. | S |  |  | S |
| TIPO_PARC | Tipo | S |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ComercialGrupo / [TCSCGR](TCSCGR.md) | ComercialParceiro / [TCSCPA](TCSCPA.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| ComercialParceiro / [TCSCPA](TCSCPA.md) | ComercialGrupo / [TCSCGR](TCSCGR.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| ComercialParceiro / [TCSCPA](TCSCPA.md) | ContratoServico / [TCSCSE](TCSCSE.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| ComercialParceiro / [TCSCPA](TCSCPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ComercialParceiro / [TCSCPA](TCSCPA.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ContratoServico / [TCSCSE](TCSCSE.md) | ParceiroInstalacao / [TCSCPA](TCSCPA.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| GrupoInstalacao / [TCSCGR](TCSCGR.md) | ParceiroInstalacao / [TCSCPA](TCSCPA.md) | GRUPO->GRUPO; NUMCONTRATO->NUMCONTRATO | I |
| ParceiroInstalacao / [TCSCPA](TCSCPA.md) | ContratoServico / [TCSCSE](TCSCSE.md) | NUMCONTRATO->NUMCONTRATO; SEQUENCIA->SEQUENCIA | I |
| ParceiroInstalacao / [TCSCPA](TCSCPA.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroInstalacao / [TCSCPA](TCSCPA.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
