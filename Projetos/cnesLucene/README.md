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

As métricas do download são registradas pelo Actuator. Para consultá-las, habilite
o endpoint de métricas e acesse `cnes.download.duration`, `cnes.download.total`
ou `cnes.download.bytes` em `/actuator/metrics`.

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

#### Buscar estabelecimentos por atributo

```
GET /estabelecimento/busca/codigoCnes/{codigo}
GET /estabelecimento/busca/codigoUnidade/{codigo}
GET /estabelecimento/busca/cnpj/{cnpj}
GET /estabelecimento/busca/razaoSocial/{razaoSocial}
GET /estabelecimento/busca/fantasia/{fantasia}
GET /estabelecimento/busca/endereco/{endereco}
GET /estabelecimento/busca/profissionais/{codigo}
```

O endpoint com `{endereco}` mantém a busca textual ampla: o texto informado
é pesquisado em todas as propriedades indexadas do endereço.

Também é possível combinar propriedades do endereço usando parâmetros de
consulta:

```
GET /estabelecimento/busca/endereco?logradouro=Rua%20das%20Flores&municipio=Goi%C3%A2nia
GET /estabelecimento/busca/endereco?logradouro=Avenida%20Brasil&estado=GO
GET /estabelecimento/busca/endereco?bairro=Centro&cep=74000-000
```

Todos os parâmetros informados são combinados com AND. Os campos disponíveis
são `logradouro`, `numero`, `complemento`, `bairro`, `cep`, `municipio`,
`estado`, `telefone` e `email`. Município e estado são resolvidos pelo índice
de municípios antes da busca no índice de estabelecimentos.

Para combinar razão social ou nome fantasia com o endereço, use:

```
GET /estabelecimento/busca/combinada?razaoSocial=Hospital&logradouro=Rua%20das%20Flores&municipio=Goi%C3%A2nia
GET /estabelecimento/busca/combinada?fantasia=Municipal&bairro=Centro&estado=GO
GET /estabelecimento/busca/combinada?razaoSocial=Hospital&fantasia=Municipal&cep=74000-000
```

Os critérios `razaoSocial`, `fantasia` e os critérios de endereço são
combinados com `AND`; portanto, o estabelecimento precisa atender a todos os
parâmetros informados.

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