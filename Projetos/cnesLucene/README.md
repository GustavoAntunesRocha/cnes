# API de Consulta CNES com Lucene

Este projeto é uma aplicação Spring Boot que indexa e consulta dados do Cadastro Nacional de Estabelecimentos de Saúde (CNES) usando o Apache Lucene como motor de busca.

## Visão Geral

A aplicação baixa automaticamente a base de dados mais recente do DATASUS via FTP, processa os arquivos CSV e cria índices Lucene para consultas rápidas de profissionais e estabelecimentos de saúde.

## Requisitos

- Java 11+
- Maven 3.6+
- Espaço em disco para armazenar os índices Lucene (1Gb +)

## Construção e Execução

### Compilação

```bash
mvn clean package
```

### Execução

Para executar com download automático da base mais atualizada:

```bash
java -jar target/cnesLucene-0.0.1-SNAPSHOT.jar update
```

Para executar apenas o servidor sem atualizar a base:

```bash
java -jar target/cnesLucene-0.0.1-SNAPSHOT.jar
```

## Endpoints da API

### Profissionais

#### Buscar profissionais por nome

```
GET /profissional/nome/{nomeProfissional}
```

Retorna todos os profissionais que contenham o nome especificado.

#### Buscar profissionais por nome em um estabelecimento específico

```
GET /profissional/nome/{nomeProfissional}/cnes/{codigoCnes}
```

Retorna todos os profissionais que contenham o nome especificado e trabalhem no estabelecimento com o código CNES informado.

#### Buscar profissional por CNS e CNES

```
GET /profissional/busca/cns/{codigoCns}/cnes/{codigoCnes}
```

Retorna informações do profissional com o CNS especificado que trabalha no estabelecimento com o código CNES informado.

### Estabelecimentos

#### Verificar se um estabelecimento existe

```
GET /estabelecimento/existe/{cnes}
```

Verifica se um estabelecimento com o código CNES especificado existe no banco de dados.

#### Buscar estabelecimentos por código do profissional

```
GET /estabelecimento/busca/codigoProfissional/{codigo}
```

Retorna todos os estabelecimentos onde o profissional com o código especificado trabalha.

## Estrutura de Dados

Os principais modelos de dados incluem:

- `Profissional` - Informações sobre profissionais de saúde
- `Estabelecimento` - Informações sobre estabelecimentos de saúde
- `ProfissionalEstabelecimento` - Relação entre profissionais e estabelecimentos
- `Cbo` - Código Brasileiro de Ocupações
- `Municipio` e `Estado` - Informações geográficas

## Armazenamento

A aplicação cria e gerencia os seguintes diretórios:

- `./data/arquivos/` - Armazena os arquivos baixados do DATASUS
- `./data/index/` - Armazena os índices Lucene para consultas rápidas

## Observações

- A indexação inicial pode levar alguns minutos dependendo do tamanho da base de dados

## Tecnologias Utilizadas

- Spring Boot 2.7
- Apache Lucene 9.3
- Jackson para serialização JSON
- Apache Commons para operações FTP

Para mais detalhes, consulte o código-fonte