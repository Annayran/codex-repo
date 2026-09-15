# TGFPTC — Ponto de Controle

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PontoControle | Ponto de Controle | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPTC | Ponto de Controle | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanoManutencao / [TGFEPP](TGFEPP.md) | PontoControle / [TGFPTC](TGFPTC.md) | CODPTC->CODPTC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
