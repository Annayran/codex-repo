/*
 * Rollback do chamado 8175.
 *
 * Executar somente se a view SKL_QTDESTPLAPLA_EXP_V2 tiver sido criada
 * pelo deploy deste chamado e os campos calculados ja tiverem sido
 * desvinculados no dicionario Sankhya.
 */
DROP VIEW SKL_QTDESTPLAPLA_EXP_V2;

