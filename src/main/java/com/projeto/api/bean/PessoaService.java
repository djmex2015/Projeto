package com.projeto.api.bean;

import static com.projeto.api.exception.ErrorCode.PESSOA_NOT_FOUND;
import static com.projeto.api.exception.ErrorCode.PESSOA_NOT_FUNCIONARIA;
import static com.projeto.api.exception.Precondition.checkNotNull;
import static com.projeto.api.exception.Precondition.checkTrue;

import com.projeto.api.exception.ProjetoBusinessException;
import com.projeto.api.model.PessoaEntity;
import com.projeto.api.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaService {

  private final PessoaRepository pessoaRepo;

  public PessoaEntity find(String pessoaId) throws ProjetoBusinessException {
    return checkNotNull(pessoaRepo.findById(pessoaId), PESSOA_NOT_FOUND, pessoaId);
  }

  public PessoaEntity checkFuncionario(String idPess) throws ProjetoBusinessException {
    var pessoa = find(idPess);
    checkTrue(pessoa.isFuncionario(), PESSOA_NOT_FUNCIONARIA, idPess);
    return pessoa;
  }
}
