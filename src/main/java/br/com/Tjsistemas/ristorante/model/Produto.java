package br.com.Tjsistemas.ristorante.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="produto_id")
	private Long id;
	
	private Long codigo;
	
	private String descricao;
	
	private BigDecimal valor;
	
	private Long empresa;
	
	public String abreviacao;
    public String EAN;
    
    @Column(name="preco_custo")
    public BigDecimal precoCusto ;
   
    @Column(name="margem_de_lucro")
    public Integer margemDelucro;
    
    @Column(name="desconto_maximo")
    public Integer descontoMaximo;
    
    @Column(name="estoque_minimo")
    public Integer estoqueMinimo;
    
    @Column(name="estoque_atual")
    public Integer estoqueAtual;
    
    @Column(name="ultima_eompra")
    public LocalDateTime ultimaCompra;                
    
    public boolean ativo;
    @Column(name="controle_estoque")
    public boolean controleEstoque;
	
    @ManyToOne
    @JoinColumn(name="fornecedor_id")
    public Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Long getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}
	
	public String getAbreviacao() {
		return abreviacao;
	}
	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}
	public String getEAN() {
		return EAN;
	}
	public void setEAN(String eAN) {
		EAN = eAN;
	}
	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}
	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}
	public Integer getMargemDelucro() {
		return margemDelucro;
	}
	public void setMargemDelucro(Integer margemDelucro) {
		this.margemDelucro = margemDelucro;
	}
	public Integer getDescontoMaximo() {
		return descontoMaximo;
	}
	public void setDescontoMaximo(Integer descontoMaximo) {
		this.descontoMaximo = descontoMaximo;
	}
	public Integer getEstoqueMinimo() {
		return estoqueMinimo;
	}
	public void setEstoqueMinimo(Integer estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}
	public Integer getEstoqueAtual() {
		return estoqueAtual;
	}
	public void setEstoqueAtual(Integer estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}
	public LocalDateTime getUltimaCompra() {
		return ultimaCompra;
	}
	public void setUltimaCompra(LocalDateTime ultimaCompra) {
		this.ultimaCompra = ultimaCompra;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public boolean isControleEstoque() {
		return controleEstoque;
	}
	public void setControle_estoque(boolean controleEstoque) {
		this.controleEstoque = controleEstoque;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}