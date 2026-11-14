# Sistema Automotivo - Gestao de Estoque de Veiculos

API REST em Java 17 + Spring Boot 3 + MySQL 8.

## Como executar
1. Instale JDK 17+, Maven e MySQL 8.
2. Execute o script `database/script.sql` no MySQL (cria o banco, tabelas e dados de exemplo).
3. Ajuste usuario/senha em `src/main/resources/application.properties`.
4. Rode: `mvn spring-boot:run`
5. Swagger: http://localhost:8080/swagger-ui.html

## Endpoints
| Metodo | Rota | Descricao |
|---|---|---|
| POST | /api/marcas | Cadastra marca |
| GET | /api/marcas?nome= | Lista/filtra marcas |
| GET | /api/marcas/{id} | Busca marca |
| PUT | /api/marcas/{id} | Atualiza marca |
| DELETE | /api/marcas/{id} | Remove marca (se sem modelos) |
| POST | /api/modelos | Cadastra modelo |
| GET | /api/modelos?marcaId= | Lista modelos |
| GET | /api/modelos/{id} | Busca modelo |
| PUT | /api/modelos/{id} | Atualiza modelo |
| DELETE | /api/modelos/{id} | Remove modelo (se sem veiculos) |
| POST | /api/veiculos | Cadastra veiculo |
| GET | /api/veiculos | Lista com filtros (marca, modelo, precoMin, precoMax, anoMin, anoMax, status, page, size, sort) |
| GET | /api/veiculos/{id} | Busca veiculo |
| PUT | /api/veiculos/{id} | Atualiza veiculo |
| PATCH | /api/veiculos/{id}/status?valor=VENDIDO | Atualiza apenas o status |
| DELETE | /api/veiculos/{id} | Remove veiculo |

## Exemplo de corpo (POST /api/veiculos)
```json
{
  "modeloId": 1,
  "ano": 2022,
  "cor": "Prata",
  "preco": 135900.00,
  "quilometragem": 28000,
  "status": "DISPONIVEL"
}
```

## Autor
Miguel Iglesias - Analise e Desenvolvimento de Sistemas
