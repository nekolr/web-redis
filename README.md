```
$ docker network create -d bridge web_redis_net
$ docker run --name redis -dit --network web_redis_net -p 6379:6379 -v <your data dir>:/data redis:alpine redis-server --save 60 10
$ docker run --name web-redis -dit --network web_redis_net -p 8091:8091 nekolr/web-redis:<tag name>
```