# TSERACS — Análise de Risco

- Módulo técnico: [TSE](../modules/TSE.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnaliseCreditoSerasa | Análise de Crédito Serasa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPAP | Cód. Prospect | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHCONSULTA | Data da consulta | H |  |  | N |
| DOCUMENTO | Documento CPF/CNPJ | S |  |  | N |
| DTLIMITE | Data de expiração da consulta | D |  |  | N |
| NUCONSULTA | Nro. Único | I |  |  | N |
| RESPOSTA | Resposta | C |  |  | N |
| URLENVIO | URL Enviada | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnaliseCreditoSerasa / [TSERACS](TSERACS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| InformacaoGeralSerasa / [TSERINGR](TSERINGR.md) | AnaliseCreditoSerasa / [TSERACS](TSERACS.md) | NUCONSULTA->NUCONSULTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
