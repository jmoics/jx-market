package pe.com.jx_market.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class PO_Index extends Window{
	
	public void onCreate(){
		
	}
	
	public void accedeAsociado(){
		
	}
	
	public void accedeEmpresa(){
		
	}
	
	public void accedeExterno(){
		Executions.sendRedirect("login.zul");
	}

}
