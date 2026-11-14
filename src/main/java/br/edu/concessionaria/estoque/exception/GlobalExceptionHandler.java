package br.edu.concessionaria.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/** Padroniza as respostas de erro da API (RNF01 - usabilidade). */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> naoEncontrado(RecursoNaoEncontradoException ex) {
        return erro(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> regraNegocio(RegraNegocioException ex) {
        return erro(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> campos.put(e.getField(), e.getDefaultMessage()));
        return erro(HttpStatus.BAD_REQUEST, "Dados invalidos", campos);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> jsonInvalido(HttpMessageNotReadableException ex) {
        return erro(HttpStatus.BAD_REQUEST,
                "Corpo da requisicao invalido. Verifique o JSON e os valores informados (ex.: status).", null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> parametroInvalido(MethodArgumentTypeMismatchException ex) {
        return erro(HttpStatus.BAD_REQUEST,
                "Valor invalido para o parametro '" + ex.getName() + "'", null);
    }

    private ResponseEntity<Map<String, Object>> erro(HttpStatus status, String mensagem, Object detalhes) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", status.value());
        corpo.put("erro", status.getReasonPhrase());
        corpo.put("mensagem", mensagem);
        if (detalhes != null) {
            corpo.put("detalhes", detalhes);
        }
        return ResponseEntity.status(status).body(corpo);
    }
}
