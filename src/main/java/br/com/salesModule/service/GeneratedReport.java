package br.com.salesModule.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.salesModule.repository.SalesRepository;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GeneratedReport {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private final SalesRepository salesRepository;
	
	public void report(HttpServletResponse response) throws JRException, SQLException, IOException {
		Map<String, Object> params = new HashMap<>();
		params.put("logo", ClassLoader.getSystemResourceAsStream("/reports/logo.jpg"));
		params.put("total", this.salesRepository.sumAllPrice().toString());
		InputStream reportStream = this.getClass().getResourceAsStream("/reports/Products.jasper");
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
		JasperPrint jasperPrint = JasperFillManager
				.fillReport(jasperReport, params, dataSource.getConnection());
		response.setContentType("application/pdf");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; file=sales.pdf");
		OutputStream outputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
	}

}
