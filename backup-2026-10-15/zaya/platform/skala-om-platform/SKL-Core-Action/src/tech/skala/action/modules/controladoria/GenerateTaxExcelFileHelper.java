package tech.skala.action.modules.controladoria;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.sankhya.util.SessionFile;
import com.sankhya.util.StringUtils;
import com.sankhya.util.UIDGenerator;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.sql.NativeSql;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.sankhya.ws.ServiceContext;
import tech.skala.core.commons.constants.SklFieldName;
import tech.skala.core.commons.exceptions.SkalaModelException;
import tech.skala.core.commons.extensions.Action;

public class GenerateTaxExcelFileHelper {

	private Action action;
	private GenerateTaxExcelFileParams params;

	private static String tempFilePath = System.getProperty("jboss.server.temp.dir");
	private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private Date currentDate = new Date();
	private String fileName = String.format("%s",
			"TRIBUTACAO_ITENS_NOTA_" + resolveTimestampToString(this.currentDate));

	SessionFile sessionFile = null;

	ResultSet rs = null;

	public GenerateTaxExcelFileHelper(Action action, GenerateTaxExcelFileParams params) throws Exception {
		this.action = action;
		this.params = params;

		this.rs = this.executeQuery();
	}

	public void execute() throws Exception {
		this.generateFile(this.params.getType());
	}

	private void generateFile(String type) throws Exception {
		File file = "XLSX".equalsIgnoreCase(this.params.getType()) ? this.resultsetToXlsx(this.executeQuery())
				: this.resultsetToCsv(this.executeQuery(), "ISO-8859-1");

		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

		sessionFile = SessionFile.createSessionFile(fileName, "application/" + fileType, file);

		String key = "file_" + UIDGenerator.getNextID();
		ServiceContext.getCurrent().putHttpSessionAttribute(key, sessionFile);

		this.action.appendMessageWithLineSeparator("Key: " + key + " - " + fileName);
		this.action.appendMessageWithLineSeparator("<br><b>Arquivo Gerado, clique abaixo para baixar o arquivo:</b>");
		
		this.action.appendMessageWithLineSeparator(
				"<br /><b><a id=\"alink\" href=\"/mge/visualizadorArquivos.mge?chaveArquivo=" + key
						+ "\" target=\"_blank\">BAIXAR ARQUIVO</a></b><br />");
	}

	private ResultSet executeQuery() throws SkalaModelException {
		JdbcWrapper jdbc = null;
		NativeSql sql = null;
		ResultSet rs = null;

		try {
			jdbc = EntityFacadeFactory.getDWFFacade().getJdbcWrapper();
			jdbc.openSession();

			sql = new NativeSql(jdbc);
			sql.loadSql(getClass(), "GenerateTaxExcelFile_queData.sql");
			sql.setNamedParameter("DTINI", this.params.getInitialDate());
			sql.setNamedParameter("DTFIN", this.params.getFinalDate());
			sql.setNamedParameter("CODUF", this.params.getStateFederationId());
			sql.setNamedParameter(SklFieldName.CODEMP, this.params.getCompanyId());
			sql.setNamedParameter("CODCFO", this.params.getCfop());
			sql.setNamedParameter(SklFieldName.CODTIPOPER, this.params.getOperationTypeId());
			sql.setNamedParameter("NCM", this.params.getNcm());
			sql.setNamedParameter(SklFieldName.NUNOTA, this.params.getInvoiceId());
			sql.setNamedParameter(SklFieldName.CODPARC, this.params.getPartnerId());
			sql.setNamedParameter(SklFieldName.TIPMOV, this.params.getTypeMovementId());
			sql.setNamedParameter("CODCTACTB", this.params.getAccountingAccountId());

			rs = sql.executeQuery();

			return rs;
		} catch (Exception e) {
			throw new SkalaModelException("Ocorreu erro ao carregar as informações do Banco de Dados.", e);
		}
	}

	private File resultsetToXlsx(ResultSet rset) throws Exception {
		ResultSetMetaData rsmd = rset.getMetaData();
		SXSSFWorkbook book = new SXSSFWorkbook(100);
		Sheet sheet = book.createSheet();
		org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);
		Cell cell = null;

