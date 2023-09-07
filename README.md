# associado-api
API para cadastro de associados.


# Configurações da API
As configurações da api se encontra no arquivo application.properties dentro da pasta resources

# Criação do Banco de Dados
O arquivo DDL_desafio_unicred.sql para criação do banco  se encontra na pasta sql dento da pasta resources 

# Comandos para execução do kafka local
Necessário ter o kafka baixado no pc

comandos kafka

iniciar zookeper
.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties

iniciar kafka
.\bin\windows\kafka-server-start.bat config\server.properties

criar topico kafka
.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test

criar consumer kafka
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning

criar producer kafka
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test

