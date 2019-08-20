FROM gradle:jdk12

WORKDIR /src
COPY . /src

RUN gradle distZip
RUN cd build/distributions && unzip backend-*.zip && mv backend-*/ backend/

EXPOSE 3000
CMD ["/src/build/distributions/backend/bin/backend"]