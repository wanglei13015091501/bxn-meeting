<%@ page language="java" contentType="application/json;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.lang.management.ManagementFactory,com.sun.management.OperatingSystemMXBean"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 1L);
	response.setCharacterEncoding("utf-8");
	response.setHeader("Cache-Control", "no-cache");
	response.addHeader("Cache-Control", "no-store");
	response.setContentType("application/json;charset=UTF-8");
	Map<String,Object> status=getMonitorInfoBean();
	request.setAttribute("status",status);
%>
{
"os_info":"${status.osName}",
"cpu_used":"${status.cpu_used}",
"memory_used":"${status.usedMemory}"
}
<%! 
	public Map getMonitorInfoBean() throws Exception {
	  
	        // 操作系统   
	        String osName = System.getProperty("os.name");   
	        
	        // 构造返回对象   
	        Map<String,Object> infoBean = new HashMap<String,Object>();   
	        int usedMemeory=0;
	        int usedCpu=0;
	        if(osName.toLowerCase().indexOf("windows")>=0){
	        	usedMemeory=getMemoryForWindows();
	        	usedCpu=(int)getCpuRatioForWindows();
	        }else{
	        	
	        }
	        infoBean.put("usedMemory",usedMemeory);     
	        infoBean.put("osName",osName);   
	        infoBean.put("cpu_used",usedCpu);    
	        return infoBean;   
	}
	
	
	 private double getCpuRateForLinux() {
			String linuxVersion="2.4";
	        InputStream is = null;   
	        InputStreamReader isr = null;   
	        BufferedReader brStat = null;   
	        StringTokenizer tokenStat = null;   
	        try {   
	            System.out.println("Get usage rate of CUP , linux version: " + linuxVersion);   
	            Process process = Runtime.getRuntime().exec("top -b -n 1");   
	            is = process.getInputStream();   
	            isr = new InputStreamReader(is);   
	            brStat = new BufferedReader(isr);   
	            if (linuxVersion.equals("2.4")) {   
	                brStat.readLine();   
	                brStat.readLine();   
	                brStat.readLine();   
	                brStat.readLine();   
	                tokenStat = new StringTokenizer(brStat.readLine());   
	                tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                String user = tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                String system = tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                String nice = tokenStat.nextToken();   
	                System.out.println(user + " , " + system + " , " + nice);   
	                user = user.substring(0, user.indexOf("%"));   
	                system = system.substring(0, system.indexOf("%"));   
	                nice = nice.substring(0, nice.indexOf("%"));   
	                float userUsage = new Float(user).floatValue();   
	                float systemUsage = new Float(system).floatValue();   
	                float niceUsage = new Float(nice).floatValue();   
	                return (userUsage + systemUsage + niceUsage) / 100;   
	            } else {   
	                brStat.readLine();   
	                brStat.readLine();   
	                tokenStat = new StringTokenizer(brStat.readLine());   
	                tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                tokenStat.nextToken();   
	                String cpuUsage = tokenStat.nextToken();   
	                System.out.println("CPU idle : " + cpuUsage);   
	                Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));   
	                return (1 - usage.floatValue() / 100);   
	            }   
	        } catch (IOException ioe) {   
	            System.out.println(ioe.getMessage());   
	            freeResource(is, isr, brStat);   
	            return 1;   
	        } finally {   
	            freeResource(is, isr, brStat);   
	        }   
	    }   
	    private void freeResource(InputStream is, InputStreamReader isr,   
	            BufferedReader br) {   
	        try {   
	            if (is != null)   
	                is.close();   
	            if (isr != null)   
	                isr.close();   
	            if (br != null)   
	                br.close();   
	        } catch (IOException ioe) {   
	            System.out.println(ioe.getMessage());   
	        }   
	    }   
	    private int getMemoryForWindows() throws IOException{
			Process proc = null;
			long freeVirtualMemory = 1;
			try {
				String procCmd = "wmic os get FreeVirtualMemory";
				proc = Runtime.getRuntime().exec(procCmd);
				proc.getOutputStream().close();
				InputStreamReader ir = new InputStreamReader(proc.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				String line = null;
				while ((line = input.readLine()) != null) {
					if (line.trim().equals(""))
						continue;
					try {
						freeVirtualMemory=new Long(line.trim());
					} catch (Exception e) {
						
					}
				}
			}finally {
				try {
					if (proc != null)
						proc.getInputStream().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			long freePhysicalMemory = 1;
			try {
				String procCmd = "wmic os get FreePhysicalMemory";
				proc = Runtime.getRuntime().exec(procCmd);
				proc.getOutputStream().close();
				InputStreamReader ir = new InputStreamReader(proc.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				String line = null;
				while ((line = input.readLine()) != null) {
					if (line.trim().equals(""))
						continue;
					try {
						freePhysicalMemory=new Long(line.trim());
					} catch (Exception e) {
					}
				}
			} finally {
				try {
					if (proc != null)
						proc.getInputStream().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			long totalMemory=1;
			try {
				String procCmd = "wmic ComputerSystem get TotalPhysicalMemory";
				proc = Runtime.getRuntime().exec(procCmd);
				proc.getOutputStream().close();
				InputStreamReader ir = new InputStreamReader(proc.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				String line = null;
				while ((line = input.readLine()) != null) {
					if (line.trim().equals(""))
						continue;
					try {
						totalMemory=new Long(line.trim());
					} catch (Exception e) {
					}
				}
			}finally {
				try {
					if (proc != null)
						proc.getInputStream().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(freeVirtualMemory);
			System.out.println(totalMemory);
			return (int)(100-100*((double)(freeVirtualMemory+freePhysicalMemory)*1024)/totalMemory);
		}
		 private double getCpuRatioForWindows()throws IOException {  
			 Process proc=null;
		        try {   
		            String procCmd = "wmic cpu get loadpercentage";   
		            proc=Runtime.getRuntime().exec(procCmd);
		            proc.getOutputStream().close();   
		            InputStreamReader ir = new InputStreamReader(proc.getInputStream());   
		            LineNumberReader input = new LineNumberReader(ir);   
		            String line = null;
		            double rate=0;
		            int number=0;
		            while((line=input.readLine())!=null){
		            	if(line.trim().equals(""))
		            		continue;
		            	try{
		            		Double load=new Double(line.trim());
		            		rate+=load;
		            		number++;
		            	}catch(Exception e){
		            	}
		            }
		            return rate/number;
		        } finally {   
		            try { 
		            	if(proc!=null)
		                proc.getInputStream().close();   
		            } catch (Exception e) {   
		                e.printStackTrace();   
		            }   
		        }     
		   }
		
%>