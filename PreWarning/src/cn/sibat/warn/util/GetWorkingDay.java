package cn.sibat.warn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetWorkingDay {
	/**  
     * @param args  
	 * @throws ParseException 
     */  
    public static void main(String[] args) throws ParseException {   
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	GetWorkingDay a = new GetWorkingDay();   
    	Calendar c = Calendar.getInstance();
    	System.out.println(a.getWorkdayTime(sf.parse("2016-05-10").getTime(),sf.parse("2016-05-17").getTime()));
    }   
   
    public static int getWorkdayTime(long start, long end){   
        //如果起始时间大于结束时间，将二者交换   
        if(start>end){   
            long temp = start;   
            start = end;   
            end = temp;   
        }   
        //根据参数获取起始时间与结束时间的日历类型对象   
        Calendar sdate = Calendar.getInstance();   
        Calendar edate = Calendar.getInstance();   
        sdate.setTimeInMillis(start);   
        edate.setTimeInMillis(end);   
        //如果两个时间在同一周并且都不是周末日期，则直接返回时间差，增加执行效率   
        if(sdate.get(Calendar.YEAR)==edate.get(Calendar.YEAR)   
                && sdate.get(Calendar.WEEK_OF_YEAR)==edate.get(Calendar.WEEK_OF_YEAR)   
                && sdate.get(Calendar.DAY_OF_WEEK)!=1 && sdate.get(Calendar.DAY_OF_WEEK)!=7  
                && edate.get(Calendar.DAY_OF_WEEK)!=1 && edate.get(Calendar.DAY_OF_WEEK)!=7){   
            return edate.get(Calendar.DAY_OF_WEEK)- sdate.get(Calendar.DAY_OF_WEEK);   
        }   
        //首先取得起始日期与结束日期的下个周一的日期   
        
        Calendar snextM = getNextMonday(sdate);   
        Calendar enextM = getNextMonday(edate);   
        //获取这两个周一之间的实际天数   
        int days = getDaysBetween(snextM, enextM);   
        //获取这两个周一之间的工作日数(两个周一之间的天数肯定能被7整除，并且工作日数量占其中的5/7)   
        int workdays = days/7*5;  
        System.out.println("workdays = "+workdays);
        //获取开始时间的偏移量   
        int scharge = 0;   
        
        if(sdate.get(Calendar.DAY_OF_WEEK)!=1 && sdate.get(Calendar.DAY_OF_WEEK)!=7){   
            //只有在开始时间为非周末的时候才计算偏移量   
            scharge = (7-sdate.get(Calendar.DAY_OF_WEEK));   
          System.out.println("scharge = "+scharge);
        }   
        //获取结束时间的偏移量   
        int echarge = 0;   
        if(edate.get(Calendar.DAY_OF_WEEK)!=1 && edate.get(Calendar.DAY_OF_WEEK)!=7){   
            //只有在结束时间为非周末的时候才计算偏移量   
            echarge = (7-edate.get(Calendar.DAY_OF_WEEK));   
           System.out.println("echarge = "+echarge);
        }   
        //计算最终结果，具体为：workdays加上开始时间的时间偏移量，减去结束时间的时间偏移量   
        return workdays+scharge-echarge;   
    }   
//   
    private static Calendar getNextMonday(Calendar cal){
    	Calendar c = Calendar.getInstance();
    	c.setTime(cal.getTime());
        int addnum=9-cal.get(Calendar.DAY_OF_WEEK);   
        if(addnum==8)addnum=1;//周日的情况   
        c.add(Calendar.DATE, addnum);   
        return c;   
    }   
    /**  
     * 获取两个日期之间的实际天数，支持跨年  
     */  
    public static int getDaysBetween(Calendar start, Calendar end){   
        if(start.after(end)){   
            Calendar swap = start;   
            start = end;   
            end = swap;   
        }   
        int days = end.get(Calendar.DAY_OF_YEAR)- start.get(Calendar.DAY_OF_YEAR);   
        int y2 = end.get(Calendar.YEAR);   
        if (start.get(Calendar.YEAR) != y2) {   
            start = (Calendar) start.clone();   
            do {   
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);   
                start.add(Calendar.YEAR, 1);   
            }while(start.get(Calendar.YEAR) != y2);   
        }   
        return days;   
    }   
}
