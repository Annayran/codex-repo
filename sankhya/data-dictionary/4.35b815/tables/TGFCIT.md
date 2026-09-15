# TGFCIT — Classificação dos Itens de Telecomunicação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ClassificacaoItemTelecomunicao | Classificação dos Itens de Telecomunicação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODSERVTELECOM | Cód. Serviço de Telecomunicação | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Servico / [TGFPRO](TGFPRO.md) | ClassificacaoItemTelecomunicao / [TGFCIT](TGFCIT.md) | CODSERVTELECOM->CODSERVTELECOM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
