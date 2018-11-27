FROM clojure:lein-2.8.1-alpine
LABEL maintainer "Erik Silkensen <eriksilkensen@gmail.com>"

RUN adduser --uid 9000 --gecos "" --disabled-password app

WORKDIR /usr/src/app

COPY . ./
RUN chown -R app:app ./

USER app

RUN lein uberjar

VOLUME /code
WORKDIR /code

CMD \
  [ "java" \
  , "-XX:+UseParNewGC" \
  , "-XX:MinHeapFreeRatio=5" \
  , "-XX:MaxHeapFreeRatio=10" \
  , "-jar", "/usr/src/app/target/codeclimate-eastwood.jar", "/config.json", "." ]
