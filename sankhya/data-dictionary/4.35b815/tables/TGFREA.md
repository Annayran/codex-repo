# TGFREA — Regime Especial de Apuração de ICMS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegimeEspecialIcms | Regime Especial de Apuração de ICMS | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQEXT | Alíquota Externa | F |  |  | N |
| ALIQINT | Alíquota Interna | F |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Data da Alteração | H |  |  | N |
| DHCAD | Data | H |  |  | N |
| NUMITEMREA | Número do item | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | RegimeEspecialIcms / [TGFREA](TGFREA.md) | NUMITEMREA->NUMITEMREA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
