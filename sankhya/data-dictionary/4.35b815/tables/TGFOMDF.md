# TGFOMDF — TGFOMDF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OcorrenciasManifesto | Ocorrências do Manifesto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DHOCOR | Dt. e Hora da Ocorrência | H |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| OCORRENCIAS | Ocorrências | C |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OcorrenciasManifesto / [TGFOMDF](TGFOMDF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
