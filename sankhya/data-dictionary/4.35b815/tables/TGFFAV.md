# TGFFAV — Foto de Avaria

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FotoAvaria | Fotos de Avaria | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FOTO | Foto da Avaria | B |  |  | N |
| NUAVARIA | Nro da Avaria | I |  |  | N |
| OBSFOTO | Observação da foto | S |  |  | N |
| SEQFOTO | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FotoAvaria / [TGFFAV](TGFFAV.md) | RegistroAvaria / [TGFAVR](TGFAVR.md) | NUAVARIA->NUAVARIA | I |
| RegistroAvaria / [TGFAVR](TGFAVR.md) | FotoAvaria / [TGFFAV](TGFFAV.md) | NUAVARIA->NUAVARIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
