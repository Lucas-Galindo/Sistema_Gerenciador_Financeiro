package Model.Controller;

import Model.Entidades.Contas;
import Model.Repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("contas")
public class ContasController {

    @Autowired
    private ContasRepository contasRepository;

    @GetMapping("test")
    public ResponseEntity<Object> teste() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("list-contas")
    public ResponseEntity<Object> listarContas() {
        List<Contas> contas = contasRepository.getContas();
        return ResponseEntity.ok().body(contas);
    }

    @PostMapping("cadastro-conta")
    public ResponseEntity<Object> cadastroConta(@RequestBody Contas conta) {
        if (conta.getNome() == null || conta.getNome().trim().isEmpty()
                || conta.getSaldoInicial() == null
                || conta.getUsuario() == null
                || conta.getBanco() == null
                || conta.getTipoContas() == null) {
            return ResponseEntity.badRequest().body("Preencha todos os campos obrigatorios da conta");
        }

        if (contasRepository.existsByNomeAndUsuarioId(conta.getNome(), conta.getUsuario().getId())) {
            return ResponseEntity.badRequest().body("Conta ja cadastrada para este usuario");
        }

        contasRepository.add(conta);
        return ResponseEntity.ok().body("Conta cadastrada com sucesso");
    }

    @GetMapping("buscar-conta-id")
    public ResponseEntity<Object> buscarContaId(@RequestParam Long id) {
        Contas conta = null;
        for (Contas c : contasRepository.getContas()) {
            if (c.getId() != null && c.getId().equals(id)) {
                conta = c;
            }
        }

        if (conta != null) {
            return ResponseEntity.ok().body(conta);
        }
        return ResponseEntity.badRequest().body("Conta nao encontrada");
    }

    @GetMapping("list-contas-usuario")
    public ResponseEntity<Object> listContasUsuario(@RequestParam Long usuarioId) {
        List<Contas> contasUsuario = null;
        for(Contas c : contasRepository.getContas()){
            if(c.getUsuario().getId() == usuarioId){
                contasUsuario.add(c);
            }
        }
        if(contasUsuario != null && !contasUsuario.isEmpty())
            return ResponseEntity.ok().body(contasUsuario);
        return ResponseEntity.badRequest().body("Usuario nao possui contas");
    }

    @PutMapping("atualizar-conta")
    public ResponseEntity<Object> atualizarConta(@RequestBody Contas conta) {
        boolean flag = false;
        for (Contas c : contasRepository.getContas()) {
            if (c.getId() != null && c.getId().equals(conta.getId())) {
                c.setNome(conta.getNome());
                c.setSaldoInicial(conta.getSaldoInicial());
                c.setUsuario(conta.getUsuario());
                c.setBanco(conta.getBanco());
                c.setTipoContas(conta.getTipoContas());
                flag = true;
            }
        }

        if (flag) {
            return ResponseEntity.ok().body("Conta atualizada com sucesso");
        }
        return ResponseEntity.badRequest().body("Conta nao encontrada");
    }

    @DeleteMapping("deletar-conta")
    public ResponseEntity<Object> deletarConta(@RequestParam Long id) {
        boolean flag = false;
        for(Contas c : contasRepository.getContas()){
            if(c.getId() != null && c.getId().equals(id)){
                contasRepository.getContas().remove(c);
                flag = true;
            }
        }
        if (flag) {
            return ResponseEntity.ok().body("Conta deletada com sucesso");
        }
        return ResponseEntity.badRequest().body("Conta nao encontrada");
    }
}
