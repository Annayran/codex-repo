# TIMLTE — Lotes

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 51
- Instâncias DWF: 1
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLote | Lote | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LTEAGUA | Água | S |  |  | N |
| LTEAREA | Área (m²) | F |  |  | N |
| LTEBAIRRO | Bairro | I |  |  | N |
| LTEBENFEITORIA | Benfeitoria | S |  |  | N |
| LTEBENFEITORIAS | Benfeitorias | S |  |  | N |
| LTECARTORIO | Cartório | I |  |  | N |
| LTECEP | C.E.P | S |  |  | N |
| LTECIDADE | Cidade | I |  |  | N |
| LTECNFCHANFRO | Conf. Chanfro | S |  |  | N |
| LTECNFDIREITA | Conf. Direita | S |  |  | N |
| LTECNFESQUERDA | Conf. Esquerda | S |  |  | N |
| LTECNFFRENTE | Conf. Frente | S |  |  | N |
| LTECNFFUNDO | Conf. Fundo | S |  |  | N |
| LTECNFVARIANTE | Conf. Variante | S |  |  | N |
| LTECODEND | Endereço | I |  |  | N |
| LTECODIGO | Código | I |  |  | N |
| LTECOMPLEMENTO | Complemento | S |  |  | N |
| LTEDATADECADASTRO | Dt. Cadastro | H |  |  | N |
| LTEDATAREGISTRO | Dt. Registro | D |  |  | N |
| LTEDESCPARCELA | Vlr. Desc. Parcelas | F |  |  | N |
| LTEDESCRICAO | Descrição | S |  |  | S |
| LTEDHALTERACAO | Dh. Alteração | H |  |  | N |
| LTEENERGIA | Energia | S |  |  | N |
| LTEESQUINA | Esquina | S |  |  | N |
| LTEFOLHA | Folha | S |  |  | N |
| LTELIVRO | Livro | S |  |  | N |
| LTELOTE | Lote | S |  |  | N |
| LTELOTEAMENTO | Loteamento | I |  |  | N |
| LTEMATRICULA | Matrícula | S |  |  | N |
| LTEMEDCHANFRO | Med. Chanfro | S |  |  | N |
| LTEMEDDIREITA | Med. Direita | S |  |  | N |
| LTEMEDESQUERDA | Med. Esquerda | S |  |  | N |
| LTEMEDFRENTE | Med. Frente | S |  |  | N |
| LTEMEDFUNDO | Med. Fundo | S |  |  | N |
| LTEMEDVARIANTE | Med. Variante | S |  |  | N |
| LTENUMERO | Numero | S |  |  | N |
| LTEOBS | Observação | S |  |  | N |
| LTEPRECODOMETRO | Vlr. M2 | F |  |  | N |
| LTEQUADRA | Quadra | S |  |  | N |
| LTEREGISTRO | Registro | S |  |  | N |
| LTEREGISTROITUIPTU | Registro ITU/IPTU | S |  |  | N |
| LTERUA | Rua | S |  |  | N |
| LTESITUACAO | Situação | S |  |  | N |
| LTETIPOZONA | Zona de uso | I |  |  | N |
| LTEULTIMOIPTU | Dh. Último IPTU | H |  |  | N |
| LTEUSUALTERACAO | Usuário Alteração | I |  |  | N |
| LTEUSUINCLUSAO | Usuário Inclusão | I |  |  | N |
| LTEVALORCOMPRA | Vlr. Compra | F |  |  | N |
| LTEVLRLOTE | Vlr. Lote | F |  |  | S |
| LTEX | X | I |  |  | N |
| LTEY | Y | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimLote / [TIMLTE](TIMLTE.md) | SACLOTE->LTECODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimLote / [TIMLTE](TIMLTE.md) | LTVLOTE->LTECODIGO | I |
| TimLote / [TIMLTE](TIMLTE.md) | Bairro / [TSIBAI](TSIBAI.md) | LTEBAIRRO->CODBAI | I |
| TimLote / [TIMLTE](TIMLTE.md) | Cidade / [TSICID](TSICID.md) | LTECIDADE->CODCID | I |
| TimLote / [TIMLTE](TIMLTE.md) | Endereco / [TSIEND](TSIEND.md) | LTECODEND->CODEND | I |
| TimLote / [TIMLTE](TIMLTE.md) | TimCartorio / [TIMCAR](TIMCAR.md) | LTECARTORIO->CARCODIGO | I |
| TimLote / [TIMLTE](TIMLTE.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | LTELOTEAMENTO->LOTCODIGO | I |
| TimLote / [TIMLTE](TIMLTE.md) | TimZonaUso / [TIMTZL](TIMTZL.md) | LTETIPOZONA->TZLCODIGO | I |
| TimLote / [TIMLTE](TIMLTE.md) | Usuario / [TSIUSU](TSIUSU.md) | LTEUSUINCLUSAO->CODUSU | I |
| TimLote / [TIMLTE](TIMLTE.md) | Usuario2 / [TSIUSU](TSIUSU.md) | LTEUSUALTERACAO->CODUSU | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimLote / [TIMLTE](TIMLTE.md) | PPVLOTE->LTECODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimLote / [TIMLTE](TIMLTE.md) | VDOLOTE->LTECODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
