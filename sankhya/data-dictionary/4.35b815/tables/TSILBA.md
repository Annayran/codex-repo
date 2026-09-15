# TSILBA — Liberação de acesso por PC

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LiberacaoAcessoPC | Liberação de acesso por PC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSULIBER | Cód. Usuário Liberador | I |  |  | N |
| CODUSUSOLIC | Cód. Usuário Solicitante | I |  |  | N |
| DTLIBER | Data Liberação | D |  |  | N |
| DTSOLIC | Data Solicitação | H |  |  | N |
| DTVALIDADE | Validade Liberação | D |  |  | N |
| IPLOCAL | IP Local | S |  |  | N |
| IPREQUISICAO | IP Requisição | S |  |  | N |
| MACREQUISICAO | MAC | S |  |  | N |
| NOMEPCSOLIC | Hostname Solicitação | S |  |  | N |
| NUSOLIC | Núm. Solicitação | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PCSOLIC | Local Solicitação | S |  |  | N |
| STATUS | Status Solicitação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LiberacaoAcessoPC / [TSILBA](TSILBA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUSOLIC->CODUSU | I |
| LiberacaoAcessoPC / [TSILBA](TSILBA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSULIBER->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
