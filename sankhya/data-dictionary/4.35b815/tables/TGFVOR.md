# TGFVOR — Registro de Volumes

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegistroVolume | Registro de Volumes | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEND | CODEND | I |  |  | N |
| CODPROD | CODPROD | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| CODVOL | CODVOL | S |  |  | N |
| DHIMPRESSAO | DHIMPRESSAO | H |  |  | N |
| DIGITADO | DIGITADO | S |  |  | N |
| NUNOTA | NUNOTA | I |  |  | N |
| QTDVOL | QTDVOL | I |  |  | N |
| SEQVOL | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RegistroVolume / [TGFVOR](TGFVOR.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| RegistroVolume / [TGFVOR](TGFVOR.md) | EnderecoArmazenagem /  | CODEND->CODEND | I |
| RegistroVolume / [TGFVOR](TGFVOR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RegistroVolume / [TGFVOR](TGFVOR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
