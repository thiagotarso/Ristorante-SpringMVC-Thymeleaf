package br.com.Tjsistemas.ristorante.dto;

public class EmpresaDTO {

	private Long id;
	
	private String codigo;

	private String razaoSocial;
	
	public EmpresaDTO(Long id, String codigo, String razaoSocial) {
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getRazaoSocial() {
		return razaoSocial.toUpperCase();
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}