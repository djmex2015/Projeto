import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.projeto.api.Main;
import com.projeto.api.bean.PessoaService;
import com.projeto.api.bean.ProjetoService;
import com.projeto.api.dto.ProjectoPessoasDto;
import com.projeto.api.dto.ProjetoDto;
import com.projeto.api.enums.ERisco;
import com.projeto.api.enums.EStatus;
import com.projeto.api.exception.ProjetoBusinessException;
import com.projeto.api.model.MembroEntity;
import com.projeto.api.model.PessoaEntity;
import com.projeto.api.model.ProjetoEntity;
import com.projeto.api.repository.PessoaRepository;
import com.projeto.api.repository.ProjetoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.util.StringUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "3600000")
public class ProjetoTest {

  private static final String DESCRICAO_UPD = "descricao-updated";

  private static final String DESCRICAO = "descricao";

  private static final ProjetoEntity PROJETO_ENTITY;

  private static final ProjetoDto PROJETO_DTO;

  private static final PessoaEntity PESSOA_ENTITY;

  private static final MembroEntity MEMBRO_ENTITY;

  private static final ProjectoPessoasDto PRO_PESSOAS_DTO;

  static {
    PROJETO_ENTITY = createProjetoEntity();
    PROJETO_DTO = createProjetoDto();
    PESSOA_ENTITY = createPessoaEntity();
    MEMBRO_ENTITY = createMembro();
    PRO_PESSOAS_DTO = createProjetoPessoas();
  }

  @MockBean
  private ProjetoRepository projetoRepo;

  @MockBean
  private PessoaRepository pessoaRepo;

  @Autowired
  private ProjetoService projetoService;

  @Autowired
  private PessoaService pessoaService;

  @Test
  void createProjetoSucessfullTest() throws ProjetoBusinessException {
    when(projetoRepo.save(any())).thenReturn(PROJETO_ENTITY);
    when(pessoaRepo.findById(any())).thenReturn(Optional.of(PESSOA_ENTITY));
    var entity = projetoService.create(PROJETO_DTO);
    assertNotNull(entity.getId());
    assertEquals(entity.getNome(), PROJETO_ENTITY.getNome());
  }

  @Test
  void createProjetoErrorGerenteTest() {
    when(projetoRepo.save(any())).thenReturn(PROJETO_ENTITY);
    when(pessoaRepo.findById("2")).thenReturn(Optional.of(PESSOA_ENTITY));
    var ex = assertThrows(ProjetoBusinessException.class, () -> projetoService.create(PROJETO_DTO));
    assertTrue(StringUtils.startsWith(ex.getMessage(), "Pessoa"));
  }

  @Test
  void updateProjetoSucessfullTest() throws ProjetoBusinessException {
    when(projetoRepo.save(any())).thenReturn(PROJETO_ENTITY);
    when(projetoRepo.findById(any())).thenReturn(Optional.of(PROJETO_ENTITY));
    when(pessoaRepo.findById(any())).thenReturn(Optional.of(PESSOA_ENTITY));
    var entity = projetoService.update(PROJETO_DTO.getId(), PROJETO_DTO);
    assertNotNull(entity.getId());
    assertEquals(entity.getDescricao(), DESCRICAO_UPD);
  }

  @Test
  void findProjetoSucessfullTest() throws ProjetoBusinessException {
    when(projetoRepo.findById(any())).thenReturn(Optional.of(PROJETO_ENTITY));
    var entity = projetoService.find(PROJETO_DTO.getId());
    assertNotNull(entity.getId());
    assertEquals(entity.getDescricao(), DESCRICAO);
  }

  @Test
  void deleteProjetoSucessfullTest() throws ProjetoBusinessException {
    PROJETO_ENTITY.setStatus(EStatus.EM_ANALISE);
    when(projetoRepo.findById(any())).thenReturn(Optional.of(PROJETO_ENTITY));
    projetoService.delete(PROJETO_DTO.getId());
  }

