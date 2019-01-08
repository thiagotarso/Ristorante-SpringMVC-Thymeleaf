package br.com.Tjsistemas.ristorante.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.Tjsistemas.ristorante.model.EmpresaUsuario;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.filter.UsuarioFilter;
import br.com.Tjsistemas.ristorante.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Long codigoUsuario(Long codigoEmpresa) {
		Long valor = manager.createQuery("select max(u.codigo) from Usuario u where u.empresa = :emp", Long.class)
				.setParameter("emp", codigoEmpresa)
				.getSingleResult();
		
		return valor != null ? valor + 1 : 1L;
	}
	
	@Override
	public Optional<Usuario> porNomeEAtivo(String userNome) {
		return manager
				.createQuery("from Usuario where lower(nome) = lower(:nome) and ativo = true", Usuario.class)
				.setParameter("nome", userNome).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager.createQuery(
                    "select distinct p.nome from Usuario u inner join u.grupo g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<EmpresaUsuario> BuscarEmpresaUsuario(Usuario usuario) {
        Criteria criteria= manager.unwrap(Session.class).createCriteria(EmpresaUsuario.class);
        criteria.add(Restrictions.eq("usuario", usuario)); 
        criteria.addOrder(Order.desc("empresa"));
        
		return (List<EmpresaUsuario>) criteria.list();
	} 
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
		Criteria criteria =  manager.unwrap(Session.class).createCriteria(Usuario.class);
		
        paginacaoUtil.preparar(criteria, pageable);		
		adicionarFiltro(filtro, criteria);
		
		
		Sort sort = pageable.getSort();
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending()? Order.asc(property) : Order.desc(property));
		}
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(UsuarioFilter filtro) {
		   Criteria criteria =  manager.unwrap(Session.class).createCriteria(Usuario.class);
		   adicionarFiltro(filtro, criteria);
		   criteria.setProjection(Projections.rowCount());
			return (Long) criteria.uniqueResult();
		}
	
	
	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		//filtros vindo da tela 
		if (filtro != null) {
			criteria.add(Restrictions.eq("empresa", filtro.getEmpresa()));
			
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE)); //  MatchMode.ANYWHER funciona como %%
			}
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(),  MatchMode.ANYWHERE));
			}
		}
	}
}