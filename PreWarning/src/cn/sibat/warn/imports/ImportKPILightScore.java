package cn.sibat.warn.imports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;

import cn.sibat.warn.model.kpi.KPILightScore;
import cn.sibat.warn.util.HibSession;

public class ImportKPILightScore {
	
	public static void main(String[] args) {
		ImportKPILightScore is = new ImportKPILightScore();
		try {
			is.readXls("D:\\work\\宝安区劳资纠纷模型指标分值表.xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public void readXls(String path) throws IOException {
		 		  HibSession hb = HibSession.getInstance();
		 		  Session session = hb.getSessionFactory().openSession();
		 		  session.beginTransaction();
		          InputStream is = new FileInputStream(path);
		          HSSFWorkbook wb = new HSSFWorkbook(is);
		          HSSFSheet  sheet = wb.getSheetAt(0);
		          String[] s = new String[8];
		          for (int i = 2; i < 52; i++) {
		        	  HSSFRow row = sheet.getRow(i);
		        	  for (int j = 0; j < 8; j++) {
		        		 s[j] = getValue(row.getCell(j));
//						System.out.print(getValue(row.getCell(j))+"  ");
					}
		        	  KPILightScore ks = new KPILightScore();
		        	  ks.setFirst_kpi(s[0]);
		        	  ks.setSecond_kpi(s[1]);
		        	  ks.setThird_kpi(s[1]+s[2]);
		        	  ks.setGreen_score(Double.valueOf(s[4]));
		        	  ks.setBlue_score(Double.valueOf(s[5]));
		        	  ks.setYellow_score(Double.valueOf(s[6]));
		        	  ks.setRed_score(Double.valueOf(s[7]));
//		        	  System.out.println("");
		        	  session.save(ks);
				}
		          session.getTransaction().commit();
		          session.close();
	 }
	
	
	 
	 private String getValue(HSSFCell hssfCell) {
		          if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
		              return String.valueOf(hssfCell.getBooleanCellValue());
		          } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
		              return String.valueOf(hssfCell.getNumericCellValue());
		          } else {
		              return String.valueOf(hssfCell.getStringCellValue());
		          }
		      }

}
