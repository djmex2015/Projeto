# Projeto

ENDPOINTS

 POST http://localhost:8180/projeto

     {
        "nome": "Tiago2",
        "dataInicio": "2024-01-01",
        "dataPrevisaoFim": "2025-01-01",
        "dataFim": "2025-02-01",
        "descricao": "Descricao Test",
        "status": "INICIADO",
        "orcamento": 12.3,
        "risco": "MEDIO",
        "idGerente": 1
    }

 GET http://localhost:8180/projeto/{id}

 PUT http://localhost:8180/projeto/{id}

     {
        "nome": "Tiago2",
        "dataInicio": "2024-01-01",
        "dataPrevisaoFim": "2025-01-01",
        "dataFim": "2025-02-01",
        "descricao": "Descricao Test",
        "status": "INICIADO",
        "orcamento": 12.3,
        "risco": "MEDIO",
        "idGerente": 1
    }

 PUT http://localhost:8180/projeto/membros/(projetoId)

     {
        "idProjeto": 5,
        "idPessoas": [1,2,3]
    }

 DELETE http://localhost:8180/projeto/{id}

