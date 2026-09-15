# TIMPVD — Pasta Documentos

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimPastaDocumento | Pasta Documento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PVDCODIGO | Código | I |  |  | N |
| PVDPASTA | Pasta | S |  |  | N |
| PVDPRODUTO | Produto | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimPastaDocumento / [TIMPVD](TIMPVD.md) | VDOPASTA->PVDCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
