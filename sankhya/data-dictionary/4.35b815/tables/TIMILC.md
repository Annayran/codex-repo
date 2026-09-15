# TIMILC — Imóveis do Lugar Certo

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimImovelLugarCerto | Publicações portal Lugar Certo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ILCCODIGO | Código | I |  |  | N |
| ILCDATA | Data | D |  |  | N |
| ILCIMOVEL | Imóvel | I |  |  | N |
| ILCINICIO | Dt. Início | D |  |  | N |
| ILCQTDDIAS | Qtd. Dias | I |  |  | N |
| ILCUSUARIO | Usuário Alteração | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovel / [TIMIMV](TIMIMV.md) | TimImovelLugarCerto / [TIMILC](TIMILC.md) | IMVCODIGO->ILCIMOVEL | I |
| TimImovelLugarCerto / [TIMILC](TIMILC.md) | Usuario / [TSIUSU](TSIUSU.md) | ILCUSUARIO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
