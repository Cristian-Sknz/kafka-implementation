# Apache Kafka implementation

Este projeto é uma simples implementação do Apache Kafka numa aplicação Java.

## Sobre
Este projeto serviu como uma introdução no meu aprendizado sobre o software de mensageria Apache Kafka. Implementei o Kafka de forma bem simples, criando um producer, e consumindo na mesma aplicação com o intuito de demonstrar a implementação.

## Aplicação

Para iniciar a aplicação, você terá que seguir alguns passos.
* Criar os containers do docker (kafka e zookeeper) `docker-compose up -d`
* Construir a aplicação com o gradle: `gradle build`
* Abrir o prompt em `./build/libs/`

#### Iniciar aplicação:
> Agora inicie a aplicação java, `java -jar KafkaApplication-1.0-SNAPSHOT-all.jar`
![app](https://i.imgur.com/4EPBNf1.png)
#### Enviar mensagens:
> Digite o IP do container do Kafka e pronto, você pode mandar mensagens!
![messages](https://i.imgur.com/bVSDlpE.png)

Caso não consiga se conectar, tente usar o IP da VM, `docker-machine ip` e não esqueça de colocar a porta `:9092`
