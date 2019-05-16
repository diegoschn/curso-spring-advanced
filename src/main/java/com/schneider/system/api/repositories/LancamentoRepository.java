package com.schneider.system.api.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schneider.system.api.model.Lancamento;

@Repository
/**
 * 
 * @author Diego Pex
 *@NamedQueries - Foi usando pq não existe convenção para essa query spring.. então por isso teve que ser usando os @NamedQueries 
 * que é justamente criar uma jpql personalidade para a classe. E com ela, é possível fazer "n" named queries.
 * @NamedQuery - É a jpql criada afim de obter lancamentos para um determinado funcionario, por isso está associado
 * a uma outra entidade. O name deve ser em formato Nome da Classe/nomeDoMétodo e query
 * é a jpql solicitada. 
 */
@NamedQueries({
	@NamedQuery(name = "LancamentoRepository.findByFuncionarioId",
			query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId")
	
})
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

	/**
	 * 
	 * @param funcionarioId
	 * @return - retorna todos os funcionários
	 */
	List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
	/**
	 * 
	 * @param funcionarioId
	 * @param pageAble - serve para paginar os resultados
	 * @return
	 */
	Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageAble);
}
