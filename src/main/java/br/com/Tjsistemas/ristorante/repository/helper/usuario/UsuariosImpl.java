package br.com.Tjsistemas.ristorante.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.EmpresaUsuario;
import br.com.Tjsistemas.ristorante.model.Usuario;

public class UsuariosImpl implements UsuariosQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Long codigoUsuario(Usuario usuarioAutenticado) {
		Long valor = manager.createQuery("select max(u.codigo) from Usuario u where u.empresa = :emp", Long.class)
				.setParameter("emp", usuarioAutenticado.getEmpresa())
				.getSingleResult();
		
		return  valor + 1L;
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
}