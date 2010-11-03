package pe.com.jx_market.domain;

import java.util.Date;

public class DTO_Empleado implements java.io.Serializable {

	private String codigo;
	private DTO_Perfil perfil;
	private DTO_Usuario usuario;
	private DTO_Empresa empresa;
	private String nombre;
	private String apellido;
	private Date fecNac;
	private Integer dni;
	private String direccion;
	private String telefono;
	private String celular;
	private String email;
	private String ciudad;
	private String region;
	private Integer estado;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public DTO_Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(DTO_Perfil perfil) {
		this.perfil = perfil;
	}
	public DTO_Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(DTO_Usuario usuario) {
		this.usuario = usuario;
	}
	public DTO_Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(DTO_Empresa empresa) {
		this.empresa = empresa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Date getFecNac() {
		return fecNac;
	}
	public void setFecNac(Date fecNac) {
		this.fecNac = fecNac;
	}
	public Integer getDni() {
		return dni;
	}
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
