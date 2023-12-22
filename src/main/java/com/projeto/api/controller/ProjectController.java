package com.projeto.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.projeto.api.bean.ProjetoService;
import com.projeto.api.dto.ProjectoPessoasDto;
import com.projeto.api.dto.ProjetoDto;
import com.projeto.api.exception.ProjetoBusinessException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/projeto")
public class ProjectController {

  private final ProjetoService projetoService;

  @PostMapping
  @ResponseStatus(CREATED)
  public ProjetoDto create(@RequestBody @Valid @NotNull ProjetoDto request) throws ProjetoBusinessException {
    return projetoService.create(request);
  }

  @GetMapping("/{projetoId}")
  public ProjetoDto find(@PathVariable @NotBlank String projetoId) throws ProjetoBusinessException {
    return projetoService.find(projetoId);
  }

  @PutMapping("/{projetoId}")
  public ProjetoDto update(@PathVariable @NotBlank String projetoId, @RequestBody @Valid @NotNull ProjetoDto request)
      throws ProjetoBusinessException {
    return projetoService.update(projetoId, request);
  }

  @ResponseStatus(NO_CONTENT)
  @DeleteMapping("/{projetoId}")
  public void delete(@PathVariable @NotBlank String projetoId) throws ProjetoBusinessException {
    projetoService.delete(projetoId);
  }

  @PutMapping("/membros/{projetoId}")
  public ProjectoPessoasDto associateMembers(@PathVariable @NotBlank String projetoId,
      @RequestBody @Valid @NotNull ProjectoPessoasDto memberDto)
      throws ProjetoBusinessException {
    return projetoService.associateMembers(projetoId, memberDto);
  }

}
