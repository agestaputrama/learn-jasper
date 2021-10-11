package com.example.TryJasper;

import com.example.TryJasper.Entity.DummyData;
import com.example.TryJasper.Entity.ParentDummy;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class TryJasperApplication {

	public static void main(String[] args) {
		SpringApplication.run(TryJasperApplication.class, args);

		new TryJasperApplication().report();

	}

	private void report() {
		//buat data dummy
		List<ParentDummy> listOfParent = new ArrayList<>();
		List<DummyData> listOfChild = new ArrayList<>();
		ParentDummy parentDummy = new ParentDummy();
		DummyData dummyData = new DummyData();

		//set data
		parentDummy.setSatu("tes satu");
		parentDummy.setDua("tes dua");
		parentDummy.setTiga("tes tiga");
		listOfParent.add(parentDummy);

		for (int i = 1; i < 8; ++i) {
			dummyData = new DummyData();
			dummyData.setFirst("Tes A - "+i);
			dummyData.setMiddle("Tes B - "+i);
			dummyData.setLast("Tes C - " +i);
			listOfChild.add(dummyData);
		}

		try {

			//data utama
			JRBeanCollectionDataSource dataSourceParent = new JRBeanCollectionDataSource(listOfParent);

			//data dalam tabel
			JRBeanCollectionDataSource dataSourceChild = new JRBeanCollectionDataSource(listOfChild);

			//set parameter utk tabel
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("CollectionBeanParameter", dataSourceChild);

			System.out.println(dataSourceChild.getRecordCount()+" jumlah data tabel");

			InputStream input = getClass().getResourceAsStream("/templates/jasper.jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(input);

			String path = "C:\\Users\\C090821002\\Downloads";
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourceParent);
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\Coba jasper.pdf");

			System.out.println("berhasil");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}