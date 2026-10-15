package tech.skala.action.modules.pessoas.beneficio;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class CalculoBeneficioProcessor {
	
	private Registro[]						linhas;

	public CalculoBeneficioProcessor() {
		
	}
	
	public CalculoBeneficioProcessor(Registro[] linhas) {
		this.linhas = linhas;
	}

	public void calcularPreBeneficio(Timestamp dtRef, Timestamp dtPon) throws Exception {

		try {
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_ALELO_ECOMMERCE.getValue());
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_ALELO.getValue());
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_FLASH_VR_CSC.getValue());
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_TRANSUBE.getValue()); //ESSE FICA NA REFERENCIA DO PONTO
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_GUARUPASS.getValue());
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_VALECARD.getValue()); //ESSE FICA NA REFERENCIA DO PONTO
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_VIA_NOVA.getValue());
		} catch (Exception e) {
			String msg = "Não foi possível realizar o pré processamento dos Benefícios.";
			SkalaModelException.throwMe(msg, e);
		}
	}

	public void adicionarRecargaManual(BigDecimal indCre) throws Exception {
		JapeWrapper dao = JapeFactory.dao("AD_PLTMAPAPREBEN");

		try {
			BigDecimal nuOcor;
			BigDecimal vlrDiaTrab;
			DynamicVO vo;

			MapaProcessamentoBeneficio mapaBeneficio;
			for (Registro linha : linhas) {
				nuOcor = BigDecimalUtil.getBigDecimal(linha.getCampo("NUOCOR"));

				vo = dao.findByPK(nuOcor);

				mapaBeneficio = new MapaProcessamentoBeneficio();
				mapaBeneficio.loadByVO(vo);

				vlrDiaTrab = mapaBeneficio.getVlrBen().divide(mapaBeneficio.getIndBen(), BigDecimalUtil.MATH_CTX);

				mapaBeneficio.setIndCre(indCre);
				mapaBeneficio.setIndBen(mapaBeneficio.getIndBen().add(indCre));
				mapaBeneficio.setDiasTrab(mapaBeneficio.getDiasTrab().add(indCre));
				mapaBeneficio.setVlrBen(mapaBeneficio.getIndBen().multiply(vlrDiaTrab, BigDecimalUtil.MATH_CTX));

				mapaBeneficio.persists();
			}
		} catch (Exception e) {
			String msg = "Não foi possível alterar o campo Indice Crédito.";
			SkalaModelException.throwMe(msg, e);
		}
	}
	
	private void executeQueries(Timestamp dtRef, Timestamp dtPon, String caminho) throws SkalaModelException {
		NativeSql queValores = null;
		ResultSet rsValores = null;
		
		MapaProcessamentoBeneficio mapaBeneficio;
		JdbcWrapper jdbc = null;
		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
            jdbc.openSession();

            queValores = new NativeSql(jdbc);
			
			queValores.loadSql(getClass(), caminho);
			queValores.setNamedParameter("DTREF", dtRef);
			queValores.setNamedParameter("DTPON", dtPon);
			
			rsValores = queValores.executeQuery();
			while (rsValores.next()) {
				mapaBeneficio = new MapaProcessamentoBeneficio();
				mapaBeneficio.loadByResultSet(rsValores);
				mapaBeneficio.persists();
				
			}
			
		} catch (Exception e) {
			String msg = "Não foi possível processar o Pré Benefício.";
			SkalaModelException.throwMe(msg, e);
		} finally {
			JdbcUtils.closeResultSet(rsValores);
			NativeSql.releaseResources(queValores);
			JdbcWrapper.closeSession(jdbc);
		}
	}

	public void processarRecargaSemanal(Timestamp dtRef, Timestamp dtPon) throws SkalaModelException {
		try {
			executeQueries(dtRef, dtPon, QueryBeneficioEnum.BENEFICIO_TRANSUBE_SEMANAL.getValue()); 
		} catch (Exception e) {
			String msg = "Não foi possível realizar o pré processamento dos Benefícios.";
			SkalaModelException.throwMe(msg, e);
		}
		
	}

	
}
