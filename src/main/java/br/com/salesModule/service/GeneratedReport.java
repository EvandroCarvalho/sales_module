package br.com.salesModule.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

public interface GeneratedReport {
	public void report(HttpServletResponse response) throws JRException, SQLException, IOException;
}
