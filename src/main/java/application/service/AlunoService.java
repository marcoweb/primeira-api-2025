package application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import application.model.Aluno;
import application.record.AlunoDTO;
import application.record.AlunoInsertDTO;
import application.repository.AlunoRepository;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepo;

    public Iterable<AlunoDTO> getAll() {
        return alunoRepo.findAll().stream().map(AlunoDTO::new).toList();
    }

    public AlunoDTO insert(AlunoInsertDTO dados) {
        return new AlunoDTO(alunoRepo.save(new Aluno(dados)));
    }

    public AlunoDTO getOne(long id) {
        Optional<Aluno> resultado = alunoRepo.findById(id);

        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Aluno não encontrado"
            );
        }

        return new AlunoDTO(resultado.get());
    }

    public AlunoDTO update(long id, AlunoInsertDTO dadosAluno) {
        Optional<Aluno> resultado = alunoRepo.findById(id);

        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Aluno não encontrado"
            );
        }

        resultado.get().setNome(dadosAluno.nome());
        resultado.get().setIdade(dadosAluno.idade());

        return new AlunoDTO(alunoRepo.save(resultado.get()));
    }

    public void delete(long id) {
        if(!alunoRepo.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Aluno não encontrado"
            );
        }
        alunoRepo.deleteById(id);
    }
}
