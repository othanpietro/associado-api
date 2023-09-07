package br.com.associadoapi.controller;

import br.com.associadoapi.model.AssociadoDTO;
import br.com.associadoapi.service.AssociadoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/associado")
@Validated
public class AssociadoController {

    private final AssociadoService associadoService;


    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;

    }

    @ApiOperation(httpMethod = "POST", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Cria um associado.")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Associado criado com sucesso"),
            @ApiResponse(code = 500, message = "Não foi possível criar associado")})
    @PostMapping()
    public ResponseEntity<AssociadoDTO> postAssociado(@RequestBody @Valid AssociadoDTO associadoDTO){
        return ResponseEntity.ok(associadoService.salvar(associadoDTO));
    }

    @GetMapping()
    @ApiOperation(httpMethod = "GET", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Retona todos associados. ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Associados carregados com sucesso."),
            @ApiResponse(code = 500, message = "Não foi possível listar associados.")})
    public ResponseEntity<List<AssociadoDTO>> getAssociados(){
        return ResponseEntity.ok(associadoService.listarTodos());
    }

    @GetMapping("/{uuid}")
    @ApiOperation(httpMethod = "GET", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Retona associado pelo uuid")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Associado carregado com sucesso."),
            @ApiResponse(code = 500, message = "Não foi possível carregar associado.")})
    public ResponseEntity<AssociadoDTO> getAssociado(@PathVariable String uuid){
        return ResponseEntity.ok(associadoService.listar(uuid));
    }

    @PutMapping()
    @ApiOperation(httpMethod = "POST", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Atualiza um associado.")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Associado atualizado com sucesso."),
            @ApiResponse(code = 500, message = "Não foi possível atualizar associado.")})
    public ResponseEntity<AssociadoDTO> putAssociado(@RequestBody @Valid AssociadoDTO associadoDTO){
        return ResponseEntity.ok(associadoService.atualizar(associadoDTO));
    }
    @DeleteMapping("/{uuid}")
    @ApiOperation(httpMethod = "GET", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Deleta associado pelo uuid")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Associado deletado com sucesso."),
            @ApiResponse(code = 500, message = "Não foi possível deletar associado.")})
    public ResponseEntity<Object> deleteAssociado(@PathVariable String uuid ){
        return ResponseEntity.ok(associadoService.deletar(uuid));
    }

    @GetMapping("/documento/{documento}")
    @ApiOperation(httpMethod = "GET", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, value = "Retona associado pelo documento")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Associado carregado com sucesso."),
            @ApiResponse(code = 500, message = "Não foi possível carregar associado.")})
    public ResponseEntity<AssociadoDTO> getAssociadoPorDocumento(@PathVariable String documento){
        return ResponseEntity.ok(associadoService.listarPorDocumento(documento));
    }

}
