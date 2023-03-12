# CNES

Esse projeto tem como objetivo criar uma api para acesso à informações do Cadastro Nacional de Estabelecimentos de Saúde, CNES.
A base de dados é atualizada mês a mês e é disponibilizada através do servidor ftp do Datasus em um arquivo zip, dentro dele contém diversos arquivos .csv contendo todas os dados.

Nesse projeto criei dois sistemas, um mapeando os dados e armazendando em um banco de dados relacional, e o outro utilizando o Apache Lucene que cria um indexamento dos dados.
Utilizando o Lucene se mostrou uma melhor opção, devido ao tempo para criar o indexamento é bastante menor que armazenar os dados em um banco de dados relacional.