		Font font = book.createFont();
		font.setBoldweight((short) 700);
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Arial");

		CellStyle style = book.createCellStyle();
		style.setFont(font);
		style.setAlignment((short) 2);

		int rowNum;
		for (rowNum = 0; rowNum < rsmd.getColumnCount(); ++rowNum) {
			cell = row.createCell(rowNum);
			cell.setCellValue(rsmd.getColumnLabel(rowNum + 1));
			cell.setCellStyle(style);
			sheet.autoSizeColumn(rowNum);
		}

		for (rowNum = 1; rset.next(); ++rowNum) {
			row = sheet.createRow(rowNum);

			for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
				Object columnValue = rset.getObject(i);
				cell = row.createCell(i - 1);
				if (columnValue != null && !Blob.class.isAssignableFrom(columnValue.getClass())) {
					if (!(columnValue instanceof Date) && !(columnValue instanceof Timestamp)) {
						if (Number.class.isAssignableFrom(columnValue.getClass())) {
							cell.getCellStyle().setAlignment((short) 3);
							cell.setCellType(0);
							cell.setCellValue(rset.getDouble(i));
						} else if (Clob.class.isAssignableFrom(columnValue.getClass())) {
							style.setAlignment((short) 1);
							cell.setCellValue(rset.getString(i));
						} else {
							style.setAlignment((short) 1);
							cell.setCellValue(columnValue.toString());
						}
					} else {
						cell.getCellStyle().setAlignment((short) 2);
						String data = dateTimeFormatter.format(columnValue);
						cell.setCellValue(data);
					}
				}
			}
		}

		FileOutputStream output = new FileOutputStream(tempFilePath + File.separator + this.fileName + ".xlsx");
		book.write(output);
		output.flush();
		output.close();

		return new File(tempFilePath + File.separator + this.fileName + ".xlsx");
	}

	private File resultsetToCsv(ResultSet rset, String charSet) throws Exception {
		FileOutputStream output = new FileOutputStream(tempFilePath + File.separator + this.fileName + ".csv");
		ResultSetMetaData rsmd = rset.getMetaData();
		StringBuilder record = new StringBuilder();
		int timeToFlush = 0;

		int i;
		for (i = 1; i <= rsmd.getColumnCount(); ++i) {
			record.append(i > 1 ? ", " : "").append(rsmd.getColumnLabel(i));
		}

		output.write(charSet == null ? record.toString().getBytes() : record.toString().getBytes(charSet));

		while (rset.next()) {
			record = new StringBuilder();
			record.append("\n");

			for (i = 1; i <= rsmd.getColumnCount(); ++i) {
				Class<?> type = Class.forName(rsmd.getColumnClassName(i));
				Object value = null;
				if (rset.getObject(i) != null) {
					if (!Date.class.isAssignableFrom(type) && !Timestamp.class.isAssignableFrom(type)) {
						if (Number.class.isAssignableFrom(type)) {
							value = rset.getBigDecimal(i);
						} else if (String.class.isAssignableFrom(type) || Clob.class.isAssignableFrom(type)) {
							value = escape(rset.getString(i));
						}
					} else {
						value = dateTimeFormatter.format(rset.getTimestamp(i));
					}
				}

				record.append(i > 1 ? ", " : "").append("\"").append(StringUtils.getNullAsEmpty(value)).append("\"");
			}

			output.write(charSet == null ? record.toString().getBytes() : record.toString().getBytes(charSet));
			++timeToFlush;
			if (timeToFlush > 1000) {
				output.flush();
				timeToFlush = 0;
			}
		}

		output.flush();
		output.close();
		return new File(tempFilePath + File.separator + this.fileName + ".csv");
	}

	private static String escape(String text) {
		return text == null ? ""
				: text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\b", "\\b").replace("\f", "\\f")
						.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}

	private static String resolveTimestampToString(Date currentDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String string = dateFormat.format(currentDate);

		return string.replaceAll("\\D*", "");
	}
}
