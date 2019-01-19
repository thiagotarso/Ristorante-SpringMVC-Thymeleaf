package br.com.Tjsistemas.ristorante.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import br.com.Tjsistemas.ristorante.validation.validator.AtributoConfirmacao;

@AtributoConfirmacao(atributo= "senha", atributoConfirmacao = "confirmacaoSenha", message ="Confirmação Senha não Conferem!")
@Entity
@Table(name="usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long codigo;
	
	@NotBlank( message="Campo 'Nome' não pode ser vazio!")
	@Size(max = 20, message="Campo 'Nome' pode ter no maximo 20 caracteres!")
    private String nome;
    
	@Email(message="Digite um E-mail valido!")
    private String email;
	
    private String senha;
   
    @Transient
    private String confirmacaoSenha;
    
    private Boolean ativo;
	
	private Long empresa;
    
	@Size(min= 1,message="selecione pelo menos um grupo!")
	@ManyToMany
	@JoinTable(name="usuario_grupo", joinColumns = @JoinColumn(name ="codigo_usuario"), 
	inverseJoinColumns = @JoinColumn(name="codigo_grupo"))
	private List<Grupo> grupo;
	
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	private List<EmpresaUsuario> empresaUsuario  = new ArrayList<>();

    public Usuario() {
	}

	public Usuario(Long id, String nome, String email, Long empresa, List<EmpresaUsuario> empresaUsuario) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.empresa = empresa;
		this.empresaUsuario = empresaUsuario;
	}
    

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<Grupo> getGrupo() {
		return grupo;
	}

	public void setGrupo(List<Grupo> grupo) {
		this.grupo = grupo;
	}
	
	public List<EmpresaUsuario> getEmpresaUsuario() {
		return empresaUsuario;
	}

	public void setEmpresaUsuario(List<EmpresaUsuario> empresaUsuario) {
		this.empresaUsuario = empresaUsuario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
}
