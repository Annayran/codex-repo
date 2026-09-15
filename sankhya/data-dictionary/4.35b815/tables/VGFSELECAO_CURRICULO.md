# VGFSELECAO_CURRICULO — view processo de seleção curriculo

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewProcessoSelecaoCurriculo | Processos de seleção que participou | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSURESPONSAVEL | Cód. Responsável | I |  |  | N |
| DESCRSELECAO | Descrição Seleção | S |  |  | N |
| DTINICIO | Data início | D |  |  | N |
| DTPREVTERMINO | Previsão de término | D |  |  | N |
| NOMEUSU | Nome Responsável | S |  |  | N |
| NUCURRICULO | NUCURRICULO | I |  |  | N |
| NUSELECAO | Número de seleção | I |  |  | N |
| SITSELECAO | Situação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Candidato / [TRSCAN](TRSCAN.md) | ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | NUCURRICULO->NUCURRICULO | I |
| Curriculo / [TFCBCO](TFCBCO.md) | ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | NUCURRICULO->NUCURRICULO | I |
| ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | PontuacaoEtapaCurriculo / [TRSPON](TRSPON.md) | NUCURRICULO->NUCURRICULO; NUSELECAO->NUSELECAO | I |
| ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODUSURESPONSAVEL->CODUSU | I |
| ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | SelecaoCurriculo / [TRSCAN](TRSCAN.md) | NUCURRICULO->NUCURRICULO; NUSELECAO->NUSELECAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
