package tech.skala.action.modules.comercial.estoque.terceiros;

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
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import tech.skala.core.commons.exceptions.SkalaModelException;

public class ProcessProductCountWorksheetAttachmentHelper {
	
	private Registro[] rows;

	private BigDecimal id;
	private Timestamp countDate;
	private String instanceId = "";
	private String fileName;
	private String descriptionFile;
	private Collection<Worksheet> collection = new LinkedList<>();

	public ProcessProductCountWorksheetAttachmentHelper(Registro[] rows) {
		this.rows = rows;
		this.collection.clear();
		
	}

	public void processAttachedSpreadsheet() throws SkalaModelException {
		this.id = BigDecimalUtil.getBigDecimal(this.rows[0].getCampo("NUNICO"));
		this.countDate = (Timestamp) this.rows[0].getCampo("DTCONTAGEM");
		this.instanceId = String.format("%s_%s_%s", this.id,
				StringUtils.formatTimestamp(this.countDate, "dd/MM/yyyy H:mm:ss"), "AD_PLTTGFCTETERCAB");

		try {
			this.validateFiles();
			this.clearWorksheet(id, countDate);
			this.getFileName();
			this.loadWorksheet();
			this.persistWorksheet(this.collection);
			this.updateDescriptionFile();
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel processar a acao.", e);
		}
		
	}

	private void getFileName() throws SkalaModelException {
		SessionHandle hnd = null;
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			hnd = JapeSession.open();

			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "ProcessAttachedWorksheet_queDataFile.sql");
			sql.setNamedParameter("CHAVE", "AD_PLTTGFCTETER");
			sql.setNamedParameter("NOMEINSTANCIA", "AD_PLTTGFCTETERCAB");
			sql.setNamedParameter("PKREGISTRO", this.instanceId);

			rs = sql.executeQuery();

			while (rs.next()) {
				this.fileName = String.format("%s%s", rs.getString("TEXTO"), rs.getString("CHAVEARQUIVO"));
				this.descriptionFile = rs.getString("NOMEARQUIVO");
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel localizar o arquivo anexo.", e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
			JapeSession.close(hnd);
		}
		
	}

	private void loadWorksheet() throws SkalaModelException {
		try {
			final FileInputStream fileInputStream = new FileInputStream(this.fileName);
			final XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			final int totalLines = sheet.getLastRowNum();
			final int startLine = 1;
			
			Worksheet worksheet = null;
			
			String control = "";
			
			for (int i = startLine; i <= totalLines; ++i) {
				final XSSFRow row = sheet.getRow(i);
				
				worksheet = new Worksheet();
				worksheet.setId(this.id);
				worksheet.setCountDate(this.countDate);
				
				worksheet.setProductId(new BigDecimal(row.getCell(0).toString().replace(".0", "")));
				
				try {
					control = row.getCell(1).toString();
					
					if (control.length() == 0) {
						worksheet.setControl(" ");
					} else {
						worksheet.setControl(control);
					}
				} catch (NullPointerException e ) {
					worksheet.setControl(" ");
				}
				
				worksheet.setQuantity(new BigDecimal(row.getCell(2).toString()));
				worksheet.setPartnerId(new BigDecimal(row.getCell(3).toString().replace(".0", "")));
				worksheet.setLocationId(new BigDecimal(row.getCell(4).toString().replace(".0", "")));
				
				try {
					worksheet.setManufacturingDate(new Timestamp(row.getCell(5).getDateCellValue().getTime()));
				} catch (NullPointerException e ) {
					worksheet.setManufacturingDate(null);
				}
				
				try {
					worksheet.setExpirationDate(new Timestamp(row.getCell(6).getDateCellValue().getTime()));
				} catch (NullPointerException e) {
					worksheet.setExpirationDate(null);
				}
				
				worksheet.setCompanyId(new BigDecimal(row.getCell(7).toString().replace(".0", "")));

				collection.add(worksheet);
			}
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel ler os dados da Planilha.", e);
		}
		
	}
	
	private void persistWorksheet(Collection<Worksheet> collection) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(Boolean.TRUE);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					for (Worksheet worksheet : collection) {
						worksheet.persists();
					}
				}
			});
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel persistir as informacoes do arquivo anexo.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	private void clearWorksheet(BigDecimal id, Timestamp countDate) throws SkalaModelException {
		SessionHandle hnd = null;

		try {
			hnd = JapeSession.open();
			hnd.setCanTimeout(true);

			hnd.execWithTX(new JapeSession.TXBlock() {
				@Override
				public void doWithTx() throws Exception {
					Worksheet worksheet = new Worksheet();
					worksheet.clear(id, countDate);
				}
			});
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel excluir as informacoes processadas pelo arquivo anexo.", e);
		} finally {
			JapeSession.close(hnd);
		}
		
	}
	
	private void updateDescriptionFile() {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "ProcessAttachedWorksheet_queUpdateDescriptionFile.sql");
			sql.setNamedParameter("NOMEARQUIVO", this.descriptionFile);
			sql.setNamedParameter("NUNICO", this.id);
			sql.setNamedParameter("DTCONTAGEM", this.countDate);
			sql.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			NativeSql.releaseResources(sql);
			JdbcWrapper.closeSession(jdbc);
		}
		
	}
	
	private BigDecimal counterFile() throws SkalaModelException {
		BigDecimal counter = BigDecimal.ZERO;
		
		try {
			counter = NativeSql.getBigDecimal("COUNT(1)", "TSIANX", "NOMEINSTANCIA = ? AND PKREGISTRO = ?", new Object[] { "AD_PLTTGFCTETERCAB", this.instanceId });
		} catch (Exception e) {
			SkalaModelException.throwMe("Nao foi possivel resolver a quantidade de arquivo(s) anexo(s).", e);
		}
		
		return counter;
		
	}
	
	private void validateFiles() throws SkalaModelException {
		BigDecimal fileCounter = this.counterFile();

		if (fileCounter.compareTo(BigDecimal.ZERO) == 0)
			SkalaModelException.throwMe("<br />Nao existe planilha em anexo para processamento, verifique.<br />", new Exception());

		if (fileCounter.compareTo(BigDecimal.ONE) != 0)
			SkalaModelException.throwMe("<br />So e permitido 1 (uma) planilha em anexo para processamento, verifique.<br />", new Exception());

	}
}
