# TGFCER — Certificação da Regra

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CertificacaoRegra | Certificação da Regra | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CHAVE | Chave | I |  |  | N |
| CODREGRA | Regra | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| FILTRAR | Não Visualizar | S |  |  | N |
| RECONTAR | Permite executar recontagem | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CertificacaoRegra / [TGFCER](TGFCER.md) | Regras / [TGFREG](TGFREG.md) | CODREGRA->CODREGRA | I |
| Empresa / [TSIEMP](TSIEMP.md) | CertificacaoRegra / [TGFCER](TGFCER.md) | CODEMP->CHAVE; TIPOREGRA->TIPO | I |
| Usuario / [TSIUSU](TSIUSU.md) | CertificacaoRegra / [TGFCER](TGFCER.md) | CODUSU->CHAVE | I |
| Vendedor / [TGFVEN](TGFVEN.md) | CertificacaoRegra / [TGFCER](TGFCER.md) | CODVEND->CHAVE; TIPOCERTIF->TIPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
