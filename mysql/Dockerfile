FROM mysql:8.0.33

COPY ./init.sql /docker-entrypoint-initdb.d/init-custom.sql

USER mysql

RUN chmod +rx /var/lib/mysql-files

COPY ./final_items.csv /var/lib/mysql-files

CMD ["mysqld"]