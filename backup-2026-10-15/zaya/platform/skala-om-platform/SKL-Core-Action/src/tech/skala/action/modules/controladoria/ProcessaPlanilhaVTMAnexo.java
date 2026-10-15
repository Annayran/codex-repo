package tech.skala.action.modules.controladoria;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;
import com.sankhya.util.StringUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.EntityPrimaryKey;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.modelcore.evo.job.EvoUpdateProductsTrialJob;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.action.modules.comercial.estoque.terceiros.Worksheet;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.dd.EntityUpdate;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class ProcessaPlanilhaVTMAnexo {

	private Registro[] rows;

	private BigDecimal codEmp;
	private Timestamp dtRef;
	private String instanceId = "";
	private String fileName;
	private String descriptionFile;
	private Action action;
	private Collection<ValorTributarioMinimoItem> collection = new LinkedList<>();

	public ProcessaPlanilhaVTMAnexo(Registro[] rows) {
		this.rows = rows;
		this.collection.clear();

		this.codEmp = BigDecimalUtil.getBigDecimal(rows[0].getCampo(SklFieldName.CODEMP));
		this.dtRef = (Timestamp) rows[0].getCampo(SklFieldName.DTREF);
	}

	public void processarPlanilha() throws SkalaModelException {

		this.codEmp = BigDecimalUtil.getBigDecimal(rows[0].getCampo(SklFieldName.CODEMP));
		this.dtRef = (Timestamp) rows[0].getCampo(SklFieldName.DTREF);

		this.instanceId = String.format("%s_%s_%s", codEmp, StringUtils.formatTimestamp(dtRef, "dd/MM/yyyy H:mm:ss"),
				"AD_PLTGFVTMCAB");

		try {
			validarArquivo();
			limparTabelaItens();
			getArquivoAnexado();
			carregarPlanilha();
			persistePlanilha();
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar a planilha VTM.", e);
		}

	}

	private void persistePlanilha() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					for (ValorTributarioMinimoItem vtm : collection) {
						vtm.persists();
					}

				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível persistir as informações do arquivo anexo.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	private void carregarPlanilha() throws SkalaModelException {
		try {
			final FileInputStream fileInputStream = new FileInputStream(this.fileName);
			final XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);

			final int totalLines = sheet.getLastRowNum();
			final int startLine = 0;

			ValorTributarioMinimoItem vtm = null;

			for (int i = startLine; i <= totalLines; ++i) {
				final XSSFRow row = sheet.getRow(i);

				vtm = new ValorTributarioMinimoItem();
				vtm.setCodEmp(new BigDecimal(row.getCell(0).toString().replace(".0", "")));
				vtm.setDtRef(new Timestamp(row.getCell(1).getDateCellValue().getTime()));
				vtm.setCodProd(new BigDecimal(row.getCell(2).toString().replace(".0", "")));
				vtm.setVlrTributario(new BigDecimal(row.getCell(3).toString()));

				collection.add(vtm);

			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível ler os dados da Planilha.", e);
		}

	}

	private void getArquivoAnexado() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "ProcessaArquivoVTM_queDataFile.sql");
			sql.setNamedParameter("NOMEINSTANCIA", "AD_PLTGFVTMCAB");
			sql.setNamedParameter("PKREGISTRO", this.instanceId);

			rs = sql.executeQuery();

			while (rs.next()) {
				this.fileName = String.format("%s%s", rs.getString("TEXTO"), rs.getString("CHAVEARQUIVO"));
				this.descriptionFile = rs.getString("NOMEARQUIVO");
			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar o arquivo anexo.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}

	}

	private void limparTabelaItens() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {
					ValorTributarioMinimoItem vtm = new ValorTributarioMinimoItem();
					vtm.clear(codEmp, dtRef);
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível excluir as informações processadas pelo arquivo anexo.", e);
		} finally {
			JapeSession.close(hnd);
		}
	}

	private void validarArquivo() throws SkalaModelException {
		if (counterFile().longValue() == 0)
			throw new SkalaModelException("<br />Não existe planilha em anexo para processamento, verifique.<br />");

		if (counterFile().longValue() > 1)
			throw new SkalaModelException(
					"<br />Só é permitido 1 (uma) planilha em anexo para processamento, verifique.<br />");

	}

	private BigDecimal counterFile() throws SkalaModelException {
		BigDecimal counter = BigDecimal.ZERO;

		try {
			counter = NativeSql.getBigDecimal("COUNT(1)", "TSIANX", "NOMEINSTANCIA = ? AND PKREGISTRO = ?",
					new Object[] { "AD_PLTGFVTMCAB", this.instanceId });
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível resolver a quantidade de arquivo(s) anexo(s).", e);
		}

		return counter;

	}

	public void atualizaVTM() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(false);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws SkalaModelException {

					atualizarVTM();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Ocorreu um erro ao atualizar as informações de Valor Tributário Mínimo.", e);
		} finally {
			JapeSession.close(hnd);
		}

	}

	protected void atualizarVTM() throws SkalaModelException {

		try {

			JapeWrapper vtmItemDAO = JapeFactory.dao("AD_PLTGFVTMITE");
			Collection<DynamicVO> vtmItemVOs = vtmItemDAO.find("this.CODEMP = ? AND this.DTREF = ?", codEmp, dtRef);

			EntityUpdate update = null;

			BigDecimal codProd = null;
			BigDecimal vlrUnit = null;

			JapeWrapper vtmDAO = JapeFactory.dao("PrecoMedioProdutoEmpresa");
			for (DynamicVO vtmItemVO : vtmItemVOs) {
				codProd = vtmItemVO.asBigDecimal(SklFieldName.CODPROD);
				vlrUnit = vtmItemVO.asBigDecimal("VLRUNITMEDIO");

				DynamicVO vtmVO = vtmDAO.findByPK(codEmp, codProd, dtRef);
				if (vtmVO != null) {
					update = new EntityUpdate("PrecoMedioProdutoEmpresa", vtmVO);
					update.setField("VLRUNITMEDIO", vlrUnit);
					update.fluidUpdate();
				}

			}

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível atualizar tabela de Base de IPI (VTM)", e);
		}

	}

}
