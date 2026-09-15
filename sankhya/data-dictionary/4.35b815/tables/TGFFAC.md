# TGFFAC — Faces da chapa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FacesChapa | Faces da chapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANGULO | Ângulo | F |  |  | N |
| CODFACE | Face | I |  |  | N |
| LARGURA | Largura da face | F |  |  | N |
| NUNOTA | Nro. Nota | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheDesenhoChapas / [TGFDDC](TGFDDC.md) | FacesChapa / [TGFFAC](TGFFAC.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
