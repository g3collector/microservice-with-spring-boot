
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


## auth-service

```
curl -X POST -vu acme:acmesecret http://localhost:9191/uaa/oauth/token -H "Accept: application/json" -d "password=spring&username=jlong&grant_type=password&scope=openid&client_secret=acmesecret&client_id=acme"
*   Trying ::1...
* Connected to localhost (::1) port 9191 (#0)
* Server auth using Basic with user 'acme'
> POST /uaa/oauth/token HTTP/1.1
> Host: localhost:9191
> Authorization: Basic YWNtZTphY21lc2VjcmV0
> User-Agent: curl/7.43.0
> Accept: application/json
> Content-Length: 103
> Content-Type: application/x-www-form-urlencoded
>
* upload completely sent off: 103 out of 103 bytes
< HTTP/1.1 200
< X-Application-Context: auth-service:9191
< Cache-Control: no-store
< Pragma: no-cache
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< X-Frame-Options: DENY
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Sat, 30 Jul 2016 14:27:00 GMT
<
* Connection #0 to host localhost left intact
{"access_token":"ea775aeb-cdc9-400c-b841-2bf77e42eaa8","token_type":"bearer","refresh_token":"6b966b02-73e8-4628-8b45-1d3ed0983fe5","expires_in":42598,"scope":"openid"}
```

```
curl http://localhost:9999/reservations/names -H "Authorization: Bearer ea775aeb-cdc9-400c-b841-2bf77e42eaa8"
["takuma","michiyo","fumiyuki","yousuke"]
```