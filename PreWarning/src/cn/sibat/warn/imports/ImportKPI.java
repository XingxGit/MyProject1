package cn.sibat.warn.imports;

import org.hibernate.Session;

import cn.sibat.warn.model.kpi.KPI;
import cn.sibat.warn.util.HibSession;

public class ImportKPI {
	
	public static void main(String[] args) {
		HibSession hs = HibSession.getInstance();
		Session session = hs.getSessionFactory().openSession();
		session.beginTransaction();
		KPI kpi = new KPI();
		kpi.setKpi_id("1");
		kpi.setKpi_name("拖欠工资");
		kpi.setKpi_weight(0.2001);
		kpi.setGreen_weight(0.062);
		kpi.setBlue_weight(0.116);
		kpi.setYellow_weight(0.184);
		kpi.setRed_weight(0.638);
		kpi.setAgency("综管所");
		kpi.setMeasurement("月份数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("2");
		kpi.setKpi_name("工资未足额支付");
		kpi.setKpi_weight(0.1007);
		kpi.setGreen_weight(0.076);
		kpi.setBlue_weight(0.113);
		kpi.setYellow_weight(0.358);
		kpi.setRed_weight(0.453);
		kpi.setAgency("综管所");
		kpi.setMeasurement("百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("3");
		kpi.setKpi_name("减少福利待遇");
		kpi.setKpi_weight(0.0689);
		kpi.setGreen_weight(0.047);
		kpi.setBlue_weight(0.118);
		kpi.setYellow_weight(0.665);
		kpi.setRed_weight(0.170);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("占总量百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("4");
		kpi.setKpi_name("调整规章制度");
		kpi.setKpi_weight(0.0239);
		kpi.setGreen_weight(0.085);
		kpi.setBlue_weight(0.427);
		kpi.setYellow_weight(0.388);
		kpi.setRed_weight(0.100);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("调整幅度百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("5");
		kpi.setKpi_name("加班时间降低");
		kpi.setKpi_weight(0.0632);
		kpi.setGreen_weight(0.057);
		kpi.setBlue_weight(0.172);
		kpi.setYellow_weight(0.641);
		kpi.setRed_weight(0.130);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("6");
		kpi.setKpi_name("非法用工");
		kpi.setKpi_weight(0.0187);
		kpi.setGreen_weight(0.062);
		kpi.setBlue_weight(0.300);
		kpi.setYellow_weight(0.437);
		kpi.setRed_weight(0.201);
		kpi.setAgency("派出所 安监办 环保所 卫监所");
		kpi.setMeasurement("人数");
		session.save(kpi);
		/**
		 * hello
		 */
		
		
		kpi = new KPI();
		kpi.setKpi_id("7");
		kpi.setKpi_name("拖欠社保");
		kpi.setKpi_weight(0.0297);
		kpi.setGreen_weight(0.189);
		kpi.setBlue_weight(0.583);
		kpi.setYellow_weight(0.159);
		kpi.setRed_weight(0.070);
		kpi.setAgency("社保站");
		kpi.setMeasurement("人数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("8");
		kpi.setKpi_name("缴纳社保人数降低");
		kpi.setKpi_weight(0.0306);
		kpi.setGreen_weight(0.208);
		kpi.setBlue_weight(0.621);
		kpi.setYellow_weight(0.099);
		kpi.setRed_weight(0.073);
		kpi.setAgency("社保站");
		kpi.setMeasurement("百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("9");
		kpi.setKpi_name("集体争议、群体诉求人数占全厂员工比重");
		kpi.setKpi_weight(0.0404);
		kpi.setGreen_weight(0.054);
		kpi.setBlue_weight(0.0164);
		kpi.setYellow_weight(0.635);
		kpi.setRed_weight(0.0146);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("10");
		kpi.setKpi_name("投诉举报案件同年累计");
		kpi.setKpi_weight(0.0324);
		kpi.setGreen_weight(0.079);
		kpi.setBlue_weight(0.349);
		kpi.setYellow_weight(0.405);
		kpi.setRed_weight(0.167);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("件数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("11");
		kpi.setKpi_name("营业收入降低");
		kpi.setKpi_weight(0.0104);
		kpi.setGreen_weight(0.081);
		kpi.setBlue_weight(0.607);
		kpi.setYellow_weight(0.236);
		kpi.setRed_weight(0.075);
		kpi.setAgency("税务所");
		kpi.setMeasurement("百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("12");
		kpi.setKpi_name("利润总额降低");
		kpi.setKpi_weight(0.0171);
		kpi.setGreen_weight(0.088);
		kpi.setBlue_weight(0.582);
		kpi.setYellow_weight(0.257);
		kpi.setRed_weight(0.073);
		kpi.setAgency("税务所");
		kpi.setMeasurement("百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("13");
		kpi.setKpi_name("拟搬迁生产（经营）、企业收购兼并");
		kpi.setKpi_weight(0.0475);
		kpi.setGreen_weight(0.052);
		kpi.setBlue_weight(0.077);
		kpi.setYellow_weight(0.186);
		kpi.setRed_weight(0.685);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("-");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("14");
		kpi.setKpi_name("生产（经营）停顿");
		kpi.setKpi_weight(0.0659);
		kpi.setGreen_weight(0.052);
		kpi.setBlue_weight(0.077);
		kpi.setYellow_weight(0.186);
		kpi.setRed_weight(0.685);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("天数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("15");
		kpi.setKpi_name("经济性裁员");
		kpi.setKpi_weight(0.0323);
		kpi.setGreen_weight(0.057);
		kpi.setBlue_weight(0.169);
		kpi.setYellow_weight(0.628);
		kpi.setRed_weight(0.146);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("人数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("16");
		kpi.setKpi_name("法定代表人或经营者失联");
		kpi.setKpi_weight(0.0688);
		kpi.setGreen_weight(0.059);
		kpi.setBlue_weight(0.083);
		kpi.setYellow_weight(0.182);
		kpi.setRed_weight(0.676);
		kpi.setAgency("劳动办");
		kpi.setMeasurement("天数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("17");
		kpi.setKpi_name("拖欠房租");
		kpi.setKpi_weight(0.0237);
		kpi.setGreen_weight(0.070);
		kpi.setBlue_weight(0.604);
		kpi.setYellow_weight(0.218);
		kpi.setRed_weight(0.108);
		kpi.setAgency("综管所");
		kpi.setMeasurement("月份数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("18");
		kpi.setKpi_name("拖欠水费");
		kpi.setKpi_weight(0.0141);
		kpi.setGreen_weight(0.101);
		kpi.setBlue_weight(0.616);
		kpi.setYellow_weight(0.175);
		kpi.setRed_weight(0.108);
		kpi.setAgency("综管所");
		kpi.setMeasurement("月份数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("19");
		kpi.setKpi_name("拖欠电费");
		kpi.setKpi_weight(0.0142);
		kpi.setGreen_weight(0.101);
		kpi.setBlue_weight(0.616);
		kpi.setYellow_weight(0.175);
		kpi.setRed_weight(0.108);
		kpi.setAgency("综管所");
		kpi.setMeasurement("月份数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("20");
		kpi.setKpi_name("拟转卖物业");
		kpi.setKpi_weight(0.0049);
		kpi.setGreen_weight(0.059);
		kpi.setBlue_weight(0.147);
		kpi.setYellow_weight(0.306);
		kpi.setRed_weight(0.488);
		kpi.setAgency("综管所");
		kpi.setMeasurement("占总量百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("21");
		kpi.setKpi_name("拟转租物业");
		kpi.setKpi_weight(0.0063);
		kpi.setGreen_weight(0.067);
		kpi.setBlue_weight(0.324);
		kpi.setYellow_weight(0.256);
		kpi.setRed_weight(0.353);
		kpi.setAgency("综管所");
		kpi.setMeasurement("占总量百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("22");
		kpi.setKpi_name("被申请财产保全");
		kpi.setKpi_weight(0.0097);
		kpi.setGreen_weight(0.054);
		kpi.setBlue_weight(0.102);
		kpi.setYellow_weight(0.618);
		kpi.setRed_weight(0.225);
		kpi.setAgency("综管所");
		kpi.setMeasurement("占总量百分比");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("23");
		kpi.setKpi_name("资产被查封、冻结");
		kpi.setKpi_weight(0.0149);
		kpi.setGreen_weight(0.066);
		kpi.setBlue_weight(0.088);
		kpi.setYellow_weight(0.499);
		kpi.setRed_weight(0.346);
		kpi.setAgency("法庭");
		kpi.setMeasurement("天数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("24");
		kpi.setKpi_name("生产（经营）场所已纳入旧城改造计划");
		kpi.setKpi_weight(0.0117);
		kpi.setGreen_weight(0.053);
		kpi.setBlue_weight(0.080);
		kpi.setYellow_weight(0.444);
		kpi.setRed_weight(0.423);
		kpi.setAgency("城建科");
		kpi.setMeasurement("-");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("25");
		kpi.setKpi_name("行政处罚停止生产（经营）整顿");
		kpi.setKpi_weight(0.0193);
		kpi.setGreen_weight(0.053);
		kpi.setBlue_weight(0.080);
		kpi.setYellow_weight(0.241);
		kpi.setRed_weight(0.627);
		kpi.setAgency("派出所 安监办 环保所 卫监所");
		kpi.setMeasurement("天数");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("26");
		kpi.setKpi_name("主管机关依法取缔");
		kpi.setKpi_weight(0.0208);
		kpi.setGreen_weight(0.059);
		kpi.setBlue_weight(0.086);
		kpi.setYellow_weight(0.183);
		kpi.setRed_weight(0.672);
		kpi.setAgency("派出所 安监办 环保所 卫监所");
		kpi.setMeasurement("-");
		session.save(kpi);
		
		kpi = new KPI();
		kpi.setKpi_id("27");
		kpi.setKpi_name("重大经济纠纷诉讼");
		kpi.setKpi_weight(0.0099);
		kpi.setGreen_weight(0.068);
		kpi.setBlue_weight(0.148);
		kpi.setYellow_weight(0.613);
		kpi.setRed_weight(0.170);
		kpi.setAgency("法庭");
		kpi.setMeasurement("件数");
		session.save(kpi);
		
		session.getTransaction().commit();
		session.close();
	}

}
