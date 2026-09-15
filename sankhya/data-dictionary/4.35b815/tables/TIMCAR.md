# TIMCAR — Cartórios

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 2
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimCartorio | Cartórios | S | N |
| TimCartorioAverbacao | Cartório de Averbação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARBAIRRO | Bairro | I |  |  | N |
| CARCARTORIO | Descrição | S |  |  | N |
| CARCEP | CEP | S |  |  | N |
| CARCIDADE | Cidade | I |  |  | N |
| CARCODEND | Endereço | I |  |  | N |
| CARCODIGO | Código | I |  |  | N |
| CARCOMPLEMENTO | Complemento | S |  |  | N |
| CARENDERECO | Endereço | S |  |  | N |
| CARENDFINAL | Endereço | S |  |  | S |
| CARNUMEND | Número | S |  |  | N |
| CAROBSERVACAO | Observação | S |  |  | N |
| CARPARCEIRO | Parceiro | I |  |  | N |
| CARTELEFONES | Telefone | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | TimCartorio / [TIMCAR](TIMCAR.md) | TIMCARTORIO->CARCODIGO | I |
| TimCartorio / [TIMCAR](TIMCAR.md) | Bairro / [TSIBAI](TSIBAI.md) | CARBAIRRO->CODBAI | I |
| TimCartorio / [TIMCAR](TIMCAR.md) | Cidade / [TSICID](TSICID.md) | CARCIDADE->CODCID | I |
| TimCartorio / [TIMCAR](TIMCAR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CARPARCEIRO->CODPARC | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCartorio / [TIMCAR](TIMCAR.md) | IMVCARTORIO->CARCODIGO | I |
| TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | TimCartorio / [TIMCAR](TIMCAR.md) | LICCARTORIO->CARCODIGO | I |
| TimLocacaoImovelCaucao / [TIMLIC](TIMLIC.md) | TimCartorioAverbacao / [TIMCAR](TIMCAR.md) | LICCARTORIOAVERBACAO->CARCODIGO | I |
| TimLote / [TIMLTE](TIMLTE.md) | TimCartorio / [TIMCAR](TIMCAR.md) | LTECARTORIO->CARCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCartorio / [TIMCAR](TIMCAR.md) | LOTMDCARTORIO->CARCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCartorioAverbacao / [TIMCAR](TIMCAR.md) | LOTCARTORIOSUGE->CARCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
