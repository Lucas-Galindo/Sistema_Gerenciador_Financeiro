package Model.Controller;

import Model.Entidades.Metas;
import Model.Repository.MetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("metas")
public class MetasController {

    @Autowired
    private MetasRepository metasRepository;

    @GetMapping("test")
    public ResponseEntity<Object> teste() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("list-metas")
    public ResponseEntity<Object> listarMetas() {
        List<Metas> metas = metasRepository.getMetas();
        return ResponseEntity.ok().body(metas);
    }

    @PostMapping("cadastro-meta")
    public ResponseEntity<Object> cadastroMeta(@RequestBody Metas meta) {
        if (meta.getNome() == null || meta.getNome().trim().isEmpty()
                || meta.getValorObjetivo() == null
                || meta.getDataLimite() == null || meta.getDataLimite().trim().isEmpty()
                || meta.getUsuario() == null || meta.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body("Preencha todos os campos obrigatorios da meta");
        }

        boolean existe = false;
        for (Metas m : metasRepository.getMetas()) {
            if (m.getNome() != null
                    && m.getUsuario() != null
                    && m.getUsuario().getId() != null
                    && m.getNome().equalsIgnoreCase(meta.getNome())
                    && m.getUsuario().getId().equals(meta.getUsuario().getId())) {
                existe = true;
            }
        }

        if (existe) {
            return ResponseEntity.badRequest().body("Meta ja cadastrada para este usuario");
        }

        if (meta.getValorAtual() == null || meta.getValorAtual().trim().isEmpty()) {
            meta.setValorAtual("0.00");
        }

        metasRepository.add(meta);
        return ResponseEntity.ok().body("Meta cadastrada com sucesso");
    }

    @GetMapping("buscar-meta-id")
    public ResponseEntity<Object> buscarMetaId(@RequestParam Long id) {
        Metas meta = null;
        for (Metas m : metasRepository.getMetas()) {
            if (m.getId() != null && m.getId().equals(id)) {
                meta = m;
            }
        }

        if (meta != null) {
            return ResponseEntity.ok().body(meta);
        }
        return ResponseEntity.badRequest().body("Meta nao encontrada");
    }

    @GetMapping("list-metas-usuario")
    public ResponseEntity<Object> listMetasUsuario(@RequestParam Long usuarioId) {
        List<Metas> metasUsuario = new ArrayList<>();
        for (Metas m : metasRepository.getMetas()) {
            if (m.getUsuario() != null
                    && m.getUsuario().getId() != null
                    && m.getUsuario().getId().equals(usuarioId)) {
                metasUsuario.add(m);
            }
        }

        if (!metasUsuario.isEmpty()) {
            return ResponseEntity.ok().body(metasUsuario);
        }
        return ResponseEntity.badRequest().body("Usuario nao possui metas");
    }

    @PutMapping("atualizar-meta")
    public ResponseEntity<Object> atualizarMeta(@RequestBody Metas meta) {
        if (meta.getId() == null) {
            return ResponseEntity.badRequest().body("ID da meta obrigatorio");
        }

        boolean flag = false;
        for (Metas m : metasRepository.getMetas()) {
            if (m.getId() != null && m.getId().equals(meta.getId())) {
                m.setNome(meta.getNome());
                m.setValorObjetivo(meta.getValorObjetivo());
                m.setValorAtual(meta.getValorAtual());
                m.setDataLimite(meta.getDataLimite());
                m.setUsuario(meta.getUsuario());
                flag = true;
            }
        }

        if (flag) {
            return ResponseEntity.ok().body("Meta atualizada com sucesso");
        }
        return ResponseEntity.badRequest().body("Meta nao encontrada");
    }

    @PutMapping("atualizar-progresso-meta")
    public ResponseEntity<Object> atualizarProgressoMeta(@RequestParam Long id, @RequestParam BigDecimal valorAtual) {
        if (valorAtual.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().body("Valor atual nao pode ser negativo");
        }

        boolean flag = false;
        Metas metaAtualizada = null;

        for (Metas m : metasRepository.getMetas()) {
            if (m.getId() != null && m.getId().equals(id)) {
                m.setValorAtual(valorAtual.toPlainString());
                metaAtualizada = m;
                flag = true;
            }
        }

        if (flag) {
            return ResponseEntity.ok().body(metaAtualizada);
        }
        return ResponseEntity.badRequest().body("Meta nao encontrada");
    }

    @DeleteMapping("deletar-meta")
    public ResponseEntity<Object> deletarMeta(@RequestParam Long id) {
        Metas metaParaRemover = null;
        for (Metas m : metasRepository.getMetas()) {
            if (m.getId() != null && m.getId().equals(id)) {
                metaParaRemover = m;
            }
        }

        if (metaParaRemover != null) {
            metasRepository.getMetas().remove(metaParaRemover);
            return ResponseEntity.ok().body("Meta deletada com sucesso");
        }
        return ResponseEntity.badRequest().body("Meta nao encontrada");
    }
}
