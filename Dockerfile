FROM openjdk:11

WORKDIR /app/data/batch

COPY src/Main.java .

RUN javac Main.java

CMD ["java", "Main"]
