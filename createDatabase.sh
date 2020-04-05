mysql -u root -e "drop database if exists bd2_grupo15;
                  create database bd2_grupo15;
                  CREATE USER IF NOT EXISTS grupo15 IDENTIFIED BY 'bd2';
                  GRANT ALL PRIVILEGES ON bd2_grupo15.* TO 'grupo15'@'localhost';
                  FLUSH PRIVILEGES;
                  "
