package tech.skala.action.modules.comercial.exportacao;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sankhya.util.BigDecimalUtil;
import com.sankhya.util.JdbcUtils;

import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.core.JapeSession.SessionHandle;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.PrePersistEntityState;
import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.sankhya.modelcore.comercial.centrais.CACHelper;
import br.com.sankhya.modelcore.util.DynamicEntityNames;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessItemsWorksheetAttachmentHelper {

	private Registro[] rows;

	private BigDecimal invoiceId;
	private String identified = null;
	private Timestamp createdAt;
	private String fileName;
	private Collection<ItemDTO> collection = new LinkedList<>();
	private AuthenticationInfo authInfo = AuthenticationInfo.getCurrent();

	public ProcessItemsWorksheetAttachmentHelper(Registro[] rows) {
		this.rows = rows;
	}

	public void processAttachedSpreadsheet() throws SkalaModelException {
		this.invoiceId = BigDecimalUtil.getBigDecimal(this.rows[0].getCampo(SklFieldName.NUNOTA));

		this.getDataFile();

		if (this.identified != null) {
			this.fileName = String.format("%s/%s", this.getPath(), this.identified);

			this.loadWorksheet();

			this.processWorksheet();
		}
	}

	public void processWorksheet() throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.getCurrentSession().getTopMostHandle();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {

				@Override
				public void doWithTx() throws Exception {
					buildItems();
				}

			});

		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível processar a ação.", e);
		} finally {
			hnd = null;
		}
	}

	private void getDataFile() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "ProcessItemsWorksheetAttachment_queDataFile.sql");
			sql.setNamedParameter("CODATA", this.invoiceId);

			rs = sql.executeQuery();

			while (rs.next()) {
				this.identified = rs.getString("IDENTIFICACAOARQUIVO");
				this.createdAt = rs.getTimestamp("DTINCLUSAO");
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível localizar inforações do arquivo anexo.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
	}

	private String getPath() {
		String path = null;

		final SimpleDateFormat dirFormat = new SimpleDateFormat("yyyy/MM/dd");
		path = "/home/mgeweb/SankhyaW/Anexos/" + dirFormat.format(this.createdAt);

		return path;
	}

	private void loadWorksheet() throws SkalaModelException {
		try {
			final FileInputStream fileInputStream = new FileInputStream(this.fileName);
			final XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);

			final int totalLines = sheet.getLastRowNum();
			final int startLine = 0;

			ItemDTO itemDTO = null;

			for (int i = startLine; i <= totalLines; ++i) {
				final XSSFRow row = sheet.getRow(i);

				itemDTO = new ItemDTO();
				itemDTO.setProductId(new BigDecimal(row.getCell(0).toString().replace(".0", "")));
				itemDTO.setQuantity(new BigDecimal(row.getCell(1).toString()));
				itemDTO.setValue(new BigDecimal(row.getCell(2).toString()));
				itemDTO.setOrigLocalId(row.getCell(3) == null ? null : new BigDecimal(row.getCell(3).toString()));
				

				collection.add(itemDTO);
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível ler os dados da Planilha.", e);
		}
	}

	private void buildItems() throws SkalaModelException {
		try {
			Collection<PrePersistEntityState> itensNota = new ArrayList<>();
			CACHelper cacHelper = new CACHelper();

			final DynamicVO cabVO = (DynamicVO) EntityFacadeFactory.getDWFFacade()
					.findEntityByPrimaryKeyAsVO("CabecalhoNota", new Object[] { this.invoiceId });
			
			BigDecimal currencyValue = BigDecimalUtil.getValueOrZero(cabVO.asBigDecimal("VLRMOEDA"));
			
			if (currencyValue.compareTo(BigDecimal.ZERO)==0 )  {
				SkalaModelException.throwMe("Valor da moeda não pode ser zero", new Exception());
			}			
			DynamicVO itemVO;

			for (ItemDTO itemDTO : this.collection) {
				itemVO = (DynamicVO) EntityFacadeFactory.getDWFFacade()
						.getDefaultValueObjectInstance(DynamicEntityNames.ITEM_NOTA);
				itemVO.setProperty(SklFieldName.CODPROD, itemDTO.getProductId());
				itemVO.setProperty(SklFieldName.QTDNEG, itemDTO.getQuantity());
				itemVO.setProperty(SklFieldName.VLRUNIT, currencyValue.multiply(itemDTO.getValue()));
				itemVO.setProperty(SklFieldName.CODVOL, "CX");
				itemVO.setProperty(SklFieldName.CODLOCALORIG, itemDTO.getOrigLocalId());

				PrePersistEntityState itePreState = PrePersistEntityState.build(EntityFacadeFactory.getDWFFacade(),
						DynamicEntityNames.ITEM_NOTA, itemVO);
				itensNota.add(itePreState);
			}

			cacHelper.incluirAlterarItem(this.invoiceId, this.authInfo, itensNota, true);
		
		} catch (Exception e) {
			SkalaModelException.throwMe("Não foi possível incluir os itens.", e);
		}
	}

}
