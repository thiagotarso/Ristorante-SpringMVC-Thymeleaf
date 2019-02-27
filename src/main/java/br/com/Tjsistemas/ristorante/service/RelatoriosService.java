package br.com.Tjsistemas.ristorante.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatoriosService {

	@Autowired
	private DataSource dataSource;
	
	@SuppressWarnings("unused")
	public byte[] relatorioComandaCupom(Long empresa, Long comanda) throws SQLException, JRException {
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("format", "pdf");
		parametros.put("comanda_id", comanda);
		parametros.put("empresa", empresa);
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/relatorio_comanda_emitida.jasper");	

		Connection con = dataSource.getConnection();
		 try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con); 
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}
}
