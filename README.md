
## youtube
https://www.youtube.com/watch?v=ULxNBphD-cw

## reservation-service

@RepositoryRestResource

http://localhost:8080/reservations


config-server 設定後
bootstrap.properties
```
spring.cloud.config.uri=http://localhost:8888
#config-server のpuropertiesファイルの名前と同じ
spring.application.name=reservation-service
```

RestController
@RefreshScope

vi reservation-service.properties
server.port=${PORT:8000}
message = Hello JSUG!!!!!!!!!!!!!!!!!!!!


git commit -m -a YOLO

http://localhost:8888/reservation-service/default
変更が即反映される

downstream microservice does not know what happen.

notice enterprise bus 
curl -d{} http://localhost:8000/refresh 

## config-server

```
git clone git@github.com:joshlong/bootiful-microservices-config.git ~/Desktop/config
git clone git@github.com:stgctkm/microservice-with-spring-boot-config.git
https://github.com/stgctkm/microservice-with-spring-boot-config
```

configration information

http://localhost:8888/reservation-service/default

reservation-service.properties
起動port、messageの設定がある


spring.cloud.config.server.git.uri=${HOME}/Desktop/config
server.port=8888


## eureka-server
consul, zookeeper,etcd 等と同様のService Registry

@EnableEurekaServer

bootstrap.properties
spring.cloud.config.uri=http://localhost:8888
spring.application.name=eureka-service

http://localhost:8761

reservation-serviceに@EnableDiscoveryClientを追加

## reservation-client


@EnableZuulProxy
@EnableDiscoveryClient

Edge Service
Eureka経由
http://localhost:9999/reservation-service/reservations
直アクセス
http://localhost:8000/reservations


Netflix ribbon
http://localhost:9999/reservations/names

Read
CircuitBreaker
@EnableCircuitBreaker


Write
SAGA Pattern
@EnableBinding(ReservationChannels.class)

http://localhost:8888/reservation-client/default
spring.cloud.stream.bindings.output.destination: "reservations"


rabbitmq
https://www.rabbitmq.com/install-homebrew.html
brew install rabitmq
rabbitmq-server


http://localhost:8888/reservation-service/default
spring.cloud.stream.bindings.input.group: "reservations-group"
spring.cloud.stream.bindings.input.destination: "reservations",
spring.cloud.stream.bindings.input.durableSubscription: "true"

postman
post

http://localhost:9999/reservations/names

reservation-service stop downstream service
post
```
curl -X POST -H "Content-Type: application/json; charser=UTF-8" -H "Cache-Control: no-cache" -H "Postman-Token: 206988c3-4db1-f3a0-5c28-0ac5fee9eb68" -d '{
"reservationName":"Dr Pollack"
}' "http://localhost:9999/reservations"
```
post

restart reservation-service
起動時にqueueから取得している
2016-07-17 09:25:56.191  INFO [reservation-service,,,] 29573 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8000 (http)
2016-07-17 09:25:56.193  INFO [reservation-service,,,] 29573 --- [           main] c.n.e.EurekaDiscoveryClientConfiguration : Updating port to 8000
Reservation{id=1, reservationName=Dr Nakamatsu}
Reservation{id=2, reservationName=Dr Pollack}
Reservation{id=3, reservationName=takuma}
Reservation{id=4, reservationName=michiyo}
Reservation{id=5, reservationName=fumiyuki}
Reservation{id=6, reservationName=yousuke}


## hystrix-dashboard
http://localhost:8010/hystrix.html

http://localhost:9999/hystrix.stream

## zipkin-service

http://localhost:9411/

Sleuth