  @Test
  void deleteProjetoErrorTest() {
    PROJETO_ENTITY.setStatus(EStatus.INICIADO);
    when(projetoRepo.findById(any())).thenReturn(Optional.of(PROJETO_ENTITY));
    var ex = assertThrows(ProjetoBusinessException.class, () -> projetoService.delete(PROJETO_DTO.getId()));
    assertTrue(StringUtils.startsWith(ex.getMessage(), "Projeto com status Iniciado"));
  }

  @Test
  void findPessoaSucessfullTest() throws ProjetoBusinessException {
    when(pessoaRepo.findById(any())).thenReturn(Optional.of(PESSOA_ENTITY));
    var entity = pessoaService.find(String.valueOf(PESSOA_ENTITY.getId()));
    assertNotNull(entity.getId());
  }

  @Test
  void associateMembrosSucessfullTest() {
    when(projetoRepo.findById(any())).thenReturn(Optional.of(PROJETO_ENTITY));
    when(pessoaRepo.findById("1")).thenReturn(Optional.of(createPessoaEntity(1L, true)));
    when(pessoaRepo.findById("2")).thenReturn(Optional.of(createPessoaEntity(2L, false)));
    when(pessoaRepo.findById("3")).thenReturn(Optional.of(createPessoaEntity(3L, true)));
    var ex = assertThrows(RuntimeException.class,
        () -> projetoService.associateMembers(PRO_PESSOAS_DTO.getIdProjeto(), PRO_PESSOAS_DTO));
    assertTrue(StringUtils.contains(ex.getMessage(), "Pessoa 2"));
  }

  private static ProjetoEntity createProjetoEntity() {
    ProjetoEntity entity = new ProjetoEntity();
    entity.setId(1L);
    entity.setNome("Mat");
    entity.setDataInicio(LocalDate.ofYearDay(2020, 3));
    entity.setDataFim(LocalDate.ofYearDay(2021, 3));
    entity.setDataPrevisaoFim(LocalDate.ofYearDay(2021, 2));
    entity.setDescricao("descricao");
    entity.setRisco(ERisco.ALTO);
    entity.setOrcamento(12.20F);
    entity.setIdGerente(createPessoaEntity());
    entity.setStatus(EStatus.EM_ANALISE);
    return entity;
  }

  private static ProjetoDto createProjetoDto() {
    ProjetoDto dto = new ProjetoDto();
    dto.setNome("Mat");
    dto.setDataInicio(LocalDate.ofYearDay(2020, 3));
    dto.setDataFim(LocalDate.ofYearDay(2021, 3));
    dto.setDataPrevisaoFim(LocalDate.ofYearDay(2021, 2));
    dto.setDescricao(DESCRICAO_UPD);
    dto.setRisco(ERisco.ALTO);
    dto.setOrcamento(12.20F);
    dto.setIdGerente("1");
    dto.setStatus(EStatus.INICIADO);
    return dto;
  }

  private static PessoaEntity createPessoaEntity(Long id, boolean isFunc) {
    var entity = createPessoaEntity();
    entity.setId(id);
    entity.setFuncionario(isFunc);
    return entity;
  }

  private static PessoaEntity createPessoaEntity() {
    PessoaEntity pessoa = new PessoaEntity();
    pessoa.setId(1L);
    pessoa.setCpf("999.222.111-3");
    pessoa.setNome("Luiz");
    pessoa.setDatanascimento(LocalDate.of(1980, 2, 3));
    pessoa.setFuncionario(true);
    return pessoa;
  }

  private static MembroEntity createMembro() {
    return MembroEntity.builder().idProjeto(createProjetoEntity()).idPessoa(createPessoaEntity()).build();
  }

  private static ProjectoPessoasDto createProjetoPessoas() {
    return ProjectoPessoasDto.builder().idPessoas(List.of("1", "2", "3")).idProjeto("1").build();
  }

}
